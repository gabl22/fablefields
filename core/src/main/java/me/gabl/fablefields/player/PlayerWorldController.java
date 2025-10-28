package me.gabl.fablefields.player;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.game.inventory.Slot;
import me.gabl.fablefields.game.inventory.entity.Entity;
import me.gabl.fablefields.game.inventory.item.HitContext;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.screen.util.DefaultInputProcessor;
import me.gabl.fablefields.util.ScreenUtil;

@AllArgsConstructor
public class PlayerWorldController implements DefaultInputProcessor {

    private final Player player;
    private final MapChunk chunk;
    private final GameScreen screen;
    private final Vector2 screenVector = new Vector2();

    // only called if not dragged, since drag is interrupted by CamController
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenVector.set(screenX, screenY);
        checkUse();
        return true;
    }

    @SuppressWarnings("unchecked")
    public void checkUse() {
        Slot slot = player.inventory.getSelectedSlot();
        // todo eventbus calls
        // todo check if hit instanceof entity
        if (slot == null)
            return;
        Entity hitEntity = screen.entityHitCursor();
        Vector2 position = ScreenUtil.getPosition(screenVector.x, screenVector.y, screen);
        if (!slot.item.type.isUsable(position, slot.item, chunk, player, hitEntity)) {
            return;
        }

        if (hitEntity == null) {
            slot.item.type.use(new UseContext(slot.item, player, screen, chunk, position.x, position.y));
        } else {
            slot.item.type.hit(new HitContext(slot.item, player, screen, chunk, hitEntity));
        }
    }
}
