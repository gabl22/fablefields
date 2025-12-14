package me.gabl.fablefields.game.inventory.item;

import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.map.material.PlantTile;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.util.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Seed extends ItemType {

    public static final Seed CARROT = new Seed("carrot_seed");
    public static final Seed CAULIFLOWER = new Seed("cauliflower_seed");
    public static final Seed PUMPKIN = new Seed("pumpkin_seed");
    public static final Seed SUNFLOWER = new Seed("sunflower_seed");
    public static final Seed RADISH = new Seed("radish_seed");
    public static final Seed PARSNIP = new Seed("parsnip_seed");
    public static final Seed POTATO = new Seed("potato_seed");
    public static final Seed CABBAGE = new Seed("cabbage_seed");
    public static final Seed BEETROOT = new Seed("beetroot_seed");
    public static final Seed WHEAT = new Seed("wheat_seed");
    public static final Seed LETTUCE = new Seed("lettuce_seed");

    private Seed(String id) {
        super(id);
    }

    @Override //todo differentiation item <-> type really needed?
    public void use(UseContext context) {
        PlantMaterial material = getMaterial(this);
        //        PlantMaterial material = getMaterial((Seed) context.item.type); <- complicated way
        PlantTile tile = material.createMapTile(context.getAddress(MapLayer.FEATURE), context.screen);
        context.setTile(MapLayer.FEATURE, tile);
        context.chunk.getRenderComponent().updateCells(context.x(), context.y());
        context.removeSelectedItem();
        tile.getGrowTask().schedule(context.screen.syncScheduler);

    }

    @Override
    protected String getUseToolTip() {
        return language("item/seed/plant_crop").replace("%crop%", language("material/" + getMaterial(this).id));
    }

    @Contract("_ -> !null")
    public static PlantMaterial getMaterial(@NotNull Seed seed) {
        return switch (seed.id) {
            case "carrot_seed" -> Materials.CARROT;
            case "cauliflower_seed" -> Materials.CAULIFLOWER;
            case "pumpkin_seed" -> Materials.PUMPKIN;
            case "sunflower_seed" -> Materials.SUNFLOWER;
            case "radish_seed" -> Materials.RADISH;
            case "parsnip_seed" -> Materials.PARSNIP;
            case "potato_seed" -> Materials.POTATO;
            case "cabbage_seed" -> Materials.CABBAGE;
            case "beetroot_seed" -> Materials.BEETROOT;
            case "wheat_seed" -> Materials.WHEAT;
            case "lettuce_seed" -> Materials.LETTUCE;
            default -> null;
        };
    }

    @Contract("_ -> !null")
    public static Seed getSeed(@NotNull PlantMaterial material) {
        return switch (material.id) {
            case "carrot" -> CARROT;
            case "cauliflower" -> CAULIFLOWER;
            case "pumpkin" -> PUMPKIN;
            case "sunflower" -> SUNFLOWER;
            case "radish" -> RADISH;
            case "parsnip" -> PARSNIP;
            case "potato" -> POTATO;
            case "cabbage" -> CABBAGE;
            case "beetroot" -> BEETROOT;
            default -> null;
        };
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.cursorInRange(Range.PLACE)) {
            return false;
        }
        return Materials.SOIL.materialEquals(context.getTile(MapLayer.SURFACE)) && context.getTile(MapLayer.FEATURE) == null;
    }
}
