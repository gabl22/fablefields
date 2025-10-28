package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.game.inventory.entity.Animal;
import me.gabl.fablefields.game.inventory.entity.Entity;
import me.gabl.fablefields.map.Material;
import me.gabl.fablefields.map.Materials;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.player.RunningAction;

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
            //            context.hitActor.remove(); //todo damage?
            if (context.hitActor instanceof Animal) {
                Animal animal = (Animal) context.hitActor;
                animal.inflictDamage(0.0f);
            }
        }
    }

    @Override
    public boolean isUsable(Vector2 cursor, Item item, MapChunk chunk, Player player, Entity entity) {
        Range range = null;
        if (item.typeEquals(SWORD)) {
            range = Range.WEAPON_SHORT;
        } else if (item.typeEquals(HOE) || item.typeEquals(AXE) || item.typeEquals(PICKAXE) || item.typeEquals(WATERING_CAN) || item.typeEquals(SHOVEL)) {
            range = Range.TOOL;
        }
        if (!player.inRange(range, cursor)) {
            return false;
        }

        if (item.typeEquals(SWORD)) {
            return entity != null && !(entity instanceof Player);
        }
        if (item.typeEquals(HOE)) {
            return Materials.DIRT.materialEquals(chunk.getTile(MapLayer.GROUND, cursor.x, cursor.y));
        }

        return true;
    }
}
