package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.item.UseContext;

public class ItemType {

    public final String id;

    public ItemType(String id) {
        this.id = id;
    }

    public Drawable render() {
        return Asset.REGISTRY.getDrawable("item/" + this.id);
    }

    public void use(UseContext context) {
    }

    public boolean isUsable(UseContext context) {
        return true;
    }

    public Item createItem() {
        return new Item(this);
    }
}
