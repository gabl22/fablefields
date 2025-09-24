package me.gabl.fablefields.preference.file;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.AllArgsConstructor;
import me.gabl.fablefields.preference.KeyAction;

import java.util.HashMap;
import java.util.Map;

// TODO future implement multiple keys -> multiple binds
public class KeybindPreferenceFile extends PreferenceFile<KeybindPreferenceFile.Data> {

    public KeybindPreferenceFile() {
        super("keybind", Data.class, 1);
        super.json.setSerializer(Data.class, new DataDeserializer());
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

    @AllArgsConstructor
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

    public static class DataDeserializer implements Json.Serializer<Data> {

        @Override
        public void write(Json json, Data object, Class knownType) {
            json.writeObjectStart();
            object.map.forEach((key, value) -> json.writeValue(String.valueOf(key), value.id));
            json.writeObjectEnd();
        }

        @Override
        public Data read(Json json, JsonValue data, Class type) {
            Map<Integer, KeyAction> actions = new HashMap<>();
            for (JsonValue child = data.child; child != null; child = child.next) {
                 Integer key = Integer.valueOf(child.name);
                 KeyAction action = KeyAction.get(child.asString());
                 actions.put(key, action);
            }
            return new Data(actions);
        }
    }
}
