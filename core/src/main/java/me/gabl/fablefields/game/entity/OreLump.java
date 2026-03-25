package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;

public class OreLump extends StaticTextureEntity {

    private static final int STAGE_INITIAL = 2;
    private int stage = STAGE_INITIAL;
    @Getter
    private Type type;

    public OreLump(MapChunk chunk, Type type) {
        super(chunk, "tree", getTexture(type, STAGE_INITIAL));
        float width = 2f;
        float height = 2f;
        setSize(width, height);
        setOrigin(1f, 0.25f);
        setHitbox(HitBox.rect(-0.75f, 0.75f, 0.25f, 1.5f));
        setCollisionBoxRelative(HitBox.rectangle(-0.75f, 0.75f, 0, 1.0f));
        this.type = type;
    }

    private static Texture getTexture(Type type, int stage) {
        return Asset.manager.get(Asset.getTexture("elements/orelump/" + type + "_" + stage + ".png"));
    }

    public void reduce() {
        stage--;
        if (stage < 0) {
            remove();
            return;
        }
        setTexture(getTexture(type, stage));
    }

    @Override
    public boolean remove() {
        boolean removed = super.remove();
        chunk.collision.removeIf(entity -> entity == this);
        return removed;
    }

    public enum Type {
        COAL("coal"), IRON("iron"), GOLD("gold"), DIAMOND("diamond"), STONE("stone");

        private final String id;

        Type(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }
}