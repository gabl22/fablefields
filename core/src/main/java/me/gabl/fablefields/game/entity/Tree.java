package me.gabl.fablefields.game.entity;

import lombok.AllArgsConstructor;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;

public class Tree extends StaticTextureEntity {


    public static final Type ROUND = new Type("elements/trees/tree_round.png");
    public static final Type SLIM = new Type("elements/trees/tree_slim.png");
    public static final Type CONE = new Type("elements/trees/tree_cone.png");

    public static final Type[] TYPES = new Type[]{ROUND, SLIM, CONE};

    public Tree(MapChunk chunk, Type type) {
        super(chunk, Asset.manager.get(Asset.getTexture(type.texturePath)));
        float width = texture.getWidth() / 16f;
        float height = texture.getHeight() / 16f;
        setSize(width, height);
        setOrigin(width/2, 0);
        setHitbox(HitBox.rect(-width/2, width/2, -height/2, height/2));
    }

    @AllArgsConstructor
    public static final class Type {
        private final String texturePath;
    }
}
