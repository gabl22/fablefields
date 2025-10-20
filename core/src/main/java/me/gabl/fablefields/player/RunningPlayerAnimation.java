package me.gabl.fablefields.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;
import me.gabl.fablefields.asset.Asset;

public class RunningPlayerAnimation {

    public final Action action;
    public final boolean flip;
    private final Animation<TextureRegion>[] animations;
    @Getter
    private float stateTime = 0f;
    @Setter
    private float speedFactor = 1f;

    public RunningPlayerAnimation(Action action, ActionLayer[] layers, Direction direction) {
        this(action, layers, direction.flip);
    }

    @SuppressWarnings("unchecked")
    public RunningPlayerAnimation(Action action, ActionLayer[] layers, boolean flip) {
        this.action = action;
        this.animations = (Animation<TextureRegion>[]) new Animation[layers.length];
        this.flip = flip;
        for (int i = 0; i < layers.length; i++) {
            if (layers[i] == null) {
                continue;
            }
            this.animations[i] = Asset.manager.get(Asset.descriptorHuman(action, layers[i]));
        }
    }

    public void reset() {
        this.stateTime = 0f;
    }

    public void addDelta(float delta) {
        this.stateTime += delta * this.speedFactor;
    }

    public void draw(Batch batch, Player player) {
        for (Animation<TextureRegion> animation : this.animations) {
            if (animation == null) {
                continue;
            }
            TextureRegion region = animation.getKeyFrame(this.stateTime);
            batch.draw(region.getTexture(), player.getX() - player.getOriginX(), player.getY() - player.getOriginY(), player.getWidth(), player.getHeight(),
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), this.flip, false
            );
        }
    }
}
