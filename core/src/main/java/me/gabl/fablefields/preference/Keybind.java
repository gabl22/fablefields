package me.gabl.fablefields.preference;

import com.badlogic.gdx.Input;

import java.util.Map;
import java.util.stream.Collectors;

public class Keybind {

    // @formatter:off
    static final Map<Integer, KeyAction> defaultKeys = Map.of(
        Input.Keys.W, KeyAction.MOVE_UP,
        Input.Keys.A, KeyAction.MOVE_LEFT,
        Input.Keys.S, KeyAction.MOVE_DOWN,
        Input.Keys.D, KeyAction.MOVE_RIGHT,
        Input.Keys.TAB, KeyAction.SWITCH_SLOT_BAR
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
