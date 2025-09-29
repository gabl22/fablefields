package me.gabl.fablefields;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.asset.GameAssetManager;
import me.gabl.fablefields.screen.Screens;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {

    public GameAssetManager assetManager;
    private SpriteBatch batch;
    private Screens screens;

    @Override
    public void create() {
//        this.batch = new SpriteBatch();
        this.screens = new Screens(this);
        this.assetManager = new GameAssetManager();
        Asset.manager = this.assetManager;
        super.setScreen(this.screens.loadingScreen);
    }

//    @Override
//    public void render() {
//        ScreenUtils.clear(0, 0, 0, 1);
//        super.render();
//    }

    @Override
    public void dispose() {
        super.dispose();
//        this.batch.dispose();
        this.screens.dispose();
    }
}
