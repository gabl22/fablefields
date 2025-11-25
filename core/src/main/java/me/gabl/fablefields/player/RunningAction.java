package me.gabl.fablefields.player;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
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

    protected RunningAction(RunningAction copy) {
        this.animationDuration = copy.animationDuration;
        this.action = copy.action;
        this.infinite = copy.infinite;
    }

    public RunningAction copyAnimation() {
        return new RunningAction(this);
    }

    public static RunningAction get(Action action) {
        return DEFAULT.get(action).get();
    }

    public final Action action;
    public final float animationDuration;
    public final boolean infinite;
    public float durationLeft;


    // start -> act -> ... -> act -> (interrupt xor finished) -> stop

    @Setter
    private Consumer<Float> onActConsumer;
    @Setter
    private Runnable onStart;
    @Setter
    private Runnable onInterrupt;
    @Setter
    private Runnable onFinished;
    @Setter
    private Runnable onStop;

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
            run(onStart);
        }
    }

    RunningAction act(float delta) {
        if (onActConsumer != null) onActConsumer.accept(delta);
        if (!infinite) {
            durationLeft -= delta;
            if (durationLeft <= 0) {
                durationLeft = 0;
                finished();
                return next();
            }
        }
        return this;
    }

    void finished() {
        if (running) {
            run(onFinished);
            stop();
        }
    }

    void interrupt() {
        if (running) {
            run(onInterrupt);
            stop();
        }
    }

    void stop() {
        if (running) {
            running = false;
            run(onStop);
        }
    }

    @NotNull
    protected RunningAction next() {
        return idle();
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

    private void run(Runnable runnable) {
        if (runnable != null) runnable.run();
    }
}
