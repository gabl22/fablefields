package me.gabl.fablefields.game.inventory.item.tool;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;

public class Tool extends ItemType {

    private final Drawable graphics;

    public Tool(String id, int tileSetId) {
        super(id);
        this.graphics = Asset.TILESET.getDrawable(tileSetId);
    }

    @Override
    public Drawable render() {
        return graphics;
    }
}
