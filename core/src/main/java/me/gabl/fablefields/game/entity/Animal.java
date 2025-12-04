package me.gabl.fablefields.game.entity;

import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Movement;
import me.gabl.fablefields.util.MathUtil;

public class Animal extends LivingEntity {

    private Action action = Action.IDLE;
    private float actionDuration;
    private int direction;

    public Animal(MapChunk chunk) {
        super(chunk);
    }

    @Override
    public void onDamage(float damage) {
        action = Action.RUN;
        getAnimation().setSpeedFactor(20);
        actionDuration = 5;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        actionDuration -= delta;
        if (actionDuration <= 0) {
            switchAction();
        }
        if (action == Action.IDLE) {
            return;
        }
        float speed = delta * (action == Action.WANDER ? 1f : 3f);

        if (!walk(getX() + MathUtil.dir8[direction] * speed, getY() + MathUtil.dir8[direction + 1] * speed)) {
            randomizeDirection();
        }
    }

    public void switchAction() {
        action = switch (action) {
            case IDLE -> Action.WANDER;
            case WANDER -> MathUtil.RANDOM.nextFloat() > 0.8 ? Action.IDLE : Action.WANDER;
            case RUN -> Action.IDLE;
        };

        boolean isIdle = action == Action.IDLE;

        if (isIdle) {
            actionDuration = MathUtil.RANDOM.nextFloat() * 5 + 5; // 5 to 10 seconds
        } else {
            actionDuration = MathUtil.RANDOM.nextFloat() * 3 + 2; // 2 to 5 seconds
        }
        randomizeDirection();

        getAnimation().setSpeedFactor(isIdle ? 1 : 3);
    }

    public boolean walk(float x, float y) {
        if (chunk.containsTile((int) x, (int) y) && chunk.is(Movement.WALKABLE, x, y)) {
            setPosition(x, y);
            return true;
        }
        return false;
    }

    public void randomizeDirection() {
        direction = ((direction / 2 + MathUtil.RANDOM.nextInt(7)) % 8) * 2;
        float directionHorizontal = MathUtil.dir8[direction];
        if (directionHorizontal > 0) {
            getAnimation().setFlip(true);
        } else if (directionHorizontal < 0) {
            getAnimation().setFlip(false);
        }
    }

    private enum Action {
        IDLE, WANDER, RUN
    }
}
