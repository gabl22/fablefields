package me.gabl.fablefields;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.asset.GameAssetManager;
import me.gabl.fablefields.screen.LoadingScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {

    @Override
    public void create() {
        Asset.manager = new GameAssetManager();
        super.setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
