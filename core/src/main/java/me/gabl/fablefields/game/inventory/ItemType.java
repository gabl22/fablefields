package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.game.inventory.item.HitContext;
import me.gabl.fablefields.game.inventory.item.ItemUseCondition;
import me.gabl.fablefields.game.inventory.item.UseContext;

public class ItemType implements ItemUseCondition {

    public final String id;

    public ItemType(String id) {
        this.id = id;
    }

    public Drawable render() {
        return null;
    }

    public void use(UseContext context) {
    }

    public void hit(HitContext context) {
    }

    public Item createItem() {
        return new Item(this);
    }
}
