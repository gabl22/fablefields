package me.gabl.fablefields.map.material;

import me.gabl.fablefields.game.inventory.item.Crop;
import me.gabl.fablefields.game.inventory.item.Seed;

import java.util.HashMap;
import java.util.Map;

public class Plant {

    public final String id;
    public final Crop crop;
    public final PlantMaterial material;
    public final Seed seed;

    Plant(String id, Crop crop, PlantMaterial material, Seed seed) {
        this.id = id;
        this.crop = crop;
        this.material = material;
        this.seed = seed;
    }

    public static Plant get(String id) {
        return PLANT_TYPES.get(id);
    }

    public static Plant get(Crop crop) {
        return get(crop.id);
    }

    public static Plant get(PlantMaterial material) {
        return get(material.id);
    }

    public static Plant get(Seed seed) {
        return get(seed.id);
    }

    private static final Map<String, Plant> PLANT_TYPES = new HashMap<>();

    public static final Plant CARROT = new Plant("carrot", Crop.CARROT, Materials.CARROT, Seed.CARROT);
    public static final Plant CAULIFLOWER = new Plant("cauliflower", Crop.CAULIFLOWER, Materials.CAULIFLOWER, Seed.CAULIFLOWER);
    public static final Plant PUMPKIN = new Plant("pumpkin", Crop.PUMPKIN, Materials.PUMPKIN, Seed.PUMPKIN);
    public static final Plant SUNFLOWER = new Plant("sunflower", Crop.SUNFLOWER, Materials.SUNFLOWER, Seed.SUNFLOWER);
    public static final Plant RADISH = new Plant("radish", Crop.RADISH, Materials.RADISH, Seed.RADISH);
    public static final Plant PARSNIP = new Plant("parsnip", Crop.PARSNIP, Materials.PARSNIP, Seed.PARSNIP);
    public static final Plant POTATO = new Plant("potato", Crop.POTATO, Materials.POTATO, Seed.POTATO);
    public static final Plant CABBAGE = new Plant("cabbage", Crop.CABBAGE, Materials.CABBAGE, Seed.CABBAGE);
    public static final Plant BEETROOT = new Plant("beetroot", Crop.BEETROOT, Materials.BEETROOT, Seed.BEETROOT);
    public static final Plant WHEAT = new Plant("wheat", Crop.WHEAT, Materials.WHEAT, Seed.WHEAT);
    public static final Plant LETTUCE = new Plant("lettuce", Crop.LETTUCE, Materials.LETTUCE, Seed.LETTUCE);

    public static final Plant[] VALUES = new Plant[]{CARROT, CAULIFLOWER, PUMPKIN, SUNFLOWER, RADISH, PARSNIP, POTATO, CABBAGE, BEETROOT, WHEAT, LETTUCE};

    static {
        for (Plant plant : VALUES) {
            PLANT_TYPES.put(plant.id, plant);
        }
    }

}
