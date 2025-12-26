package me.gabl.fablefields.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import me.gabl.fablefields.game.entity.Entity;
import me.gabl.fablefields.game.entity.HitBox;
import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.PathFinder;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.util.Logger;

public class Player extends Entity {

    private static final Logger logger = Logger.get(Player.class);

    @Getter
    public final Attributes attributes;
    public final PlayerWorldController worldController;
    private final GameScreen gameScreen;
    private final Vector2 movement = new Vector2();
    public transient RunningAction action;
    public transient Direction direction = Direction.RIGHT;
    public ActionLayer hair = ActionLayer.BOWLHAIR;
    public transient RunningPlayerAnimation currentAnimation = new RunningPlayerAnimation(Action.IDLE,
            new ActionLayer[]{
            ActionLayer.BASE, this.hair, ActionLayer.TOOLS}, false);
    public boolean forceRenewAnimation = false;
    public Inventory inventory;

    public Player(GameScreen gameScreen, MapChunk chunk) {
        super(chunk, "player");
        this.gameScreen = gameScreen;
        this.worldController = new PlayerWorldController(this, chunk, gameScreen);
        this.attributes = new Attributes();
        setHitbox(HitBox.rect(-0.5f, 0.5f, 0.0f, 1.0f));
        setCollisionBoxRelative(HitBox.rectangle(-0.3f, 0.3f, 0.0f, 0.2f));
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
            Movement moveBy = Movement.WALK;
            if (chunk.is(Movement.WALKABLE, super.getX(), super.getY())) {
                moveBy = gameScreen.keyManager.isActionTriggered(KeyAction.MOVE_FAST) ? Movement.RUN : Movement.WALK;
            } else if (chunk.is(Movement.SWIMMABLE, super.getX(), super.getY())) {
                moveBy = Movement.SWIM;
            }


            float newX = super.getX() + movement.x * this.attributes.movementSpeed * delta * moveBy.speedFactor;
            float newY = super.getY() + movement.y * this.attributes.movementSpeed * delta * moveBy.speedFactor;

            chunk.moveTo(this, newX, newY, Movement.ACCESSIBLE);
            this.action = RunningAction.get(moveBy.action);
            if (movement.x > 0) {
                this.direction = Direction.RIGHT;
            } else if (movement.x < 0) {
                this.direction = Direction.LEFT;
            }
        } else if (this.action.action == Action.RUN || this.action.action == Action.WALKING) {
            replaceAction(Action.IDLE);
        }

        checkEnvironment();
    }

    private void checkAnimation(float delta) {
        if (this.action.action != this.currentAnimation.action || this.direction.flip != this.currentAnimation.flip) {
            forceRenewAnimation = false;
            this.currentAnimation = new RunningPlayerAnimation(this.action.action, new ActionLayer[]{ActionLayer.BASE,
                    this.hair, ActionLayer.TOOLS}, this.direction.flip);
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

    public void replaceAction(Action action) {
        replaceAction(RunningAction.get(action));
    }

    private void checkEnvironment() {
        if (gameScreen.getChunk().is(Movement.ACCESSIBLE, super.getX(), super.getY())) {
            return;
        }

        if (!PathFinder.moveToSafety(this, chunk, (int) getX(), (int) getY(), 15, Movement.ACCESSIBLE)) {
            logger.error("No safe position for entity found. id=" + id);
        }
    }

    public void replaceAction(RunningAction action) {
        if (!actionReplaceable()) {
            // TODO give feedback to player
            return;
        }
        this.action.interrupt();
        this.action = action;
        forceRenewAnimation = true;
        this.action.start();
    }

    public boolean actionReplaceable() {
        return this.action.action != Action.SWIMMING && chunk.is(Movement.WALKABLE, getX(), getY());
    }

    public void turnTo(float x) {
        this.direction = x > getX() ? Direction.RIGHT : Direction.LEFT;
    }

    public boolean inRange(Range range, float x, float y) {
        if (range == null) return true;
        float tolerance = range.rangeTiles * attributes.range;
        tolerance *= tolerance;
        return tolerance >= (x - getX()) * (x - getX()) + (y - getY()) * (y - getY());
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.currentAnimation.draw(batch, this);
    }

    public static class Attributes {

        public float range = 1f;
        public float movementSpeed = 1f;
    }
}
