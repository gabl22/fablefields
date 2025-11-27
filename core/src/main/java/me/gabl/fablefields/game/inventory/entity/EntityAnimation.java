package me.gabl.fablefields.game.inventory.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import lombok.Setter;

public class EntityAnimation {

    @Setter
    private boolean flip;
    private Animation<TextureRegion>[] layers;
    @Getter
    private float stateTime = 0.0f;
    @Setter
    @Getter
    private float speedFactor = 1.0f;

    public void reset() {
        this.stateTime = 0f;
    }

    public void addDelta(float delta) {
        this.stateTime += delta * this.speedFactor;
    }

    public void setProgress(float progress) {
        if (layers == null || layers.length == 0) return;
        stateTime = progress * layers[0].getAnimationDuration();
    }

    public EntityAnimation setAnimationLayers(Animation<TextureRegion>[] layers) {
        this.layers = layers;
        return this;
    }

    public void draw(Batch batch, Actor entity) {
        if (layers == null) return;
        for (Animation<TextureRegion> animation : this.layers) {
            if (animation == null) {
                continue;
            }
            TextureRegion region = animation.getKeyFrame(this.stateTime);
            batch.draw(region.getTexture(), entity.getX() - entity.getOriginX(), entity.getY() - entity.getOriginY(),
                    entity.getWidth(), entity.getHeight(), region.getRegionX(), region.getRegionY(),
                    region.getRegionWidth(), region.getRegionHeight(), this.flip, false);
        }
    }
}
