package me.gabl.fablefields.player;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.game.inventory.Slot;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.screen.util.DefaultInputProcessor;
import me.gabl.fablefields.task.eventbus.event.ItemUseEvent;
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

    public void checkUse() {
        Slot slot = player.inventory.getSelectedSlot();
        if (slot == null) return;
        Vector2 position = ScreenUtil.getPosition(screenVector.x, screenVector.y, screen);
        UseContext context = new UseContext(slot.item, player, screen, chunk, position.x, position.y,
                screen.entityHitCursor());
        if (!slot.item.type.isUsable(context)) {
            return;
        }
        turnPlayer(context);
        slot.item.type.use(context);
        screen.eventBus.fire(new ItemUseEvent(context));
    }

    public void turnPlayer(UseContext context) {
        if (context.hitActor != null) player.turnTo(context.hitActor.getX());
        else player.turnTo(context.x() + 0.5f);
    }
}
