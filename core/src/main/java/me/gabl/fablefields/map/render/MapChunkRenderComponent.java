package me.gabl.fablefields.map.render;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.*;

import java.util.*;

@AllArgsConstructor
public class MapChunkRenderComponent {

    public final MapChunkLayers<TileStackLayer> layerStack;
    public final MapChunkLayers<Layer<RenderInstruction[]>> referencedInstructions;
    public final TiledMap map;

    private final MapChunk chunk;

    public MapChunkRenderComponent(MapChunk chunk) {
        this.map = new TiledMap();
        this.chunk = chunk;
        this.referencedInstructions = new MapChunkLayers<>(() -> new Layer<>(new RenderInstruction[chunk.height * chunk.width][], chunk.width, chunk.height));
        this.layerStack = new MapChunkLayers<>(() -> new TileStackLayer(chunk.width, chunk.height));
        initMap();
    }

    private void addLayers() {
        MapLayers mapLayers = map.getLayers();
        layerStack.forEach(layer -> {
            layer.forEachTiledLayer(mapLayers::add);
            layer.dirty = false;
        });
    }

    private void initMap() {
        map.getTileSets().addTileSet(Asset.TILESET);
        addLayers();
    }

    public void initialRender() {
        renderLayer(MapLayer.GROUND, chunk.getLayer(MapLayer.GROUND).tiles);
        renderLayer(MapLayer.SURFACE, chunk.getLayer(MapLayer.SURFACE).tiles);
        renderLayer(MapLayer.FEATURE, chunk.getLayer(MapLayer.FEATURE).tiles);
    }

    private void renderLayer(MapLayer layer, MapTile[] logicLayer) {
        for (int x = 0; x < chunk.width; x++) {
            for (int y = 0; y < chunk.height; y++) {
                renderCell(layer, x, y, logicLayer[x + y * chunk.width]);
            }
        }

        if (dirty()) rebuildLayers();
    }


    void renderCell(Address address, MapTile tile) {
        renderCell(address.layer, address.x(chunk.width), address.y(chunk.width), tile);
    }

    public void renderCell(MapLayer layer, int position, MapTile tile) {
        renderCell(layer, position % chunk.width, position / chunk.width, tile);
    }

    private void removeReferences(MapLayer layer, int x, int y) {
        RenderInstruction[] toRemove = referencedInstructions.get(layer).get(x, y);
        if (toRemove == null) return;
        Set<MapLayer> changedLayers = new HashSet<>();
        for (RenderInstruction instruction : toRemove) {
            if (instruction == null) continue;
            layerStack.get(instruction.address().layer).remove(instruction);
            changedLayers.add(instruction.address().layer);
        }

        changedLayers.forEach(cleanupLayer -> layerStack.get(cleanupLayer).cleanUp());
        referencedInstructions.get(layer).set(x, y, null);
    }

    void renderCell(MapLayer layer, int x, int y, MapTile tile) {
        removeReferences(layer, x, y);
        if (tile == null) return;
        for (RenderInstruction instruction : tile.material.render(new MapTileContext(chunk, tile, layer, x, y))) {
            if (instruction == null || instruction.cell() == null || instruction.cell().getTile() == null) {
                continue;
            }
            layerStack.get(instruction.address().layer).push(instruction);
            pushInstructionReference(layer, x, y, instruction);
        }
        if (dirty()) rebuildLayers();
    }

    void pushInstructionReference(MapLayer layer, int x, int y, RenderInstruction instruction) {
        Layer<RenderInstruction[]> referenceLayer = referencedInstructions.get(layer);
        RenderInstruction[] instructions = referenceLayer.get(x, y);
        if (instructions == null || instructions.length == 0) {
            referenceLayer.set(x, y, new RenderInstruction[]{instruction});
            return;
        }
        for (int i = 0; i < instructions.length; i++) {
            if (instructions[i] == null) {
                instructions[i] = instruction;
                return;
            }
        }

        instructions = Arrays.copyOf(instructions, instructions.length + 1);
        instructions[instructions.length - 1] = instruction;
        referenceLayer.set(x, y, instructions);
    }

    private static TiledMapTileLayer getEmptyLayer(int width, int height) {
        return new TiledMapTileLayer(width, height, 16, 16);
    }

    private boolean dirty() {
        return layerStack.stream().anyMatch(layer -> layer.dirty);
    }

    private void rebuildLayers() {
        Iterator<com.badlogic.gdx.maps.MapLayer> iterator = map.getLayers().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        if (map.getLayers().size() > 0) {
            throw new IllegalStateException("Map is not empty after removing all layers");
        }

        addLayers();
    }
}
