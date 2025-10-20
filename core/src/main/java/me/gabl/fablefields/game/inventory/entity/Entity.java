package me.gabl.fablefields.game.inventory.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Setter;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.util.GdxLogger;

public class Entity extends Actor {

    protected final MapChunk chunk;
    @Setter
    protected Hitbox hitbox;

    public Entity(MapChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && !super.isTouchable())
            return null;
        if (!isVisible())
            return null;
        if (hitbox == null || hitbox.hit(x, y))
            return this;
        return null;
    }
}
