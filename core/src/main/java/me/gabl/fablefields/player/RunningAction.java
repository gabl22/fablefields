package me.gabl.fablefields.player;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RunningAction {

    public static final Map<Action, Supplier<RunningAction>> DEFAULT;
    private boolean running = false;

    static {
        DEFAULT = Collections.unmodifiableMap(Arrays.stream(Action.values()).collect(
            Collectors.toMap(
            value -> value,
            value -> () -> new RunningAction(value, value.looping ? Float.NaN : value.animationDuration())
        )));
    }

    public static RunningAction get(Action action) {
        return DEFAULT.get(action).get();
    }

    public final Action action;
    public final float animationDuration;
    public final boolean infinite;
    public float durationLeft;

    protected RunningAction(Action action, float animationDuration) {
        this.action = action;
        this.animationDuration = animationDuration;
        infinite = Float.isNaN(animationDuration);
    }

    protected RunningAction(Action action) {
        this(action, Float.NaN);
    }

    void start() {
        if (!running) {
            running = true;
            durationLeft = animationDuration;
            onStart();
        }
    }

    protected void onStart() {

    }

    RunningAction act(float delta) {
        onAct(delta);
        if (!infinite) {
            durationLeft -= delta;
            if (durationLeft <= 0) {
                durationLeft = 0;
                stop();
                return next();
            }
        }
        return this;
    }

    protected void onAct(float delta) {

    }

    void stop() {
        if (running) {
            running = false;
            onStop();
        }
    }

    @NotNull
    protected RunningAction next() {
        return idle();
    }

    protected void onStop() {

    }

    public static RunningAction idle() {
        return new RunningAction(me.gabl.fablefields.player.Action.IDLE) {
            @NotNull
            @Override
            protected RunningAction next() {
                return this;
            }
        };
    }

    void interrupt() {
        if (running) {
            onInterrupt();
        }
        stop();
    }

    protected void onInterrupt() {

    }

    protected boolean permitsMovement() {
        return permitsMovement(this.action);
    }

    protected static boolean permitsMovement(Action action) {
        return true;
    }

    protected boolean interruptedByMovement() {
        return interruptedByMovement(this.action);
    }

    protected static boolean interruptedByMovement(Action action) {
        return permitsMovement(action) && switch (action) {
            case ATTACK, AXE, CASTING, CAUGHT, DIG, HAMMERING, MINING, REELING, WALKING, WATERING -> true;
            default -> false;
        };
    }
}
