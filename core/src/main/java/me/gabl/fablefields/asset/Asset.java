package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import me.gabl.fablefields.map.Material;
import me.gabl.fablefields.map.StaticTile;
import me.gabl.fablefields.map.Tile;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.ActionLayer;

public class Asset {

    public static final AssetDescriptor<Texture> LOGO_LIBGDX = new AssetDescriptor<>("libgdx.png", Texture.class);
    public static final AssetDescriptor<Texture> LOGO = new AssetDescriptor<>("fablefields.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> UI_BOX = new AssetDescriptor<>("ui/box.atlas",
        TextureAtlas.class
    );
    public static final AssetDescriptor<TextureAtlas> UI_SKIN = new AssetDescriptor<>("ui/uiskin.atlas",
        TextureAtlas.class
    );
    public static final AssetDescriptor<Texture> TILESET_TEXTURE = new AssetDescriptor<>("tiles/tileset.png",
        Texture.class
    );
    public static final AssetDescriptor<Texture> TILESET_TEXTURE_DEBUG = new AssetDescriptor<>("tiles/tileset_debug.png",
        Texture.class
    );

    public static final TiledMapTileSet TILESET = new TiledMapTileSet();

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

    public static void completeLoad() {
//        Texture tilesetImage = Asset.get(Asset.TILESET_TEXTURE_DEBUG);
        Texture tilesetImage = Asset.get(Asset.TILESET_TEXTURE);
        for (int i = 0; i < 4096; i++) {
            Asset.TILESET.putTile(i,
                new StaticTile(new TextureRegion(tilesetImage, (i % 64) * 16, (i / 64) * 16, 16, 16))
            );
            //TODO problems rendering at certain viewport sizes =?=
        }

        ((Tile) Asset.TILESET.getTile(68)).setMaterial(Material.WATER);
        ((Tile) Asset.TILESET.getTile(74)).setMaterial(Material.WATER);
        ((Tile) Asset.TILESET.getTile(2438)).setMaterial(Material.WATER);

        ((Tile) Asset.TILESET.getTile(74)).setMaterial(Material.WATER);
        ((Tile) Asset.TILESET.getTile(68)).setMaterial(Material.WATER);
        ((Tile) Asset.TILESET.getTile(74)).setMaterial(Material.WATER);

        ((Tile) Asset.TILESET.getTile(67)).setMaterial(Material.SAND);
        ((Tile) Asset.TILESET.getTile(69)).setMaterial(Material.SAND);
        ((Tile) Asset.TILESET.getTile(73)).setMaterial(Material.SAND);
    }

    public static AssetDescriptor<AnimationD> descriptorHuman(Action action, ActionLayer layer) {
        return new AssetDescriptor<>(GameAssetManager.resolveHumanCharacterPath(action, layer), AnimationD.class);
    }
}
