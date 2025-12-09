package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.item.UseContext;
import org.jetbrains.annotations.NotNull;

public class ItemType {

    @NotNull
    public final String id;

    public ItemType(@NotNull String id) {
        this.id = id;
    }

    public Drawable render() {
        return Asset.REGISTRY.getDrawable("item/" + this.id);
    }

    public void use(UseContext context) {
    }

    public boolean isUsable(UseContext context) {
        return false;
    }

    public String getUseToolTip() {
        return null;
    }

    public String tooltip(String name) {
        return Asset.LANGUAGE_SERVICE.get("item/"+this.id+"/tooltip/"+name);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ItemType type)) return false;

        return id.equals(type.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @NotNull
    public Item createItem() {
        return new Item(this);
    }
}
