package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import me.gabl.fablefields.game.entity.HitBox.RectangularHitBox;
import me.gabl.fablefields.map.logic.MapChunk;

public class StaticCollisionEntity extends StaticTextureEntity {

    public Rectangle collisionBox = new Rectangle();

    public StaticCollisionEntity(MapChunk chunk, String id, Texture texture) {
        super(chunk, id, texture);
    }

    public StaticCollisionEntity(MapChunk chunk, String id) {
        super(chunk, id);
    }

    public void calculateCollisionBox() {
        RectangularHitBox hitBox = (RectangularHitBox) getHitbox();
        float x = getX();
        float y = getY();
        collisionBox.x = x + hitBox.xMin;
        collisionBox.y = y + hitBox.yMin;
        collisionBox.width = hitBox.xMax - hitBox.xMin;
        collisionBox.height = hitBox.yMax - hitBox.yMin;
    }

    // if necessary, seperate hitbox (cursor) and collisionbox (interference with other entities)
    @Override
    public void setHitbox(HitBox hitbox) {
        if (hitbox instanceof RectangularHitBox) {
            super.setHitbox(hitbox);
            calculateCollisionBox();
            return;
        }
        throw new UnsupportedOperationException("StaticCollisionEntity Hitbox must be Rectangular");
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        calculateCollisionBox();
    }
}
