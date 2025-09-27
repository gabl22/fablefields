package me.gabl.fablefields.map;

import de.articdive.jnoise.core.api.noisegen.NoiseGenerator;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapChunkLayers;
import me.gabl.fablefields.map.logic.MapTile;

public class MapGenerator {

    private static final int size = 1500; //TODO

    public static MapChunk getMapx() {
        NoiseGenerator noise = FastSimplexNoiseGenerator.newBuilder().setSeed(5).setVariant2D(Simplex2DVariant.CLASSIC)
            .build();

        MapTile[] ground = new MapTile[size * size];
        MapTile[] feature = new MapTile[size * size];
        MapTile[] surface = new MapTile[size * size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                ground[x * size + y] = getMapTile(noise.evaluateNoise(x / 150d, y / 150d));
                feature[x * size + y] = null;
                surface[x * size + y] = null;
            }
        }

        MapChunk chunk = new MapChunk(new MapChunkLayers<>(ground, feature, surface), size, size);
        return chunk;
    }

    private static MapTile getMapTile(double noise) {
        if (noise > -0.2) {
            return new MapTile(Material.DIRT);
        }
        if (noise > -0.4) {
            return new MapTile(Material.SAND);
        }
        return new MapTile(Material.WATER);
    }
}
