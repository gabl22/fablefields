package me.gabl.fablefields.preference;

import me.gabl.common.util.enumutil.Identifiable;

import java.util.Map;

public enum KeyAction implements Identifiable<String> {

    MOVE_UP("move_up"), MOVE_DOWN("move_down"), MOVE_LEFT("move_left"), MOVE_RIGHT("move_right"), SWITCH_SLOT_BAR(
            "switch_slot_bar");

    public static final Map<String, KeyAction> ACTIONS = Identifiable.getMap(KeyAction.values());
    public final String id;

    KeyAction(String id) {
        this.id = id;
    }

    public static KeyAction get(String id) {
        return ACTIONS.get(id);
    }

    @Override
    public String getId() {
        return id;
    }
}
