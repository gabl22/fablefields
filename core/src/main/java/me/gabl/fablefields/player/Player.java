package me.gabl.fablefields.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;

public class Player extends Actor {

    private static final float SQRT2_2 = (float) (Math.sqrt(2) / 2);

    @Getter
    public final Attributes attributes;
    public transient Action action = Action.IDLE;
    public transient Direction direction = Direction.RIGHT;
    public ActionLayer hair = ActionLayer.SPIKEYHAIR;
    public transient RunningPlayerAnimation currentAnimation = new RunningPlayerAnimation(Action.IDLE,
        new ActionLayer[]{ActionLayer.BASE, this.hair, ActionLayer.TOOLS}, false
    );
    private transient float mx;
    private transient float my;

    public Player(Attributes attributes) {
        this.attributes = attributes;
    }

    public Player() {
        this.attributes = new Attributes();
    }

    @Override
    public void act(float delta) {
        this.calculateMovement();
        super.setX(
            super.getX() + this.mx * this.attributes.movementSpeed * delta * 15f); //constant to match animation to speed
        super.setY(super.getY() + this.my * this.attributes.movementSpeed * delta * 15f);
        if (this.mx == 0 && this.my == 0) {
            this.action = Action.IDLE;
        } else {
            this.action = Action.WALKING;
        }
        if (this.mx > 0) {
            this.direction = Direction.RIGHT;
        } else if (this.mx < 0) {
            this.direction = Direction.LEFT;
        }
        this.checkAnimation(false, delta);
    }

    private void calculateMovement() {
        // TODO rebind keys functionality?
        boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);
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
        this.currentAnimation.draw(batch, super.getX(), super.getY());
    }

    public static class Attributes {
        public float movementSpeed = 1f;
    }
}
