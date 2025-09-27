package me.gabl.fablefields.map.logic;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class MapChunk {

    public static final int WIDTH_HEIGHT = 1000;

    private MapChunkLayers<MapChunkLayer> tileLayers;

    public final int width;
    public final int height;

    @Setter @Getter
    private MapChunkRenderComponent renderComponent;

    public void initRenderComponent() {
        renderComponent = new MapChunkRenderComponent(this);
    }

    MapChunkLayer getLayer(MapLayer layer) {
        return tileLayers.get(layer);
    }

    public MapChunk(MapChunkLayers<MapTile[]> tileLayers, int width, int height) {
        assert !tileLayers.containsRenderLayers;

        tileLayers.stream().forEach(tileLayer -> {
            assert tileLayer.length == width * height;
//            for (MapTile tile : tileLayer) {
//                assert tile != null;
//            }
        });


        this.tileLayers = tileLayers.map(array -> new MapChunkLayer(array, width, height));
        this.width = width;
        this.height = height;
    }

    public void setTile(MapLayer layer, int position, MapTile tile) {
        tileLayers.get(layer).tiles[position] = tile;
    }

    public void setTile(MapTile tile, Address address) {
        setTile(address.layer, address.position, tile);
    }


    public MapTile getTile(MapLayer layer, int position) {
        return tileLayers.get(layer).tiles[position];
    }

    public MapTile getTile(Address address) {
        return getTile(address.layer, address.position);
    }
}
