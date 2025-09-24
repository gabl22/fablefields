package me.gabl.fablefields.preference;

//Todo static?
public class Settings {

    public static final Keybind keybind;

    static {
        keybind = new Keybind();
    }

    public static void load() {
        keybind.load();
    }
}
