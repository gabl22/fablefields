package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import me.gabl.fablefields.asset.AnimationD;
import me.gabl.fablefields.asset.AnimationLoader;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.util.MathUtil;

public class Chicken extends Animal {

    private static final float EGG_MIN_INTERVAL = 30f;
    private static final float EGG_MAX_INTERVAL = 60f;
    private static final float EGG_NEARBY_RADIUS = 8f;

    private float eggTimer;

    @SuppressWarnings("unchecked")
    public Chicken(MapChunk chunk) {
        super(chunk, "chicken");
        super.setSize(2, 2);
        setOrigin(1, 0.5f);
        setHitbox(HitBox.rect(-0.5f, 0.5f, 0.0f, 1.0f));
        setCollisionBoxRelative(HitBox.rectangle(-0.2f, 0.2f, 0.0f, 0.2f));
        maxHealth = 12;
        health = maxHealth;
        getAnimation().setAnimationLayers(new Animation[]{getChickenAnimation()});
        resetEggTimer();
    }

    private static Animation<TextureRegion> getChickenAnimation() {
        AssetDescriptor<AnimationD> descriptor = Asset.getAnimation("elements/animals/chicken.png",
                AnimationLoader.params(32, 32, 4, 0.8f, Animation.PlayMode.LOOP));
        return Asset.manager.get(descriptor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        eggTimer -= delta;
        if (eggTimer <= 0) {
            tryLayEgg();
            resetEggTimer();
        }
    }

    private void tryLayEgg() {
        if (hasEggNearby()) return;
        Egg egg = new Egg(chunk);
        egg.setPosition(getX(), getY());
        chunk.entities.addActor(egg);
    }

    private boolean hasEggNearby() {
        for (Actor actor : chunk.entities.getChildren()) {
            if (actor instanceof Egg) {
                float dx = actor.getX() - getX();
                float dy = actor.getY() - getY();
                if (dx * dx + dy * dy < EGG_NEARBY_RADIUS * EGG_NEARBY_RADIUS) {
                    return true;
                }
            }
        }
        return false;
    }

    private void resetEggTimer() {
        eggTimer = MathUtil.RANDOM.nextFloat() * (EGG_MAX_INTERVAL - EGG_MIN_INTERVAL) + EGG_MIN_INTERVAL;
    }
}
