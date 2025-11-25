package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.game.inventory.entity.Entity;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.map.material.PlantTile;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.util.GdxLogger;

import java.util.function.Supplier;

public final class Seed extends ItemType {

    public static final Seed CARROT = new Seed("carrot_seed", () -> Asset.TILESET.getDrawable(755 + 0));
    public static final Seed CAULIFLOWER = new Seed("cauliflower_seed", () -> Asset.TILESET.getDrawable(755 + 1));
    public static final Seed PUMPKIN = new Seed("pumpkin_seed", () -> Asset.TILESET.getDrawable(755 + 2));
    public static final Seed SUNFLOWER = new Seed("sunflower_seed", () -> Asset.TILESET.getDrawable(755 + 3));
    public static final Seed RADISH = new Seed("radish_seed", () -> Asset.TILESET.getDrawable(755 + 4));
    public static final Seed PARSNIP = new Seed("parsnip_seed", () -> Asset.TILESET.getDrawable(755 + 5));
    public static final Seed CABBAGE = new Seed("cabbage_seed", () -> Asset.TILESET.getDrawable(755 + 6));
    public static final Seed BEETROOT = new Seed("beetroot_seed", () -> Asset.TILESET.getDrawable(755 + 7));
    public static final Seed LETTUCE = new Seed("lettuce_seed", () -> Asset.TILESET.getDrawable(755 + 8));

    private final Supplier<Drawable> graphics;

    public Seed(String id, Supplier<Drawable> graphics) {
        super(id);
        this.graphics = graphics;
    }

    @Override
    public Drawable render() {
        return graphics.get();
    }

    @Override //todo differentiation item <-> type really needed?
    public void use(UseContext context) {
        PlantMaterial material = getMaterial(this);
        //        PlantMaterial material = getMaterial((Seed) context.item.type); <- complicated way
        if (material != null) { //should never be null!
            PlantTile tile = material.createMapTile(context.getAddress(MapLayer.FEATURE), context.chunk);
            context.setTile(MapLayer.FEATURE, tile);
            context.chunk.getRenderComponent().updateCells(context.x(), context.y());
            context.removeSelectedItem();
            tile.getGrowTask().schedule(context.screen.syncScheduler);
        } else {
            GdxLogger.get().error("No tile found for seed: " + id + " #okdtva");
        }

    }

    public static PlantMaterial getMaterial(Seed seed) {
        return switch (seed.id) {
            case "carrot_seed" -> Materials.CARROT;
            case "cauliflower_seed" -> Materials.CAULIFLOWER;
            case "pumpkin_seed" -> Materials.PUMPKIN;
            case "sunflower_seed" -> Materials.SUNFLOWER;
            case "radish_seed" -> Materials.RADISH;
            case "parsnip_seed" -> Materials.PARSNIP;
            case "cabbage_seed" -> Materials.CABBAGE;
            case "beetroot_seed" -> Materials.BEETROOT;
            case "lettuce_seed" -> Materials.LETTUCE;
            default -> null;
        };
    }

    @Override
    public boolean isUsable(Vector2 cursor, Item item, MapChunk chunk, Player player, Entity hitEntity) {
        if (!player.inRange(Range.PLACE, cursor)) {
            return false;
        }

        return Materials.SOIL.materialEquals(chunk.getTile(MapLayer.SURFACE, cursor.x, cursor.y)) && chunk.getTile(
            MapLayer.FEATURE, cursor.x, cursor.y) == null;
    }
}
