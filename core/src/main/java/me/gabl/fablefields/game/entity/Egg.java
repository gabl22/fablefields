package me.gabl.fablefields.game.entity;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;

public class Egg extends StaticTextureEntity {

    public Egg(MapChunk chunk) {
        super(chunk, "egg", Asset.manager.get(Asset.getTexture("elements/crops/egg.png")));
        float width = getTexture().getWidth() / 16f;
        float height = getTexture().getHeight() / 16f;
        setSize(width, height);
        setOrigin(width / 2, 0);
        setHitbox(HitBox.rect(-width / 2, width / 2, 0, height));
    }
}
