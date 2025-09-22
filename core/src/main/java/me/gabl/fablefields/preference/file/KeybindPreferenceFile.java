package me.gabl.fablefields.preference.file;

import me.gabl.fablefields.preference.KeyAction;

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
        return this.data.get(keycode);
    }

    public interface Data extends Map<Integer, KeyAction> {}
}
