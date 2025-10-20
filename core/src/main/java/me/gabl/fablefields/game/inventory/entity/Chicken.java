package me.gabl.fablefields.game.inventory.entity;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import me.gabl.fablefields.asset.AnimationD;
import me.gabl.fablefields.asset.AnimationLoader;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;

public class Chicken extends LivingEntity {

    @SuppressWarnings("unchecked")
    public Chicken(MapChunk chunk) {
        super(chunk);
        super.setSize(2, 2);
        setOrigin(1, 0.5f);
        setHitbox(Hitbox.rect(-0.5f, 0.5f, 0.0f, 1.0f));
        setPosition(2, 3);
        getAnimation().setAnimationLayers(new Animation[]{getChickenAnimation()});
    }

    private static Animation<TextureRegion> getChickenAnimation() {
        AssetDescriptor<AnimationD> descriptor = Asset.getAnimation("elements/animals/chicken.png",
            AnimationLoader.params(32, 32, 4, 0.8f, Animation.PlayMode.LOOP)
        );
        return Asset.manager.get(descriptor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        setPosition((getX() - delta/10) % 10 + 10, (getY() - delta/10) % 10 + 10);
    }
}
