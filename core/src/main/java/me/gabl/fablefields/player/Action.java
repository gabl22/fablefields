package me.gabl.fablefields.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import me.gabl.fablefields.asset.AnimationLoader;

public enum Action {
    ATTACK("attack", 10, false), AXE("axe", 10, false, 0.08f), CARRY("carry", 8, true), CASTING("casting", 15, false)
    , CAUGHT("caught", 10, false), DEATH("death", 13, false), DIG("dig", 13, false, 0.1f), DOING("doing", 8, true),
    HAMMERING("hammering", 23, false), HURT("hurt", 8, false), IDLE("idle", 9, true, 0.25f), JUMP("jump", 9, false),
    MINING("mining", 10, false), REELING("reeling", 13, true), ROLL("roll", 10, false), RUN("run", 8, true, 0.09f),
    SWIMMING("swimming", 12, true, 0.15f), WAITING("waiting", 9, true), WALKING("walking", 8, true, 0.15f), WATERING(
            "watering", 5, false, 0.2f);

    public final String id;
    public final int frames;
    public final boolean looping;
    public final float frameDuration;

    Action(String id, int frames, boolean loop, float frameDuration) {
        this.id = id;
        this.frames = frames;
        this.looping = loop;
        this.frameDuration = frameDuration;
    }

    Action(String id, int frames, boolean loop) {
        this.id = id;
        this.frames = frames;
        this.looping = loop;
        this.frameDuration = 1f; //default to be changed @TODO
    }

    public static AnimationLoader.Parameters getParameters(Action action) {
        return action.getParameters();
    }

    public AnimationLoader.Parameters getParameters() {
        return AnimationLoader.params(64, 96, this.frames, this.frameDuration, this.looping ?
                Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
    }

    public float animationDuration() {
        return frames * frameDuration;
    }
}
