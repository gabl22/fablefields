package me.gabl.fablefields.preference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

// TODO future implement multiple keys -> multiple binds
public class KeybindPreferenceFile extends PreferenceFile<KeybindPreferenceFile.Data> {

    public KeybindPreferenceFile() {
        super("keybind", Data.class, 1);
    }

    public Map<String, KeyAction> getMap() {
        return get().map;
    }

    @Override
    protected void setDefaultValues() {
        this.data.map.clear();
        this.data.map.putAll(Keybind.defaultStringKeys);

    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        private Map<String, KeyAction> map;
    }
}
