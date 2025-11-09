package me.gabl.fablefields.map.render;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import me.gabl.common.util.tuple.Pair;
import me.gabl.fablefields.map.logic.Layer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TileStackLayer {

    private final List<Pair<TiledMapTileLayer, Layer<RenderInstruction>>> layers;
    private final int width;
    private final int height;

    // layer has been removed/added, initially true
    public boolean dirty;

    public TileStackLayer(int width, int height) {
        this.width = width;
        this.height = height;
        layers = new ArrayList<>();
        dirty = true;
    }

    public void forEachTiledLayer(Consumer<? super TiledMapTileLayer> consumer) {
        forEachPair(pair -> consumer.accept(pair.getA()));
    }

    public void forEachPair(Consumer<? super Pair<TiledMapTileLayer, Layer<RenderInstruction>>> consumer) {
        layers.forEach(consumer);
    }

    public void remove(RenderInstruction instruction) {
        //linear search more than enough, should never be more than 5 layers
        int x = instruction.address().x(width);
        int y = instruction.address().y(width);
        boolean found = false;
        for (int i = 0; i < layers.size(); i++) {
            Pair<TiledMapTileLayer, Layer<RenderInstruction>> layer = layers.get(i);
            if (!found && layer.getB().get(x, y) == instruction) {
                layer.getB().set(x, y, null);
                found = true;
            }

            if (found) {
                RenderInstruction nextLevelInstruction = (i + 1 < layers.size()) ? layers.get(i + 1).getB().get(x, y) : null;
                layer.getB().set(x, y, nextLevelInstruction);
            }
        }

        match(x, y);
    }

    public void push(RenderInstruction instruction) {
        List<RenderInstruction> localInstructions = new ArrayList<>();
        localInstructions.add(instruction);
        int x = instruction.address().x(width);
        int y = instruction.address().y(width);
        forEachRenderLayer(layer -> {
            RenderInstruction localInstruction = layer.get(x, y);
            if (localInstruction != null) {
                localInstructions.add(localInstruction);
            }
        });

        List<RenderInstruction> sorted = localInstructions.stream().sorted().toList();
        for (int i = 0; i < Math.max(sorted.size(), layers.size()); i++) {
            if (i < sorted.size()) {
                getCreate(i).getB().set(x, y, sorted.get(i));
            } else {
                getCreate(i).getB().set(x, y, null);
            }
        }

        match(x, y);
    }

    public void forEachRenderLayer(Consumer<? super Layer<RenderInstruction>> consumer) {
        forEachPair(pair -> consumer.accept(pair.getB()));
    }

    public Pair<TiledMapTileLayer, Layer<RenderInstruction>> getCreate(int pairIndex) {
        Pair<TiledMapTileLayer, Layer<RenderInstruction>> x = null;
        if (layers.size() > pairIndex) {
            x = layers.get(pairIndex);
        }
        if (x != null) {
            return x;
        }
        x = Pair.of(emptyTiledLayer(), emptyRenderLayer());
        layers.add(x);
        dirty = true;
        return x;
    }

    public void cleanUp() {
        // try & clear the top layer, if cleared, try clearing the next one aso
//        while (clearEmptyLayer());
        // todo suspended, microstutters?
    }

    private boolean clearEmptyLayer() {
        if (layers.isEmpty()) return false;
        for (RenderInstruction instruction : layers.getLast().getB().tiles) {
            if (instruction != null) {
                return false;
            }
        }

        layers.removeLast();
        dirty = true;
        return true;
    }

    private TiledMapTileLayer emptyTiledLayer() {
        return new TiledMapTileLayer(width, height, 16, 16);
    }

    private Layer<RenderInstruction> emptyRenderLayer() {
        return new Layer<>(new RenderInstruction[width * height], width, height);
    }

    public void match(int x, int y) {
        forEachPair(pair -> {
            TiledMapTileLayer.Cell cell = null;
            if (pair.getB().get(x, y) != null) {
                cell = pair.getB().get(x, y).cell();
            }
            pair.getA().setCell(x, y, cell);
        });
    }
}
