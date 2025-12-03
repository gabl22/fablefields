package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.map.material.PlantMaterial;

//todo use concept (?)
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

    public Crop(String id) {
        super(id);
    }

    public static Crop getCrop(PlantMaterial plantMaterial) {
        return switch (plantMaterial.id) {
            case "carrot" -> CARROT;
            case "cauliflower" -> CAULIFLOWER;
            case "pumpkin" -> PUMPKIN;
            case "sunflower" -> SUNFLOWER;
            case "radish" -> RADISH;
            case "parsnip" -> PARSNIP;
            case "potato" -> POTATO;
            case "cabbage" -> CABBAGE;
            case "beetroot" -> BEETROOT;
            case "wheat" -> WHEAT;
            case "lettuce" -> LETTUCE;
            default -> null;
        };
    }
}
