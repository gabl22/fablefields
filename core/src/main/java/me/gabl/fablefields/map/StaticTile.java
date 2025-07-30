package me.gabl.fablefields.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StaticTile extends Tile {

    private TextureRegion texture;

    @Override
    public TextureRegion getTextureRegion() {
        return this.texture;
    }

    @Override
    public void setTextureRegion(TextureRegion texture) {
        this.texture = texture;
    }

    public StaticTile(TextureRegion texture) {
        this.texture = texture;
    }

    public StaticTile(StaticTile copy) {
        super(copy);
        this.texture = copy.texture;
    }
}
