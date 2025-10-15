package me.gabl.fablefields.game.inventory.item;

import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.game.GameScreen;

public class UseContext extends Context {

    public final float fx;
    public final float fy;

    public UseContext(Item item, Player player, GameScreen screen, MapChunk chunk, float fx, float fy) {
        super(item, player, screen, chunk);
        this.fx = fx;
        this.fy = fy;
    }

    public UseContext(Context context, float fx, float fy) {
        super(context);
        this.fx = fx;
        this.fy = fy;
    }

    public int x() {
        return (int) fx;
    }

    public int y() {
        return (int) fy;
    }

    public boolean chunkContainsTile() {
        return chunk.containsTileAt(x(), y());
    }

    public MapTile getTile(MapLayer layer) {
        return chunk.getTile(layer, chunk.position(x(), y()));
    }

    public void setTile(MapLayer layer, MapTile tile) {
        chunk.setTile(layer, chunk.position(x(), y()), tile);
    }
}
