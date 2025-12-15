package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.material.Plant;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.map.material.PlantTile;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.task.eventbus.event.PlantHarvestEvent;

public final class Hoe extends Tool {

    Hoe() {
        super("hoe");
    }

    @Override
    public void use(UseContext context) {
        if (!context.chunkContainsTile()) return;
        PlantTile tile = (PlantTile) context.getTile(MapLayer.FEATURE); //due to check in useable
        Plant plant = Plant.get(tile.getMaterial());
        int seeds = plant.material.dropChances.drawSeed();
        int crops = plant.material.dropChances.drawCrop();
        context.player.inventory.addItem(plant.crop.createItem(), crops);
        context.player.inventory.addItem(plant.seed.createItem(), seeds);
        context.chunk.setTile(null, context.getAddress(MapLayer.FEATURE));
        if (Materials.SOIL.materialEquals(context.getTile(MapLayer.SURFACE).material)) {
            context.setTile(MapLayer.SURFACE, (MapTile) null);
        }
        context.chunk.getRenderComponent().updateCells(context.x(), context.y());

        context.screen.eventBus.fire(new PlantHarvestEvent(tile, seeds, crops));
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.cursorInRange(Range.TOOL)) return false;
        if (context.getTile(MapLayer.FEATURE) instanceof PlantTile tile) {
            return tile.isFullyGrown();
        }
        return false;
    }


    @Override
    public String getUseToolTip(UseContext context) {
        PlantMaterial material = ((PlantTile) context.getTile(MapLayer.FEATURE)).getMaterial();
        return tooltip("harvest_crop").replace("%crop%", language("material/" + material.id));
    }
}
