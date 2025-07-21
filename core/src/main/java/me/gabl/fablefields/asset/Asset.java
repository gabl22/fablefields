package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Asset {

    public static final AssetDescriptor<Texture> LOGO_LIBGDX = new AssetDescriptor<>("libgdx.png", Texture.class);
    public static final AssetDescriptor<Texture> LOGO = new AssetDescriptor<>("fablefieldsx2.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> UI_BOX = new AssetDescriptor<>("ui/box.atlas",
        TextureAtlas.class
    );
    public static final AssetDescriptor<TextureAtlas> UI_SKIN = new AssetDescriptor<>("ui/uiskin.atlas",
        TextureAtlas.class
    );
    public static final AssetDescriptor<Texture> TILESET_TEXTURE = new AssetDescriptor<>("tiles/tileset.png",
        Texture.class
    );
    public static final TiledMapTileSet TILESET = new TiledMapTileSet();

    //TODO
    public static GameAssetManager manager;
    public static NinePatchDrawable UI_BOX_DARK;
    public static NinePatchDrawable UI_BOX_LIGHT;
    public static NinePatchDrawable UI_BOX_WHITE;

    static void completePreLoad() {
        UI_BOX_DARK  = new NinePatchDrawable(new NinePatch(Asset.get(UI_BOX).findRegion("dark_knob" ), 3, 3, 3, 3));
        UI_BOX_LIGHT = new NinePatchDrawable(new NinePatch(Asset.get(UI_BOX).findRegion("light_knob"), 3, 3, 3, 3));
        UI_BOX_WHITE = new NinePatchDrawable(new NinePatch(Asset.get(UI_BOX).findRegion("white_knob"), 3, 3, 3, 3));
    }

    public static void completeLoad() {
        Texture tilesetImage = Asset.get(Asset.TILESET_TEXTURE);
        for (int i = 0; i < 4096; i++) {
            Asset.TILESET.putTile(i, new StaticTiledMapTile(new TextureRegion(tilesetImage, (i % 64) * 16, (i / 64) * 16, 16, 16)));
        }
    }

    public static <T> T get(AssetDescriptor<T> descriptor) {
        if (manager.isLoaded(descriptor)) {
            return manager.get(descriptor);
        }
        manager.load(descriptor);
        return manager.finishLoadingAsset(descriptor);
    }
}
