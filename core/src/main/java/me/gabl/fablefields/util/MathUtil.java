package me.gabl.fablefields.util;

import java.util.Random;

public class MathUtil {

    public static final float INV_SQRT2 = (float) (Math.sqrt(2) / 2);

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    public static final float[] dir4 = {0, -1, 1, 0, 0, 1, -1, 0};
    public static final float[] dir8 = {0, -1,
        INV_SQRT2, -INV_SQRT2,
        1, 0,
        INV_SQRT2, INV_SQRT2,
        0, 1,
        -INV_SQRT2, INV_SQRT2,
        -1, 0,
        -INV_SQRT2, -INV_SQRT2
    };

    public float[] random(float[] dir) {
        int direction = RANDOM.nextInt(dir.length/2);
        return new float[] {dir[direction], dir[direction + 1]};
    }
}
