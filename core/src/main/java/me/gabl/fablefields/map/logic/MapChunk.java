package me.gabl.fablefields.map.logic;

import lombok.Getter;
import lombok.Setter;
import me.gabl.fablefields.map.render.MapChunkRenderComponent;
import org.jetbrains.annotations.Nullable;

public class MapChunk {

    public final int width;
    public final int height;
    public final long seed;
    public final Noise2D noise;

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
        if (containsTile(position))
            return tileLayers.get(layer).tiles[position];
        return null;
    }

    public boolean containsTile(int position) {
        return position >= 0 && position < width * height;
    }

    public boolean containsTile(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isWalkable(float x, float y) {
        int fx = (int) Math.floor(x);
        int fy = (int) Math.floor(y);
        if (!containsTile(fx, fy))
            return false;
        return isWalkable(position(fx, fy));
    }

    @Nullable
    public MapTile getTile(MapLayer layer, float x, float y) {
        int fx = (int) Math.floor(x);
        int fy = (int) Math.floor(y);
        if (!containsTile(fx, fy))
            return null;
        return getTile(layer, position(fx, fy));
    }

    public boolean isWalkable(int position) {
        MapTile tile = getTile(MapLayer.GROUND, position);
        return tile != null && tile.isWalkable();
    }

    public int position(int x, int y) {
        return x + width * y;
    }
}
