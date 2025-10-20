package me.gabl.fablefields.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import kotlin.Pair;
import lombok.Getter;
import me.gabl.common.log.Logger;
import me.gabl.fablefields.map.logic.Address;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.util.GdxLogger;

import java.util.function.BiFunction;

public class Player extends Actor {

    private static final Logger logger = GdxLogger.get(Player.class);

    @Getter
    public final Attributes attributes;
    public final PlayerWorldController worldController;
    private final GameScreen gameScreen;
    public transient RunningAction action;
    public transient Direction direction = Direction.RIGHT;
    public ActionLayer hair = ActionLayer.SPIKEYHAIR;
    public transient RunningPlayerAnimation currentAnimation = new RunningPlayerAnimation(Action.IDLE,
        new ActionLayer[]{ActionLayer.BASE, this.hair, ActionLayer.TOOLS}, false
    );
    public boolean forceRenewAnimation = false;
    private Vector2 movement = new Vector2();

    public Player(GameScreen gameScreen, MapChunk chunk) {
        this.gameScreen = gameScreen;
        this.worldController = new PlayerWorldController(this, chunk, gameScreen);
        this.attributes = new Attributes();
        setPosition(-10, -10);
        setSize(6, 4);
        setOrigin(3f, 1.5f);
        action = RunningAction.get(Action.IDLE);
    }

    @Override
    public void act(float delta) {
        action = action.act(delta);

        if (action.permitsMovement()) {
            move(delta);
        }
        this.checkAnimation(delta);
    }

    public void move(float delta) {
        this.calculateMovement();
        if (movement.x != 0 || movement.y != 0) {
            setXCollide(
                super.getX() + movement.x * this.attributes.movementSpeed * delta * 15f / 16); //constant to match animation to speed
            setYCollide(super.getY() + movement.y * this.attributes.movementSpeed * delta * 15f / 16);
            this.action = RunningAction.get(Action.WALKING);
            if (movement.x > 0) {
                this.direction = Direction.RIGHT;
            } else if (movement.x < 0) {
                this.direction = Direction.LEFT;
            }
        } else if (this.action.action == Action.WALKING) {
            replaceAction(Action.IDLE);
        }

        checkEnvironment();
    }

    private void checkAnimation(float delta) {
        if (this.action.action != this.currentAnimation.action || this.direction.flip != this.currentAnimation.flip) {
            forceRenewAnimation = false;
            this.currentAnimation = new RunningPlayerAnimation(this.action.action,
                new ActionLayer[]{ActionLayer.BASE, this.hair, ActionLayer.TOOLS}, this.direction.flip
            );
            switch (this.action.action) {
                case WALKING, RUN:
                    this.currentAnimation.setSpeedFactor(this.attributes.movementSpeed);
                    break;
            }
        } else if (forceRenewAnimation) {
            forceRenewAnimation = false;
            this.currentAnimation.reset();
        } else {
            this.currentAnimation.addDelta(delta);
        }
    }

    private void calculateMovement() {
        gameScreen.keyManager.calculateMovement(movement);
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

    public void replaceAction(Action action) {
        replaceAction(RunningAction.get(action));
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

    public boolean isWalkable(float x, float y) {
        int fx = (int) Math.floor(x);
        int fy = (int) Math.floor(y);
        if (!gameScreen.getChunk().containsTileAt(fx, fy))
            return false;
        return gameScreen.getChunk().isWalkable(Address.position(fx, fy, gameScreen.getChunk().width));
    }

    public void replaceAction(RunningAction action) {
        this.action.interrupt();
        this.action = action;
        forceRenewAnimation = true;
        this.action.start();
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.currentAnimation.draw(batch, this);
    }

    public static class Attributes {
        public float movementSpeed = 1f;
    }
}
