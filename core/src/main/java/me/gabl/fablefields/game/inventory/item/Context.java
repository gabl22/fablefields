package me.gabl.fablefields.game.inventory.item;

import lombok.AllArgsConstructor;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.game.GameScreen;

@AllArgsConstructor
public class Context {
    public final Item item;
    public final Player player;
    public final GameScreen screen;
    public final MapChunk chunk;

    public Context(Context that) {
        this.item = that.item;
        this.player = that.player;
        this.screen = that.screen;
        this.chunk = that.chunk;
    }
}
