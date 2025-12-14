package me.gabl.fablefields.util;

import java.util.Random;

public class MathUtil {

    public static final float INV_SQRT2 = (float) (Math.sqrt(2) / 2);

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    public static final float[] dir4 = {0, -1, 1, 0, 0, 1, -1, 0};
    public static final float[] dir8 = {0, -1, INV_SQRT2, -INV_SQRT2, 1, 0, INV_SQRT2, INV_SQRT2, 0, 1, -INV_SQRT2,
            INV_SQRT2, -1, 0, -INV_SQRT2, -INV_SQRT2};

    public static float[] random(float[] dir) {
        int direction = RANDOM.nextInt(dir.length / 2);
        return new float[]{dir[direction], dir[direction + 1]};
    }

    /**
     * Returns a random index from the given weights.
     * @param weights to use to calculate the random index. Assure each weight is non-negative.
     * @return If weights is empty, returns 0, else the random index.
     */
    public static int randomWeighted(float[] weights) {
        if (weights.length == 0) return 0;
        float sum = 0;
        for (float weight : weights) sum += weight;
        float r = RANDOM.nextFloat() * sum;
        for (int i = 0; i < weights.length; i++) {
            r -= weights[i];
            if (r <= 0) return i;
        }
        return 0;
    }
}
