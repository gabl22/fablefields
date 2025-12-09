package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Getter;
import me.gabl.fablefields.map.logic.MapChunk;

public class AnimatedEntity extends Entity {

    @Getter
    private final EntityAnimation animation;

    public AnimatedEntity(MapChunk chunk, String id) {
        super(chunk, id);
        this.animation = new EntityAnimation();
    }

    @Override
    public void draw(Batch batch, float _parentAlpha) {
        this.animation.draw(batch, this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animation.addDelta(delta);
    }
}
