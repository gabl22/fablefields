package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.inventory.item.Crop;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.material.PlantTile;
import me.gabl.fablefields.player.Range;

public final class Hoe extends Tool {

    Hoe() {
        super("hoe", 2986);
    }

    @Override
    public void use(UseContext context) {
        if (!context.chunkContainsTile()) return;
        PlantTile tile = (PlantTile) context.getTile(MapLayer.FEATURE); //due to check in useable
        Crop crop = Crop.getCrop(tile.getMaterial());
        context.player.inventory.addItem(crop.createItem(), 1);
        context.chunk.setTile(null, context.getAddress(MapLayer.FEATURE));
        if (Materials.SOIL.materialEquals(context.getTile(MapLayer.SURFACE).material)) {
            context.setTile(MapLayer.SURFACE, (MapTile) null);
        }
        context.chunk.getRenderComponent().updateCells(context.x(), context.y());
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.playerInCursorRange(Range.TOOL)) return false;
        if (context.getTile(MapLayer.FEATURE) instanceof PlantTile tile) {
            return tile.isFullyGrown();
        }
        return false;
    }
}
