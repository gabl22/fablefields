package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;

public class GenericItems {

    public static final ItemType WOOD = new ItemType("wood") {
        @Override
        public Drawable render() {
            return Asset.TILESET.getDrawable(753);
        }
    };

}
