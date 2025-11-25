package me.gabl.fablefields.game.inventory.item;

import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.material.Material;
import me.gabl.fablefields.map.render.ContextAddress;
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
        return chunk.containsTile(x(), y());
    }

    public MapTile getTile(MapLayer layer) {
        return chunk.getTile(layer, chunk.position(x(), y()));
    }

    public ContextAddress getAddress(MapLayer layer) {
        return new ContextAddress(chunk.position(x(), y()), layer, chunk.width);
    }

    public void setTile(MapLayer layer, MapTile tile) {
        chunk.setTile(layer, chunk.position(x(), y()), tile);
    }

    //removes 1 from used item
    public void removeSelectedItem() {
        screen.inventoryHud.removeSelectedItem();
    }

    //material == null -> return null;
    public MapTile setTile(MapLayer layer, Material material) {
        ContextAddress address = getAddress(layer);
        chunk.setTile(material == null ? null : material.createMapTile(address, chunk), address);
        return chunk.getTile(address);
    }
}
