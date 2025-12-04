package me.gabl.fablefields.preference;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum KeyAction {

    MOVE_UP("move_up"), MOVE_DOWN("move_down"), MOVE_LEFT("move_left"), MOVE_RIGHT("move_right"), MOVE_FAST("move_fast"), SWITCH_SLOT_BAR(
            "switch_slot_bar");

    public static final Map<String, KeyAction> ACTIONS = Arrays.stream(KeyAction.values())
            .collect(Collectors.toMap(KeyAction::getId, Function.identity()));
    public final String id;

    KeyAction(String id) {
        this.id = id;
    }

    public static KeyAction get(String id) {
        return ACTIONS.get(id);
    }

}
