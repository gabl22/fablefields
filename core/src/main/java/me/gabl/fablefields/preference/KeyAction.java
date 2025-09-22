package me.gabl.fablefields.preference;

import me.gabl.common.util.enumutil.Identifiable;

import java.util.Map;

public enum KeyAction implements Identifiable<String> {

    MOVE_UP("up"), MOVE_DOWN("down"), MOVE_LEFT("left"), MOVE_RIGHT("right");

    public final String id;
    public static final Map<String, KeyAction> ACTIONS = Identifiable.getMap(KeyAction.values());

    KeyAction(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
