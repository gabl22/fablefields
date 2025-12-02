package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Setter;
import me.gabl.fablefields.map.logic.MapChunk;

public class Entity extends Actor {

    protected final MapChunk chunk;
    @Setter
    protected HitBox hitbox;

    public Entity(MapChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && !super.isTouchable()) return null;
        if (!isVisible()) return null;
        if (hitbox == null || hitbox.hit(x, y)) return this;
        return null;
    }

    public int tileX() {
        return (int) getX();
    }

    public int tileY() {
        return (int) getY();
    }
}
