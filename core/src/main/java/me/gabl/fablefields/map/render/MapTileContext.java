package me.gabl.fablefields.map.render;

import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;

public class MapTileContext {

    public final MapChunk chunk;
    public final MapTile tile;
    public final MapLayer layer;
    public final int x;
    public final int y;

    public MapTileContext(MapChunk chunk, MapTile tile, MapLayer layer, int x, int y) {
        this.chunk = chunk;
        this.tile = tile;
        this.layer = layer;
        this.x = x;
        this.y = y;
    }

    public ContextAddress getAddress() {
        return new ContextAddress(y * chunk.width + x, layer, chunk.width);
    }

    public int position() {
        return y * chunk.width + x;
    }

    public MapTileContext up() {
        return getContext(x, y + 1);
    }

    public MapTileContext getContext(int x, int y) {
        if (checkContains(x, y)) {
            return new MapTileContext(chunk, chunk.getTile(layer, y * width() + x), layer, x, y);
        }

        return new MapTileContext(chunk, null, layer, x, y + 1);
    }

    private boolean checkContains(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }

    public int width() {
        return chunk.width;
    }

    public int height() {
        return chunk.height;
    }

    public MapTileContext down() {
        return getContext(x, y - 1);
    }

    public MapTileContext left() {
        return getContext(x - 1, y);
    }

    public MapTileContext right() {
        return getContext(x + 1, y);
    }

    public void rerender() {
        chunk.getRenderComponent().renderCell(layer, x, y, tile);
    }

    public MapTileContext inLayer(MapLayer layer) {
        return new MapTileContext(chunk, chunk.getTile(layer, x, y), layer, x, y);
    }
}
