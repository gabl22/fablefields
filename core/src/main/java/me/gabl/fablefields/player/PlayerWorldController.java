package me.gabl.fablefields.player;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.game.inventory.Slot;
import me.gabl.fablefields.game.inventory.item.HitContext;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.screen.util.DefaultInputProcessor;

@AllArgsConstructor
public class PlayerWorldController implements DefaultInputProcessor {

    private final Player player;
    private final MapChunk chunk;
    private final GameScreen screen;

    // only called if not dragged, since drag is interrupted by CamController
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Slot slot = screen.inventory.getSelectedSlot();
        // need (!) to unproject using viewport (zoom)
        Vector3 position = screen.getViewport().unproject(new Vector3(screenX, screenY, 0));
        // todo eventbus calls
        // todo check if hit instanceof entity
        Actor hit = screen.getStage().hit(position.x, position.y, true);
        if (slot != null) {
            if (hit == null) {
                slot.item.type.use(new UseContext(slot.item, player, screen, chunk, position.x, position.y));
            } else {
                slot.item.type.hit(new HitContext(slot.item, player, screen, chunk, hit));
            }
        }
        return true;
    }
}
