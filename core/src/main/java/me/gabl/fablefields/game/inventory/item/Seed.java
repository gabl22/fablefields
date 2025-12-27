package me.gabl.fablefields.game.inventory.item;

import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.material.Plant;
import me.gabl.fablefields.map.material.PlantTile;
import me.gabl.fablefields.player.Range;

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
        PlantTile tile = Plant.get(this).material.createMapTile(context.getAddress(MapLayer.FEATURE), context.screen);
        context.setTile(MapLayer.FEATURE, tile);
        context.chunk.getRenderComponent().updateCells(context.x(), context.y());
        context.removeSelectedItem();
        tile.getGrowTask().schedule(context.screen.syncScheduler);

    }

    @Override
    protected String getUseToolTip() {
        return language("item/seed/tooltip/plant_crop").replace("%crop%", language("material/" + Plant.get(this).id));
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.cursorInRange(Range.PLACE)) {
            return false;
        }
        return Materials.SOIL.materialEquals(context.getTile(MapLayer.SURFACE)) && context.getTile(MapLayer.FEATURE) == null;
    }
}
