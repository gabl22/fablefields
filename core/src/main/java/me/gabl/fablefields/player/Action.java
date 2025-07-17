package me.gabl.fablefields.player;

public enum Action {
    ATTACK("attack"),
    AXE("axe"),
    CARRY("carry"),
    CASTING("casting"),
    CAUGHT("caught"),
    DEATH("death"),
    DIG("dig"),
    DOING("doing"),
    HAMMERING("hammering"),
    HURT("hurt"),
    IDLE("idle"),
    JUMP("jump"),
    MINING("mining"),
    REELING("reeling"),
    ROLL("roll"),
    RUN("run"),
    SWIMMING("swimming"),
    WAITING("waiting"),
    WALKING("walking"),
    WATERING("watering");

    public final String id;

    Action(String id) {
        this.id = id;
    }
}
