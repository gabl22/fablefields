package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.map.material.PlantMaterial;

//todo use concept (?)
public class Crop extends ItemType {

    public static final Crop CARROT = new Crop("carrot", 691 + 0);
    public static final Crop CAULIFLOWER = new Crop("cauliflower", 691 + 1);
    public static final Crop PUMPKIN = new Crop("pumpkin", 691 + 2);
    public static final Crop SUNFLOWER = new Crop("sunflower", 691 + 3);
    public static final Crop RADISH = new Crop("radish", 691 + 4);
    public static final Crop PARSNIP = new Crop("parsnip", 691 + 5);
    public static final Crop POTATO = new Crop("parsnip", 691 + 6);
    public static final Crop CABBAGE = new Crop("cabbage", 691 + 7);
    public static final Crop BEETROOT = new Crop("beetroot", 691 + 8);
    public static final Crop WHEAT = new Crop("wheat", 691 + 9);
    public static final Crop LETTUCE = new Crop("lettuce", 691 + 10);
    private final int tileSetId;

    public Crop(String id, int tileSetId) {
        super(id);
        this.tileSetId = tileSetId;
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

    @Override
    public Drawable render() {
        return Asset.TILESET.getDrawable(tileSetId);
    }
}
