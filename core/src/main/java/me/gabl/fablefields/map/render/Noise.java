package me.gabl.fablefields.map.render;

import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;

public class Noise {

    private final FastSimplexNoiseGenerator noise;
    private final double factor;

    public Noise(FastSimplexNoiseGenerator noise, double factor) {
        this.noise = noise;
        this.factor = factor;
    }

    public static Noise get(long seed, double factor) {
        return new Noise(FastSimplexNoiseGenerator.newBuilder().setSeed(seed).setVariant2D(Simplex2DVariant.CLASSIC)
            .build(), factor
        );
    }

    public double getNoise(int x, int y) {
        return noise.evaluateNoise(x * factor, y * factor);
    }

    public long getSeed() {
        return noise.getSeed();
    }
}
