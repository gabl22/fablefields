package me.gabl.fablefields.test;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntSet;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.preference.Settings;
import me.gabl.fablefields.util.GdxLogger;

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

    public KeyAction getBind(int keycode) {
        return Settings.keybind.get(keycode);
    }

    public boolean isActionTriggered(KeyAction action) {
        return keybindTriggers.contains(action);
    }
}
