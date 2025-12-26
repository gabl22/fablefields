package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import lombok.Setter;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.PathFinder;
import me.gabl.fablefields.player.Movement;
import me.gabl.fablefields.util.Logger;

import java.util.function.BiFunction;

public class Entity extends Actor {

    private static final Logger logger = Logger.get(Entity.class);
    public final String id;
    protected final MapChunk chunk;
    @Setter
    @Getter
    protected HitBox hitbox;

    @Getter
    @Setter
    protected Rectangle collisionBoxRelative;
    private final Rectangle collisionBox = new Rectangle();
    private boolean collisionBoxValid = true;

    public Entity(MapChunk chunk, String id) {
        this.chunk = chunk;
        this.id = id;
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && !super.isTouchable()) return null;
        if (!isVisible()) return null;
        if (hitbox == null || hitbox.hit(x, y)) return this;
        return null;
    }

    public void onSpawn() {
        if (chunk.is(Movement.WALKABLE, getX(), getY())) {
            return;
        }


        if (!PathFinder.moveToSafety(this, chunk, (int) getX(), (int) getY(), 15, Movement.WALKABLE)) {
            logger.error("No safe position for entity found. id=" + id);
        }
    }

    public int tileX() {
        return (int) getX();
    }

    public int tileY() {
        return (int) getY();
    }

    public Rectangle getCollisionBox() {
        return getCollisionBox(getX(), getY());
    }

    public Rectangle getCollisionBox(float x, float y) {
        if (collisionBoxValid) return collisionBox;
        if (collisionBoxRelative == null) return null;

        collisionBox.x = x + collisionBoxRelative.x;
        collisionBox.y = y + collisionBoxRelative.y;
        collisionBox.width = collisionBoxRelative.width;
        collisionBox.height = collisionBoxRelative.height;
        return collisionBox;
    }

    @Override
    public void moveBy(float x, float y) {
        collisionBoxValid = false;
        super.moveBy(x, y);
    }

    @Override
    public void setPosition(float x, float y) {
        collisionBoxValid = false;
        super.setPosition(x, y);
    }

    @Override
    public void setX(float x) {
        collisionBoxValid = false;
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        collisionBoxValid = false;
        super.setY(y);
    }
}
