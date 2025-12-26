package me.gabl.fablefields.map.logic;

import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;
import me.gabl.fablefields.game.entity.Chicken;
import me.gabl.fablefields.game.entity.Entity;
import me.gabl.fablefields.game.entity.Tree;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.render.MapChunkRenderComponent;
import me.gabl.fablefields.player.Movement;
import me.gabl.fablefields.screen.game.Entities;
import me.gabl.fablefields.screen.util.QuadTree;
import me.gabl.fablefields.util.MathUtil;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class MapChunk {

    public final int width;
    public final int height;
    public final long seed;
    public final Noise2D noise;

    public QuadTree<Tree> collision;
    public final Entities entities;

    private final MapChunkLayers<Layer<MapTile>> tileLayers;
    @Setter
    @Getter
    private MapChunkRenderComponent renderComponent;


    public MapChunk(MapChunkLayers<MapTile[]> tileLayers, int width, int height, long seed) {
        this.seed = seed;
        this.noise = new Noise2D(seed);

        tileLayers.stream().forEach(tileLayer -> {
            assert tileLayer.length == width * height;
        });


        this.tileLayers = tileLayers.map(array -> new Layer<>(array, width, height));
        this.width = width;
        this.height = height;

        this.entities = new Entities();
    }

    public void initRenderComponent() {
        renderComponent = new MapChunkRenderComponent(this);
    }

    public Layer<MapTile> getLayer(MapLayer layer) {
        return tileLayers.get(layer);
    }

    public void setTile(MapTile tile, Address address) {
        setTile(address.layer, address.position, tile);
    }

    public void setTile(MapLayer layer, int position, MapTile tile) {
        tileLayers.get(layer).tiles[position] = tile;
        renderComponent.renderCell(layer, position, tile);
    }

    public MapTile getTile(Address address) {
        return getTile(address.layer, address.position);
    }

    /**
     * @return null if tile is out of map, else the tile stored at position
     */
    public MapTile getTile(MapLayer layer, int position) {
        if (containsTile(position)) return tileLayers.get(layer).tiles[position];
        return null;
    }

    public boolean containsTile(int position) {
        return position >= 0 && position < width * height;
    }

    public boolean is(Predicate<MapTile> predicate, float x, float y) {
        int fx = (int) Math.floor(x);
        int fy = (int) Math.floor(y);
        if (!containsTile(fx, fy)) return false;
        return is(predicate, position(fx, fy));
    }

    public boolean containsTile(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean is(Predicate<MapTile> predicate, int position) {
        MapTile tile = getTile(MapLayer.GROUND, position);
        return tile != null && predicate.test(tile);
    }

    public int position(int x, int y) {
        return x + width * y;
    }

    @Nullable
    public MapTile getTile(MapLayer layer, float x, float y) {
        int fx = (int) Math.floor(x);
        int fy = (int) Math.floor(y);
        if (!containsTile(fx, fy)) return null;
        return getTile(layer, position(fx, fy));
    }

    public void populate() {
        for (int i = 0; i < 40; i++) {
            Chicken chicken = new Chicken(this);
            do {
                chicken.setPosition(MathUtil.RANDOM.nextFloat() * width, MathUtil.RANDOM.nextFloat() * height);
            } while (!this.is(Movement.WALKABLE, chicken.tileX(), chicken.tileY()));
            entities.addActor(chicken);
        }

        collision = new QuadTree<>(0, new Rectangle(0, 0, width, height));

        //TODO max iterations!
        for (int i = 0; i < 800; ) {
            float x = MathUtil.RANDOM.nextFloat() * width;
            float y = MathUtil.RANDOM.nextFloat() * height;
            if (this.is(Movement.WALKABLE, x, y) && this.is(tile -> tile.material != Materials.SAND, x, y)) {
                Tree tree = new Tree(this, Tree.TYPES[i % Tree.TYPES.length]);
                tree.setPosition(x, y);
                if (!collision.bounds.contains(tree.getCollisionBox()) || collision.overlapsAny(tree.getCollisionBox())) {
                    continue;
                }
                i++;
                collision.add(tree);
                entities.addActor(tree);
            }
        }
    }

    /**
     * @return true iff entity moved
     */
    public boolean moveTo(Entity entity, float x, float y, Predicate<MapTile> movement) {
        boolean moved = false;
        if (is(movement, x, entity.getY()) && !collision.overlapsAny(entity.getCollisionBox(x, entity.getY()))) {
            entity.setX(x);
            moved = true;
        }
        if (is(movement, entity.getX(), y) && !collision.overlapsAny(entity.getCollisionBox(entity.getX(), y))) {
            entity.setY(y);
            return true;
        }
        return moved;
    }
}
