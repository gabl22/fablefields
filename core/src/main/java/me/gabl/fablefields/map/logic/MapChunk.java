package me.gabl.fablefields.map.logic;

import lombok.Getter;
import lombok.Setter;

public class MapChunk {

    public final int width;
    public final int height;
    public final long seed;
    public final Noise2D noise;

    private final MapChunkLayers<MapChunkLayer> tileLayers;
    @Setter
    @Getter
    private MapChunkRenderComponent renderComponent;

    public MapChunk(MapChunkLayers<MapTile[]> tileLayers, int width, int height, long seed) {
        assert !tileLayers.containsRenderLayers;

        this.seed = seed;
        this.noise = new Noise2D(seed);

        tileLayers.stream().forEach(tileLayer -> {
            assert tileLayer.length == width * height;
        });


        this.tileLayers = tileLayers.map(array -> new MapChunkLayer(array, width, height));
        this.width = width;
        this.height = height;
    }

    public void initRenderComponent() {
        renderComponent = new MapChunkRenderComponent(this);
    }

    MapChunkLayer getLayer(MapLayer layer) {
        return tileLayers.get(layer);
    }

    public void setTile(MapTile tile, Address address) {
        setTile(address.layer, address.position, tile);
    }

    public void setTile(MapLayer layer, int position, MapTile tile) {
        tileLayers.get(layer).tiles[position] = tile;
    }

    public MapTile getTile(Address address) {
        return getTile(address.layer, address.position);
    }

    public MapTile getTile(MapLayer layer, int position) {
        return tileLayers.get(layer).tiles[position];
    }
}
