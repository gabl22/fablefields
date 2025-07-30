package me.gabl.fablefields.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.apache.commons.lang3.NotImplementedException;

@Deprecated
public class AnimatedTile extends Tile {

    public AnimatedTile() {
        throw new NotImplementedException("AnimatedTile not yet implemented -> if needed"); // @todo
    }

    @Override
    public TextureRegion getTextureRegion() {
        return null;
    }

    @Override
    public void setTextureRegion(TextureRegion textureRegion) {

    }
}
