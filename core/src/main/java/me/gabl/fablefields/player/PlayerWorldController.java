package me.gabl.fablefields.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.game.entity.Cow;
import me.gabl.fablefields.game.entity.Egg;
import me.gabl.fablefields.game.inventory.Slot;
import me.gabl.fablefields.game.inventory.item.AnimalProduct;
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
        Vector2 position = ScreenUtil.getPosition(screenVector.x, screenVector.y, screen);
        Actor hitActor = screen.entityHitCursor();

        // No item selected - try bare-hand interactions
        if (slot == null) {
            checkBareHandUse(position, hitActor);
            return;
        }

        UseContext context = new UseContext(slot.item, player, screen, chunk, position.x, position.y, hitActor);
        if (!slot.item.type.isUsable(context)) {
            return;
        }
        turnPlayer(context);
        slot.item.type.use(context);
        screen.eventBus.fire(new ItemUseEvent(context));
    }

    private void checkBareHandUse(Vector2 position, Actor hitActor) {
        if (hitActor instanceof Egg egg) {
            if (!player.inRange(Range.TOOL, egg.getX(), egg.getY())) return;
            player.turnTo(egg.getX());
            egg.remove();
            player.inventory.addItem(AnimalProduct.EGG, 1);
        } else if (hitActor instanceof Cow cow) {
            if (!cow.isMilkable()) return;
            if (!player.inRange(Range.TOOL, cow.getX(), cow.getY())) return;
            player.turnTo(cow.getX());
            cow.milk();
            player.inventory.addItem(AnimalProduct.MILK_BUCKET, 1);
        }
    }

    public void turnPlayer(UseContext context) {
        if (context.hitActor != null) player.turnTo(context.hitActor.getX());
        else player.turnTo(context.x() + 0.5f);
    }
}
