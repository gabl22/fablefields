package me.gabl.fablefields.map;

import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapChunkLayers;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.render.Noise;

public class MapGenerator {

    private static final int size = 100; //TODO

    public static MapChunk getMap() {
        Noise noise = Noise.get(System.currentTimeMillis(), 1 / 100d);

        MapTile[] ground = new MapTile[size * size];
        MapTile[] feature = new MapTile[size * size];
        MapTile[] surface = new MapTile[size * size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                ground[x * size + y] = getMapTile(noise.getNoise(x, y));
                feature[x * size + y] = null;
                surface[x * size + y] = null;
            }
        }

        return new MapChunk(new MapChunkLayers<>(ground, feature, surface), size, size, noise.getSeed());
    }

    private static MapTile getMapTile(double noise) {
        if (noise > -0.0) {
            return new MapTile(Materials.GRASS);
        }
        if (noise > -0.2) {
            return new MapTile(Materials.DIRT);
        }
        if (noise > -0.5) {
            return new MapTile(Materials.SAND);
        }
        return new MapTile(Materials.WATER);
    }
}
