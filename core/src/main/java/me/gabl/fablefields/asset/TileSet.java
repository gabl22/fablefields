package me.gabl.fablefields.asset;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TileSet extends TiledMapTileSet {

    public Drawable getDrawable(int tileSetId) {
        return getImage(tileSetId).getDrawable();
    }

    public Image getImage(int tileSetId) {
        return new Image(getTextureRegion(tileSetId));
    }

    public TextureRegion getTextureRegion(int tileSetId) {
        return this.getTile(tileSetId).getTextureRegion();
    }

}
