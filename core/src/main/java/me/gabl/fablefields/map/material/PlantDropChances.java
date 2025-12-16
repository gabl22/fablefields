package me.gabl.fablefields.map.material;

import lombok.AllArgsConstructor;
import me.gabl.fablefields.util.MathUtil;

@AllArgsConstructor
public class PlantDropChances {

    public final float[] seedWeights;
    public final float[] cropWeights;

    public static final PlantDropChances CARROT = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances CAULIFLOWER = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances PUMPKIN = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances SUNFLOWER = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances RADISH = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances PARSNIP = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances POTATO = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances CABBAGE = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances BEETROOT = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances WHEAT = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});
    public static final PlantDropChances LETTUCE = new PlantDropChances(new float[] { 5, 85, 10}, new float[] { 0, 96, 3, 1});


    public int drawSeed() {
        return MathUtil.randomWeighted(seedWeights);
    }

    public int drawCrop() {
        return MathUtil.randomWeighted(cropWeights);
    }
}
