package me.gabl.fablefields.map.logic;

public class MapChunkLayer {

    public final MapTile[] tiles;

    public final int width;
    public final int height;

    public MapChunkLayer(MapTile[] tiles, int width, int height) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
    }
}
