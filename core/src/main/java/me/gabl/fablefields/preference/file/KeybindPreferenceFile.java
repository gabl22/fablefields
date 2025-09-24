package me.gabl.fablefields.preference.file;

import com.badlogic.gdx.Input;
import me.gabl.fablefields.preference.KeyAction;

import java.util.HashMap;
import java.util.Map;

// TODO future implement multiple keys -> multiple binds
public class KeybindPreferenceFile extends PreferenceFile<KeybindPreferenceFile.Data> {

    public KeybindPreferenceFile() {
        super("keybind", Data.class, 1);
    }

    /**
     * @param keycode
     * @return null if keycode not assigned, assigned Keyaction else.
     */
    public KeyAction get(int keycode) {
        // todo nullpointer if loading failed
        return this.data.map.get(keycode);
    }

    public Map<Integer, KeyAction> getMap() {
        return get().map;
    }

    public static class Data {
        private final Map<Integer, KeyAction> map;

        public Data() {
            this.map = new HashMap<>();
        }
    }

    @Override
    protected void setDefaultValues() {
        this.data.map.clear();
        this.data.map.putAll(Map.of(
            Input.Keys.W, KeyAction.MOVE_UP,
            Input.Keys.A, KeyAction.MOVE_LEFT,
            Input.Keys.S, KeyAction.MOVE_DOWN,
            Input.Keys.D, KeyAction.MOVE_RIGHT
        ));
    }
}
