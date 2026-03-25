package me.gabl.fablefields.game.inventory.item;

import me.gabl.fablefields.game.inventory.ItemType;

public class AnimalProduct extends ItemType {

    public static final AnimalProduct EGG = new AnimalProduct("egg");
    public static final AnimalProduct MILK_BUCKET = new AnimalProduct("milk_bucket");

    public AnimalProduct(String id) {
        super(id);
    }
}
