package me.gabl.fablefields.test;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.preference.Settings;
import me.gabl.fablefields.util.GdxLogger;
import me.gabl.fablefields.util.MathUtil;

import java.util.EnumSet;

public class KeyInputManager implements InputProcessor {

    private final IntSet keysDown = new IntSet();
    private final EnumSet<KeyAction> keybindTriggers = EnumSet.noneOf(KeyAction.class);

    @Override
    public boolean keyDown(int keycode) {
        KeyAction action = getBind(keycode);
        if (action == null) {
            return false;
        }

        boolean processed = keysDown.add(keycode);
        if (!processed) {
            return false;
        }
        keybindTriggers.add(action);
        return true;
    }

    public KeyAction getBind(int keycode) {
        return Settings.keybind.get(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        KeyAction action = getBind(keycode);
        boolean hasAction = action != null;
        boolean processed = keysDown.remove(keycode);
        if (!processed) {
            return false;
        }
        keybindTriggers.remove(action);
        return hasAction;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        GdxLogger.get().info("DR Pointer: " + pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    // returns a vector of length 1 or 0
    public void calculateMovement(Vector2 movement) {
        int dx = (isActionTriggered(KeyAction.MOVE_RIGHT) ? 1 : 0) - (isActionTriggered(KeyAction.MOVE_LEFT) ? 1 : 0);
        int dy = (isActionTriggered(KeyAction.MOVE_UP) ? 1 : 0) - (isActionTriggered(KeyAction.MOVE_DOWN) ? 1 : 0);
        if (dx == 0 && dy == 0) {
            movement.x = 0;
            movement.y = 0;
            return;
        }

        movement.set(dx, dy).scl(dx == 0 || dy == 0 ? 1 : MathUtil.INV_SQRT2);
    }

    public boolean isActionTriggered(KeyAction action) {
        return keybindTriggers.contains(action);
    }
}
