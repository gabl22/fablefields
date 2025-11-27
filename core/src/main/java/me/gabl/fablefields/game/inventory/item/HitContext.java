package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.Actor;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.game.GameScreen;

@Deprecated
public class HitContext extends Context {

    public final Actor hitActor;

    public HitContext(Item item, Player player, GameScreen screen, MapChunk chunk, Actor hitActor) {
        super(item, player, screen, chunk);
        this.hitActor = hitActor;
    }
}
