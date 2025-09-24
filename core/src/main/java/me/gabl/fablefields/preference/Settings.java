package me.gabl.fablefields.preference;

import me.gabl.fablefields.preference.file.KeybindPreferenceFile;

//Todo static?
public class Settings {

    public static final KeybindPreferenceFile keybind;

    static {
        keybind = new KeybindPreferenceFile();
    }

    public static void load() {
        keybind.load();
    }
}
