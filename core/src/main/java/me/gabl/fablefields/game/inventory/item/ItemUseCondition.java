package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.math.Vector2;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.game.inventory.entity.Entity;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;

public interface ItemUseCondition {

    //todo useable context? -> to itemtype?
    default boolean isUsable(Vector2 cursor, Item item, MapChunk chunk, Player player, Entity hitEntity) {
        return true;
    }
}
