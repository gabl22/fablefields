package me.gabl.fablefields.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Setter;
import me.gabl.fablefields.map.logic.MapChunk;

public class StaticTextureEntity extends Entity {

    public Texture texture;
    @Setter
    private boolean flip;

    public StaticTextureEntity(MapChunk chunk, String id, Texture texture) {
        super(chunk, id);
        this.texture = texture;
    }

    public StaticTextureEntity(MapChunk chunk, String id) {
        super(chunk, id);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.draw(batch);
    }

    public void draw(Batch batch) {
        batch.draw(texture, getX() - getOriginX(), getY() - getOriginY(), getWidth(), getHeight(), 0, 0,
                texture.getWidth(), texture.getHeight(), this.flip, false);
    }
}
