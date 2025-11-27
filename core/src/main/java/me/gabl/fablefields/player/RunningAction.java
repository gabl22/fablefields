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

    static {
        DEFAULT = Collections.unmodifiableMap(Arrays.stream(Action.values())
                .collect(Collectors.toMap(value -> value, value -> () -> new RunningAction(value, value.looping ?
                        Float.NaN : value.animationDuration()))));
    }

    public final Action action;
    public final float animationDuration;
    public final boolean infinite;
    public float durationLeft;
    private boolean running = false;
    @Setter
    private Consumer<Float> onActConsumer;
    @Setter
    private Runnable onStart;
    @Setter
    private Runnable onInterrupt;


    // start -> act -> ... -> act -> (interrupt xor finished) -> stop
    @Setter
    private Runnable onFinished;
    @Setter
    private Runnable onStop;
    protected RunningAction(RunningAction copy) {
        this.animationDuration = copy.animationDuration;
        this.action = copy.action;
        this.infinite = copy.infinite;
    }
    protected RunningAction(Action action) {
        this(action, Float.NaN);
    }
    protected RunningAction(Action action, float animationDuration) {
        this.action = action;
        this.animationDuration = animationDuration;
        infinite = Float.isNaN(animationDuration);
    }

    public static RunningAction get(Action action) {
        return DEFAULT.get(action).get();
    }

    public RunningAction copyAnimation() {
        return new RunningAction(this);
    }

    void start() {
        if (!running) {
            running = true;
            durationLeft = animationDuration;
            run(onStart);
        }
    }

    private void run(Runnable runnable) {
        if (runnable != null) runnable.run();
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

    @NotNull
    protected RunningAction next() {
        return idle();
    }

    void stop() {
        if (running) {
            running = false;
            run(onStop);
        }
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
            run(onInterrupt);
            stop();
        }
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
