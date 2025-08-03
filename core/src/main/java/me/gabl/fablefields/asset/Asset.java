package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import me.gabl.fablefields.map.Material;
import me.gabl.fablefields.map.StaticTile;
import me.gabl.fablefields.map.Tile;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.ActionLayer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Asset {

    public static final AssetLoadDescriptor<Texture> LOGO_LIBGDX = new AssetLoadDescriptor<>("libgdx.png",
        Texture.class, LoadSection.NEVER
    );
    public static final AssetLoadDescriptor<Texture> LOGO = new AssetLoadDescriptor<>("fablefields.png", Texture.class,
        LoadSection.BEFORE_LOADING_SCREEN
    );
    public static final AssetLoadDescriptor<TextureAtlas> UI_BOX = new AssetLoadDescriptor<>("ui/box.atlas",
        TextureAtlas.class, LoadSection.BEFORE_LOADING_SCREEN
    );
    public static final AssetLoadDescriptor<TextureAtlas> UI_SKIN = new AssetLoadDescriptor<>("ui/uiskin.atlas",
        TextureAtlas.class, LoadSection.NEVER
    );
    public static final AssetLoadDescriptor<Texture> TILESET_TEXTURE = new AssetLoadDescriptor<>("tiles/tileset.png",
        Texture.class, LoadSection.BEFORE_GAME_SCREEN
    );
    public static final AssetLoadDescriptor<Texture> TILESET_TEXTURE_DEBUG = new AssetLoadDescriptor<>(
        "tiles/tileset_debug.png", Texture.class, LoadSection.BEFORE_GAME_SCREEN);

    public static final TiledMapTileSet TILESET = new TiledMapTileSet();

    //TODO
    public static GameAssetManager manager;
    public static NinePatch UI_BOX_DARK;
    public static NinePatch UI_BOX_LIGHT;
    public static NinePatch UI_BOX_WHITE;

    public static int sectionCompleted;

    public static void completeLoad(int section) {
        if (LoadSection.BEFORE_LOADING_SCREEN > Asset.sectionCompleted && LoadSection.BEFORE_LOADING_SCREEN <= section) {
            UI_BOX_DARK = new NinePatch(Asset.manager.get(UI_BOX).findRegion("dark_knob"), 3, 3, 3, 3);
            UI_BOX_LIGHT = new NinePatch(Asset.manager.get(UI_BOX).findRegion("light_knob"), 3, 3, 3, 3);
            UI_BOX_WHITE = new NinePatch(Asset.manager.get(UI_BOX).findRegion("white_knob"), 3, 3, 3, 3);
        }
        if (LoadSection.BEFORE_GAME_SCREEN > Asset.sectionCompleted && LoadSection.BEFORE_GAME_SCREEN <= section) {
            Texture tilesetImage = Asset.manager.get(Asset.TILESET_TEXTURE);
            //            Texture tilesetImage = Asset.manager.get(Asset.TILESET_TEXTURE_DEBUG);
            for (int i = 0; i < 4096; i++) {
                Tile tile = new StaticTile(new TextureRegion(tilesetImage, (i % 64) * 16, (i / 64) * 16, 16, 16));
                tile.setMaterial(Material.getMaterial(i));
                Asset.TILESET.putTile(i, tile);

                //TODO problems rendering at certain viewport sizes =?=
            }


//            ((Tile) Asset.TILESET.getTile(68)).setMaterial(Material.WATER);
//            ((Tile) Asset.TILESET.getTile(74)).setMaterial(Material.WATER);
//            ((Tile) Asset.TILESET.getTile(2438)).setMaterial(Material.WATER);
//
//            ((Tile) Asset.TILESET.getTile(74)).setMaterial(Material.WATER);
//            ((Tile) Asset.TILESET.getTile(68)).setMaterial(Material.WATER);
//            ((Tile) Asset.TILESET.getTile(74)).setMaterial(Material.WATER);
//
//            ((Tile) Asset.TILESET.getTile(67)).setMaterial(Material.SAND);
//            ((Tile) Asset.TILESET.getTile(69)).setMaterial(Material.SAND);
//            ((Tile) Asset.TILESET.getTile(73)).setMaterial(Material.SAND);
            //...
        }

        Asset.sectionCompleted = section;
    }

    public static void registerAll(GameAssetManager manager) {
        manager.registerAssets(LOGO_LIBGDX, LOGO, UI_BOX, UI_SKIN, TILESET_TEXTURE, TILESET_TEXTURE_DEBUG);
        manager.registerAssets(getHumanAnimationDescriptors());
    }

    public static Collection<AssetLoadDescriptor<?>> getHumanAnimationDescriptors() {
        Set<AssetLoadDescriptor<?>> descriptors = new HashSet<>();
        for (Action action : Action.values()) {
            for (ActionLayer layer : ActionLayer.values()) {
                descriptors.add(getHumanAnimationDescriptor(action, layer));
            }
        }
        return descriptors;
    }

    static AssetLoadDescriptor<AnimationD> getHumanAnimationDescriptor(Action action, ActionLayer layer) {
        return new AssetLoadDescriptor<>(resolveHumanCharacterPath(action, layer), AnimationD.class,
            action.getParameters(), LoadSection.BEFORE_GAME_SCREEN
        );
    }

    static String resolveHumanCharacterPath(Action action, ActionLayer layer) {
        return "characters/human/" + action.id + "/" + layer.id + "_" + action.id + ".png";
    }

    public static AssetDescriptor<AnimationD> descriptorHuman(Action action, ActionLayer layer) {
        return new AssetDescriptor<>(resolveHumanCharacterPath(action, layer), AnimationD.class,
            action.getParameters()
        );
    }
}
