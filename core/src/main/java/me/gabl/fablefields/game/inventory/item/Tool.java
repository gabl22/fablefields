package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;

import java.util.function.Supplier;

public final class Tool extends ItemType {

    public static final Seed SHOVEL = new Seed("shovel", () -> Asset.TILESET.getDrawable(3114));
    public static final Seed SWORD = new Seed("sword", () -> Asset.TILESET.getDrawable(3050));
    public static final Seed HOE = new Seed("hoe", () -> Asset.TILESET.getDrawable(2986));
    public static final Seed AXE = new Seed("axe", () -> Asset.TILESET.getDrawable(2922));
    public static final Seed PICKAXE = new Seed("pickaxe", () -> Asset.TILESET.getDrawable(3113));
    public static final Seed WATERING_CAN = new Seed("watering_can", () -> Asset.TILESET.getDrawable(2858));

    private final Supplier<Drawable> graphics;

    public Tool(String id, Supplier<Drawable> graphics) {
        super(id);
        this.graphics = graphics;
    }

    @Override
    public Drawable render() {
        return graphics.get();
    }
}
