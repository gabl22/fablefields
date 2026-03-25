package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import me.gabl.fablefields.asset.AnimationD;
import me.gabl.fablefields.asset.AnimationLoader;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.util.MathUtil;

public class Cow extends Animal {

    private static final float MILKABLE_MIN_INTERVAL = 20f;
    private static final float MILKABLE_MAX_INTERVAL = 50f;
    private static final float MILKABLE_TIMEOUT = 20f;

    @Getter
    private boolean milkable;
    private float milkableTimer;
    private float nextMilkableIn;

    @SuppressWarnings("unchecked")
    public Cow(MapChunk chunk) {
        super(chunk, "cow");
        super.setSize(2, 2);
        setOrigin(1, 0.5f);
        setHitbox(HitBox.rect(-0.5f, 0.5f, 0.0f, 1.0f));
        setCollisionBoxRelative(HitBox.rectangle(-0.3f, 0.3f, 0.0f, 0.3f));
        maxHealth = 20;
        health = maxHealth;
        getAnimation().setAnimationLayers(new Animation[]{getCowAnimation()});

        milkable = false;
        nextMilkableIn = MathUtil.RANDOM.nextFloat() * (MILKABLE_MAX_INTERVAL - MILKABLE_MIN_INTERVAL) + MILKABLE_MIN_INTERVAL;
    }

    private static Animation<TextureRegion> getCowAnimation() {
        AssetDescriptor<AnimationD> descriptor = Asset.getAnimation("elements/animals/cow.png",
                AnimationLoader.params(32, 32, 4, 0.8f, Animation.PlayMode.LOOP));
        return Asset.manager.get(descriptor);
    }

    @Override
    public void act(float delta) {
        if (milkable) {
            milkableTimer -= delta;
            if (milkableTimer <= 0) {
                resetMilkable();
            }
            // Milkable cows stay in place - skip Animal movement
            getAnimation().addDelta(delta);
            return;
        }

        nextMilkableIn -= delta;
        if (nextMilkableIn <= 0) {
            milkable = true;
            milkableTimer = MILKABLE_TIMEOUT;
        }

        super.act(delta);
    }

    public void milk() {
        resetMilkable();
    }

    private void resetMilkable() {
        milkable = false;
        nextMilkableIn = MathUtil.RANDOM.nextFloat() * (MILKABLE_MAX_INTERVAL - MILKABLE_MIN_INTERVAL) + MILKABLE_MIN_INTERVAL;
    }
}
