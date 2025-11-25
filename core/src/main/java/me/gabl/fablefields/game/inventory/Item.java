package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Objects;

//TODO
public class Item {

    public final ItemType type;

    public Item(ItemType type) {
        this.type = type;
    }

    public boolean typeEquals(ItemType otherType) {
        return this.type == otherType;
    }

    public Drawable render() {
        return type.render();
    }

    public boolean mergeableWith(Item other) {
        return equals(other);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Item item))
            return false;

        return Objects.equals(type, item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }
}
