package me.gabl.fablefields.player;

import com.badlogic.gdx.math.Vector3;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.game.inventory.Slot;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.screen.util.DefaultInputProcessor;

@AllArgsConstructor
public class PlayerWorldController implements DefaultInputProcessor {

    private final Player player;
    private final MapChunk chunk;
    private final GameScreen screen;

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Slot slot = screen.inventory.getSelectedSlot();
        // need (!) to unproject using viewport (zoom)
        Vector3 position = screen.getViewport().unproject(new Vector3(screenX, screenY, 0));
        if (slot != null) {
            slot.item.type.use(new UseContext(slot.item, player, screen, chunk, position.x, position.y));
        }
        return true;
    }
}
