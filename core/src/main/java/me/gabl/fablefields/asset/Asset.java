package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Asset {

    public static final AssetDescriptor<Texture> LOGO_LIBGDX = new AssetDescriptor<>("libgdx.png", Texture.class);
    public static final AssetDescriptor<Texture> LOGO = new AssetDescriptor<>("fablefieldsx2.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> UI_BOX = new AssetDescriptor<>("ui/box.atlas",
        TextureAtlas.class
    );
    //TODO
    public static GameAssetManager manager;
    public static NinePatch UI_BOX_DARK;
    public static NinePatch UI_BOX_LIGHT;
    public static NinePatch UI_BOX_WHITE;

    static void completePreLoad() {
        UI_BOX_DARK = new NinePatch(Asset.get(UI_BOX).findRegion("dark_knob"), 3, 3, 3, 3);
        UI_BOX_LIGHT = new NinePatch(Asset.get(UI_BOX).findRegion("light_knob"), 3, 3, 3, 3);
        UI_BOX_WHITE = new NinePatch(Asset.get(UI_BOX).findRegion("white_knob"), 3, 3, 3, 3);
    }

    public static <T> T get(AssetDescriptor<T> descriptor) {
        if (manager.isLoaded(descriptor)) {
            return manager.get(descriptor);
        }
        manager.load(descriptor);
        return manager.finishLoadingAsset(descriptor);
    }
}
