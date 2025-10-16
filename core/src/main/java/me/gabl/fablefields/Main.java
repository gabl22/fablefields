package me.gabl.fablefields;

import com.badlogic.gdx.Game;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.asset.GameAssetManager;
import me.gabl.fablefields.screen.Screens;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {

    public GameAssetManager assets;
    private Screens screens;

    @Override
    public void create() {
        this.screens = new Screens(this);
        this.assets = new GameAssetManager();
        Asset.manager = this.assets;
        super.setScreen(this.screens.loadingScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.screens.dispose();
    }
}
