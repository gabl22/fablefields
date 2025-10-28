package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.game.inventory.item.HitContext;
import me.gabl.fablefields.game.inventory.item.ItemUseCondition;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;

public class ItemType<I extends Item> implements ItemUseCondition<I> {

    public final String id;

    public ItemType(String id) {
        this.id = id;
    }

    public Drawable render() {
        return null;
    }

    public void use(UseContext context) {}

    public void hit(HitContext context) {}
}
