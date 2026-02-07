package me.gabl.fablefields.game.inventory.item;

import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.map.material.Plant;

public class Crop extends ItemType {

    public static final Crop CARROT = new Crop("carrot");
    public static final Crop CAULIFLOWER = new Crop("cauliflower");
    public static final Crop PUMPKIN = new Crop("pumpkin");
    public static final Crop SUNFLOWER = new Crop("sunflower");
    public static final Crop RADISH = new Crop("radish");
    public static final Crop PARSNIP = new Crop("parsnip");
    public static final Crop POTATO = new Crop("parsnip");
    public static final Crop CABBAGE = new Crop("cabbage");
    public static final Crop BEETROOT = new Crop("beetroot");
    public static final Crop WHEAT = new Crop("wheat");
    public static final Crop LETTUCE = new Crop("lettuce");

    @Override
    public String getName() {
        return language("material/" + Plant.get(this).id);
    }

    public Crop(String id) {
        super(id);
    }
}
