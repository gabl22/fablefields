package me.gabl.fablefields.preference;

import com.badlogic.gdx.Input.Keys;

import java.util.Map;
import java.util.stream.Collectors;

public class Keybind {

    // @formatter:off
    static final Map<Integer, KeyAction> defaultKeys = Map.of(
        Keys.W,             KeyAction.MOVE_UP,
        Keys.A,             KeyAction.MOVE_LEFT,
        Keys.S,             KeyAction.MOVE_DOWN,
        Keys.D,             KeyAction.MOVE_RIGHT,
        Keys.TAB,           KeyAction.SWITCH_SLOT_BAR,
        Keys.SHIFT_LEFT,    KeyAction.MOVE_FAST
    );
    // @formatter:on
    static final Map<String, KeyAction> defaultStringKeys = toStringMap(defaultKeys);
    public final KeybindPreferenceFile backing;
    private Map<Integer, KeyAction> keys;


    public Keybind() {
        this.backing = new KeybindPreferenceFile();
    }

    private static <C> Map<String, C> toStringMap(Map<?, C> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()), Map.Entry::getValue));
    }

    public void load() {
        this.backing.load();
        this.keys = toIntMap(backing.getMap());
    }

    private static <C> Map<Integer, C> toIntMap(Map<String, C> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(entry -> Integer.valueOf(entry.getKey()), Map.Entry::getValue));
    }

    public KeyAction get(int keycode) {
        return this.keys.get(keycode);
    }
}
