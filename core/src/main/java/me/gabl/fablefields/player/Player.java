package me.gabl.fablefields.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import kotlin.Pair;
import lombok.Getter;
import me.gabl.common.log.Logger;
import me.gabl.fablefields.map.logic.Address;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.util.GdxLogger;

import java.util.function.BiFunction;

public class Player extends Actor {

    private static final float SQRT2_2 = (float) (Math.sqrt(2) / 2);
    private static final Logger logger = GdxLogger.get(Player.class);

    @Getter
    public final Attributes attributes;
    private final GameScreen gameScreen;
    public transient Action action = Action.IDLE;
    public transient float actionDuration = -1f;
    public transient Direction direction = Direction.RIGHT;
    public ActionLayer hair = ActionLayer.SPIKEYHAIR;
    public transient RunningPlayerAnimation currentAnimation = new RunningPlayerAnimation(Action.IDLE,
        new ActionLayer[]{ActionLayer.BASE, this.hair, ActionLayer.TOOLS}, false
    );
    public final PlayerWorldController worldController;
    private transient float mx;
    private transient float my;

    public Player(GameScreen gameScreen, MapChunk chunk) {
        this.gameScreen = gameScreen;
        this.worldController = new PlayerWorldController(this, chunk, gameScreen);
        this.attributes = new Attributes();
        setPosition(-10, -10);
        setSize(6, 4);
        setOrigin(3f, 1.5f);
    }

    @Override
    public void act(float delta) {
        if (actionDuration >= 0.0) {
            actionDuration -= delta;
        }
        if (actionDuration < 0.0) {
            action = Action.IDLE;
            move(delta);
        } else {
            return;
        }
        this.checkAnimation(false, delta);
    }

    public void move(float delta) {
        this.calculateMovement();
        setXCollide(super.getX() + this.mx * this.attributes.movementSpeed * delta * 15f / 16); //constant to match animation to speed
        setYCollide(super.getY() + this.my * this.attributes.movementSpeed * delta * 15f / 16);
        if (this.mx != 0 || this.my != 0) {
            this.action = Action.WALKING;
        }
        if (this.mx > 0) {
            this.direction = Direction.RIGHT;
        } else if (this.mx < 0) {
            this.direction = Direction.LEFT;
        }

        checkEnvironment();
    }

    private void checkEnvironment() {
        if (isWalkable(super.getX(), super.getY())) {
            return;
        }

        Pair<Integer, Integer> safePosition = findSafety((x, y) -> {
            boolean walkable = Player.this.isWalkable(x, y);
            GdxLogger.get().info("Test " + x + " " + y + " " + walkable);
            return walkable;
        });
        if (safePosition == null) {
            logger.warn("No safe position for player found.");
            return;
        }
        setX(safePosition.getFirst() + 0.5f);
        setY(safePosition.getSecond());
    }

    private Pair<Integer, Integer> findSafety(BiFunction<Integer, Integer, Boolean> safetyPredicate) {
        int checkX = (int) getX();
        int checkY = (int) getY();
        int cycles = 0;
        int stepLength = 1;
        while (cycles < 12) {
            for (int i = 0; i < stepLength; i++) {
                checkX++;
                if (safetyPredicate.apply(checkX, checkY))
                    return new Pair<>(checkX, checkY);
            }

            for (int i = 0; i < stepLength; i++) {
                checkY++;
                if (safetyPredicate.apply(checkX, checkY))
                    return new Pair<>(checkX, checkY);
            }

            stepLength++;

            for (int i = 0; i < stepLength; i++) {
                checkX--;
                if (safetyPredicate.apply(checkX, checkY))
                    return new Pair<>(checkX, checkY);
            }

            for (int i = 0; i < stepLength; i++) {
                checkY--;
                if (safetyPredicate.apply(checkX, checkY))
                    return new Pair<>(checkX, checkY);
            }

            stepLength++;
            cycles++;
        }
        return null;
    }

    private void setXCollide(float x) {
        if (isWalkable(x, getY())) {
            setX(x);
        }
    }

    private void setYCollide(float y) {
        if (isWalkable(getX(), y)) {
            setY(y);
        }
    }

    public boolean isWalkable(float x, float y) {
        int fx = (int) Math.floor(x);
        int fy = (int) Math.floor(y);
        if (!gameScreen.getChunk().containsTileAt(fx, fy)) return false;
        return gameScreen.getChunk().isWalkable(Address.position(fx, fy, gameScreen.getChunk().width));
    }

    private void calculateMovement() {
        //Todo simplify
        boolean up = isKeyTriggered(KeyAction.MOVE_UP);
        boolean down = isKeyTriggered(KeyAction.MOVE_DOWN);
        boolean left = isKeyTriggered(KeyAction.MOVE_LEFT);
        boolean right = isKeyTriggered(KeyAction.MOVE_RIGHT);
        this.mx = 0;
        this.my = 0;
        if (!(up || down || left || right)) {
            return;
        }
        if (up && down) {
            up = false;
            down = false;
        }
        if (left && right) {
            left = false;
            right = false;
        }

        if ((up || down) && (left || right)) {
            if (right) {
                this.mx = SQRT2_2;
            } else {
                this.mx = -SQRT2_2;
            }
            if (down) {
                this.my = -SQRT2_2;
            } else {
                this.my = SQRT2_2;
            }
            return;
        }
        if (down) {
            this.my = -1f;
            return;
        }
        if (up) {
            this.my = 1f;
            return;
        }
        if (right) {
            this.mx = 1f;
            return;
        }
        if (left) {
            this.mx = -1f;
        }
    }

    private void checkAnimation(boolean cancelled, float delta) {
        if (cancelled || (this.action != this.currentAnimation.action) || (this.direction.flip != this.currentAnimation.flip)) {
            this.currentAnimation = new RunningPlayerAnimation(this.action,
                new ActionLayer[]{ActionLayer.BASE, this.hair, ActionLayer.TOOLS}, this.direction.flip
            );
            switch (this.action) {
                case WALKING, RUN:
                    this.currentAnimation.setSpeedFactor(this.attributes.movementSpeed);
                    break;
            }
        } else {
            this.currentAnimation.addDelta(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.currentAnimation.draw(batch, this);
    }

    public static class Attributes {
        public float movementSpeed = 1f;
    }

    private boolean isKeyTriggered(KeyAction keyAction) {
        return gameScreen.keyManager.isActionTriggered(keyAction);
    }
}
