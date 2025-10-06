package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;

import java.util.function.Supplier;

public final class Seed extends ItemType {

    public static final Seed CARROT = new Seed("carrot", () -> Asset.TILESET.getDrawable(755));
    private final Supplier<Drawable> graphics;

    public Seed(String id, Supplier<Drawable> graphics) {
        super(id);
        this.graphics = graphics;
    }

    @Override
    public Drawable render() {
        return graphics.get();
    }
}
