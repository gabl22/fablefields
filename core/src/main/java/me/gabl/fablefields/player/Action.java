package me.gabl.fablefields.player;

import me.gabl.fablefields.asset.AnimationLoader;

public enum Action {
    ATTACK("attack", 10), AXE("axe", 10), CARRY("carry", 8), CASTING("casting", 15), CAUGHT("caught", 10), DEATH(
        "death", 13), DIG("dig", 13), DOING("doing", 8), HAMMERING("hammering", 23), HURT("hurt", 8), IDLE("idle",
        9
    ), JUMP("jump", 9), MINING("mining", 10), REELING("reeling", 13), ROLL("roll", 10), RUN("run", 8), SWIMMING(
        "swimming", 12), WAITING("waiting", 9), WALKING("walking", 8), WATERING("watering", 5);

    public final String id;
    public final int frames;

    Action(String id, int frames) {
        this.id = id;
        this.frames = frames;
    }

    public static AnimationLoader.Parameters getParameters(Action action) {
        return action.getParameters();
    }

    public AnimationLoader.Parameters getParameters() {
        return AnimationLoader.params(64, 96, this.frames, 5); //default 5s to be changed @TODO
    }
}
