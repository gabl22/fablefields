package me.gabl.fablefields.game.inventory.item;

import com.badlogic.gdx.scenes.scene2d.Actor;
import me.gabl.fablefields.game.entity.Entity;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.material.Material;
import me.gabl.fablefields.map.render.ContextAddress;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.screen.game.GameScreen;
import org.jetbrains.annotations.Nullable;

public class UseContext extends Context {

    public final float mouseX;
    public final float mouseY;
    public final @Nullable Actor hitActor;

    public UseContext(Item item, Player player, GameScreen screen, MapChunk chunk, float mouseX, float mouseY,
            @Nullable Actor hitActor) {
        super(item, player, screen, chunk);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.hitActor = hitActor;
    }

    public UseContext(Context context, float mouseX, float mouseY, @Nullable Actor hitActor) {
        super(context);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.hitActor = hitActor;
    }

    public boolean chunkContainsTile() {
        return chunk.containsTile(x(), y());
    }

    public int x() {
        return (int) mouseX;
    }

    public int y() {
        return (int) mouseY;
    }

    public MapTile getTile(MapLayer layer) {
        if (!chunk.containsTile(x(), y())) {
            return null;
        }
        return chunk.getTile(layer, chunk.position(x(), y()));
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
        chunk.setTile(material == null ? null : material.createMapTile(address, screen), address);
        return chunk.getTile(address);
    }

    public ContextAddress getAddress(MapLayer layer) {
        return new ContextAddress(chunk.position(x(), y()), layer, chunk.width);
    }

    public boolean cursorInRange(Range range) {
        return player.inRange(range, mouseX, mouseY);
    }

    public boolean tileInRange(Range range, int x, int y) {
        return player.inRange(range, x + 0.5f, y + 0.5f);
    }

    public boolean entityInRange(Range range, Entity entity) {
        return player.inRange(range, entity.getX(), entity.getY());
    }

    public void updateCells() {
        chunk.getRenderComponent().updateCells(x(), y());
    }

    public void updateCell(MapLayer layer) {
        chunk.getRenderComponent().updateCell(layer, x(), y());
    }
}
