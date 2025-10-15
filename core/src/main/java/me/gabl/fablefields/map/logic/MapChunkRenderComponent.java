package me.gabl.fablefields.map.logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.render.MapTileContext;

@AllArgsConstructor
public class MapChunkRenderComponent {

    public final MapChunkLayers<TiledMapTileLayer> layers;
    public final TiledMap map;

    private final MapChunk chunk;

    public MapChunkRenderComponent(MapChunk chunk) {
        this.map = new TiledMap();
        this.chunk = chunk;
        this.layers = new MapChunkLayers<>(() -> getGfxLayer(chunk.width, chunk.height));
        initMap();
    }

    private void initMap() {
        map.getTileSets().addTileSet(Asset.TILESET);
        map.getLayers().add(layers.ground);
        map.getLayers().add(layers.gfx_ground);
        map.getLayers().add(layers.surface);
        map.getLayers().add(layers.gfx_surface);
        map.getLayers().add(layers.feature);
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
    }


    void renderCell(Address address, MapTile tile) {
        renderCell(address.layer, address.x(chunk.width), address.y(chunk.width), tile);
    }

    void renderCell(MapLayer layer, int position, MapTile tile) {
        renderCell(layer, position % chunk.width, position / chunk.width, tile);
    }

    void renderCell(MapLayer layer, int x, int y, MapTile tile) {
        TiledMapTileLayer.Cell cell;
        TiledMapTileLayer.Cell gfxCell;

        if (tile != null && tile.material != null) {
            Cell.GfxPair cells = tile.material.generateCell(new MapTileContext(chunk, tile, layer, x, y));
            cell = cells.cell;
            gfxCell = cells.gfxCell;
        } else {
            cell = null;
            gfxCell = null;
        }
        set(layer, x, y, cell);
        if (layer.hasGfxLayer()) {
            set(layer.getGfxLayer(), x, y, gfxCell);
        }
    }

    private void set(MapLayer absolute, int x, int y, TiledMapTileLayer.Cell cell) {
        layers.get(absolute).setCell(x, y, cell);
    }

    private static TiledMapTileLayer getGfxLayer(int width, int height) {
        return new TiledMapTileLayer(width, height, 16, 16);
    }
}
