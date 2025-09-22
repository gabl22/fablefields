package me.gabl.fablefields.test;

import com.badlogic.gdx.utils.IntSet;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.preference.Settings;
import me.gabl.fablefields.preference.file.KeybindPreferenceFile;

import java.util.EnumSet;

public class KeyControllerManager {

    private final IntSet keysDown = new IntSet();
    private final EnumSet<KeyAction> keybindTriggers = EnumSet.noneOf(KeyAction.class);

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

    public KeyAction getBind(int keycode) {
        return Settings.keybind.get().get(keycode);
    }

    public boolean isActionTriggered(KeyAction action) {
        return keybindTriggers.contains(action);
    }
}
