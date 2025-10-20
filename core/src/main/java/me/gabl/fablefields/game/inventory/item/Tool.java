package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.map.Material;
import me.gabl.fablefields.map.Materials;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.RunningAction;
import me.gabl.fablefields.util.GdxLogger;

import java.util.function.Supplier;

public final class Tool extends ItemType {

    public static final Tool SHOVEL = new Tool("shovel", () -> Asset.TILESET.getDrawable(3114));
    public static final Tool SWORD = new Tool("sword", () -> Asset.TILESET.getDrawable(3050));
    public static final Tool HOE = new Tool("hoe", () -> Asset.TILESET.getDrawable(2986));
    public static final Tool AXE = new Tool("axe", () -> Asset.TILESET.getDrawable(2922));
    public static final Tool PICKAXE = new Tool("pickaxe", () -> Asset.TILESET.getDrawable(3113));
    public static final Tool WATERING_CAN = new Tool("watering_can", () -> Asset.TILESET.getDrawable(2858));

    private final Supplier<Drawable> graphics;

    public Tool(String id, Supplier<Drawable> graphics) {
        super(id);
        this.graphics = graphics;
    }

    @Override
    public Drawable render() {
        return graphics.get();
    }

    @Override
    public void use(UseContext context) {
        if (!context.chunkContainsTile()) return;
        if (context.item.typeEquals(HOE)) {
            if (Materials.DIRT.materialEquals(context.getTile(MapLayer.GROUND))) {
                MapTile tile = context.getTile(MapLayer.SURFACE);
                Material old = tile == null ? null : tile.material;
                if (Material.equals(old, Materials.SOIL)) {
                    context.setTile(MapLayer.SURFACE, null);
                } else {
                    context.setTile(MapLayer.SURFACE, new MapTile(Materials.SOIL));
                }
            }
        }

        if (context.item.typeEquals(AXE)) {
            context.player.replaceAction(RunningAction.get(Action.AXE));
        }
    }

    @Override
    public void hit(HitContext context) {
        if (context.item.typeEquals(SWORD)) {
            context.hitActor.remove(); //todo damage?
        }
    }
}
