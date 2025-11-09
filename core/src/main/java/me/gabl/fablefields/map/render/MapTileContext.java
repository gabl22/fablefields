package me.gabl.fablefields.map.render;

import me.gabl.fablefields.map.logic.Address;
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

    public Address getAddress() {
        return new Address(y * chunk.width + x, layer);
    }

    public int position() {
        return y * chunk.width + x;
    }

    public int width() {
        return chunk.width;
    }

    public int height() {
        return chunk.height;
    }

    public MapTileContext up() {
        return getContext(x, y + 1);
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

    public MapTileContext getContext(int x, int y) {
        if (checkContains(x, y)) {
            return new MapTileContext(chunk, chunk.getTile(layer, y * width() + x), layer, x, y);
        }

        return new MapTileContext(chunk, null, layer, x, y + 1);
    }

    public MapTileContext inLayer(MapLayer layer) {
        return new MapTileContext(chunk, tile, layer, x, y);
    }

    private boolean checkContains(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }
}
