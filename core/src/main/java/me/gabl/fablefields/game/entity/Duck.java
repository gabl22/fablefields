package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.gabl.fablefields.asset.AnimationD;
import me.gabl.fablefields.asset.AnimationLoader;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Movement;

public class Duck extends Animal {

    @SuppressWarnings("unchecked")
    public Duck(MapChunk chunk) {
        super(chunk, "duck");
        super.setSize(1, 1);
        setOrigin(0.5f, 0.25f);
        setHitbox(HitBox.rect(-0.25f, 0.25f, 0.0f, 0.5f));
        setCollisionBoxRelative(HitBox.rectangle(-0.1f, 0.1f, 0.0f, 0.1f));
        maxHealth = 8;
        health = maxHealth;
        getAnimation().setAnimationLayers(new Animation[]{getDuckAnimation()});
    }

    private static Animation<TextureRegion> getDuckAnimation() {
        AssetDescriptor<AnimationD> descriptor = Asset.getAnimation("elements/animals/duck.png",
                AnimationLoader.params(16, 16, 4, 0.8f, Animation.PlayMode.LOOP));
        return Asset.manager.get(descriptor);
    }

    @Override
    public boolean walk(float x, float y) {
        if (chunk.moveTo(this, x, y, Movement.SWIMMABLE)) {
            setPosition(x, y);
            return true;
        }
        return false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
