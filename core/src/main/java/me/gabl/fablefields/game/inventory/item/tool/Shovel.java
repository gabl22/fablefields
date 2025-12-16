package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.material.Material;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.player.RunningAction;
import me.gabl.fablefields.task.eventbus.event.TillSoilEvent;
import me.gabl.fablefields.task.eventbus.event.UntillSoilEvent;

public final class Shovel extends Tool {

    Shovel() {
        super("shovel");
    }

    @Override
    public void use(UseContext context) {
        MapTile surfaceTile = context.getTile(MapLayer.SURFACE);
        Material old = surfaceTile == null ? null : surfaceTile.material;
        RunningAction soilGround = RunningAction.get(Action.DIG).copyAnimation();
        MapTile newMapTile = Material.equals(old, Materials.SOIL) ? null :
                Materials.SOIL.createMapTile(context.getAddress(MapLayer.SURFACE));
        soilGround.setOnFinished(() -> {
            context.setTile(MapLayer.SURFACE, newMapTile);
            context.setTile(MapLayer.FEATURE, (MapTile) null); //destroy crops no matter what
            if (Materials.SOIL.materialEquals(newMapTile)) context.screen.eventBus.fire(new TillSoilEvent());
            else context.screen.eventBus.fire(new UntillSoilEvent());
        });
        context.player.replaceAction(soilGround);
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.cursorInRange(Range.TOOL)) return false;
        return Materials.DIRT.materialEquals(context.chunk.getTile(MapLayer.GROUND, context.mouseX, context.mouseY));
    }

    @Override
    public String getUseToolTip(UseContext context) {
        MapTile surfaceTile = context.getTile(MapLayer.SURFACE);
        Material old = surfaceTile == null ? null : surfaceTile.material;
        if (old == Materials.SOIL) return tooltip("untill");
        else return tooltip("till");
    }
}
