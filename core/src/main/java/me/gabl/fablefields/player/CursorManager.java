package me.gabl.fablefields.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.gabl.fablefields.asset.Cursors;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.screen.game.GameScreen;

public class CursorManager {

    private final Player player;
    private final GameScreen screen;
    private final MapChunk chunk;

    private final Vector3 position3 = new Vector3();
    private final Vector2 position2 = new Vector2();

    public CursorManager(Player player, GameScreen screen, MapChunk chunk) {
        this.player = player;
        this.screen = screen;
        this.chunk = chunk;
    }

    public void update() {
        if (screen.camController.isDragging()) {
            Cursors.grab();
            return;
        }

        getCursorPosition(position2);
        Item selectedItem = player.inventory.getSelectedItem();
        if (selectedItem == null) {
            Cursors.pointer();
            return;
        }

        UseContext context = new UseContext(selectedItem, player, screen, chunk, position2.x, position2.y, screen.entityHitCursor());
        if (selectedItem.type.isUsable(context)) {
            Cursors.arrow();
        } else {
            Cursors.unavailable();
        }
    }

    public void getCursorPosition(Vector2 position) {
        position3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        screen.getViewport().unproject(position3);
        position.set(position3.x, position3.y);
    }
}
