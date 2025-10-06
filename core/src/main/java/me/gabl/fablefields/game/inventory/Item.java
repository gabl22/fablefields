package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

//TODO
public class Item {

    public final ItemType type;

    public Item(ItemType type) {
        this.type = type;
    }

    public boolean typeEquals(Item other) {
        return this.type == other.type;
    }

    public boolean typeEquals(ItemType otherType) {
        return this.type == otherType;
    }

    public Drawable render() {
        return type.render();
    }

    public boolean mergeableWith(Item other) {
        return typeEquals(other);
    }
}
