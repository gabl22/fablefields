package me.gabl.fablefields.map.logic;

public class Noise2D {

    private final long seed;

    public Noise2D(long seed) {
        this.seed = seed;
    }

    // [0; 1)
    public double getValue(double x, double y) {
        return normalize(getLongValue(x, y));
    }

    private static double normalize(long longValue) {
        // nicht negativ
        return (longValue >>> 1) / (double) Long.MAX_VALUE;
    }

    // Deterministic noise function: (x, y) -> int
    public long getLongValue(double x, double y) {
        long bitsX = Double.doubleToLongBits(x);
        long bitsY = Double.doubleToLongBits(y);

        long combined = seed;
        combined ^= bitsX * 0x632BE59BD9B4E019L + 0xfdfef13e5fde19b6L;
        combined ^= bitsY * 0x9E3779B97F4A7C15L + 0x0167bc4cc7314858L;
        combined += 0xf3e72aa1cbe75f0aL;

        // scramble bits (SplitMix64)
        combined = (combined ^ (combined >>> 30)) * 0xBF58476D1CE4E5B9L;
        combined = (combined ^ (combined >>> 27)) * 0x94D049BB133111EBL;
        combined = combined ^ (combined >>> 31);

        return combined;
    }
}
