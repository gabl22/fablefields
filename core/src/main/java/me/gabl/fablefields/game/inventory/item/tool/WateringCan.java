package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.map.material.PlantTile;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.player.RunningAction;
import me.gabl.fablefields.task.eventbus.event.PlantGrowEvent;
import me.gabl.fablefields.task.eventbus.event.PlantWaterEvent;

public final class WateringCan extends Tool {

    WateringCan() {
        super("watering_can");
    }

    @Override
    public void use(UseContext context) {
        if (!context.chunkContainsTile()) return;
        PlantTile tile = (PlantTile) context.getTile(MapLayer.FEATURE); //due to check in  useable
        RunningAction waterPlant = RunningAction.get(Action.WATERING).copyAnimation();
        waterPlant.setOnFinished(() -> {
            tile.water();
            context.screen.eventBus.fire(new PlantWaterEvent(tile));
            context.updateCells();
        });
        context.player.replaceAction(waterPlant);
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.cursorInRange(Range.TOOL)) return false;
        if (context.getTile(MapLayer.FEATURE) instanceof PlantTile tile) {
            return tile.needsWater();
        }
        return false;
    }


    @Override
    public String getUseToolTip(UseContext context) {
        PlantMaterial material = ((PlantTile) context.getTile(MapLayer.FEATURE)).getMaterial();
        return tooltip("water_crop").replace("%crop%", language("material/" + material.id));
    }
}
