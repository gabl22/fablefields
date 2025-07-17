package me.gabl.fablefields.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.util.PlainTiledProgressBar;

public class LoadingScreen implements Screen {

    private final Main game;
    private ScreenViewport viewport;
    private Stage stage;
    private Image logo;
    private PlainTiledProgressBar tb;

    public LoadingScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        Asset.manager.queuePreLoad();
        Asset.manager.finishLoading();
        //assuming quick loading of assets listed in preload
        this.logo = new Image(Asset.manager.get(Asset.LOGO));
        //        this.knob = new TextureRegionDrawable(Asset.manager.get(Asset.UI_BOX).findRegion("white_knob"));
        //        this.background = new TextureRegionDrawable(Asset.manager.get(Asset.UI_BOX).findRegion("light_cc"));
        this.stage = new Stage();

        //        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle();
        //        barStyle.background = this.background;
        //        barStyle.knob = this.knob;
        //        barStyle.knobBefore = this.knob;
        //        barStyle.knobAfter = this.knob;

        //        this.bar = new ProgressBar(0, 10000, 1, false,
        //            barStyle
        //        );
        //        this.bar.setSize(this.bar.getWidth() * 4, this.bar.getHeight() * 4);
        //        this.stage.addActor(this.bar);

        this.tb = new PlainTiledProgressBar(Asset.UI_BOX_LIGHT, Asset.UI_BOX_DARK);
        this.tb.setWidth(400);
        this.tb.setHeight(50);
        this.stage.addActor(this.tb);

        this.stage.addActor(this.logo);
        this.viewport = new ScreenViewport();
        this.stage.setViewport(this.viewport);

        Asset.manager.queueHuman();
    }

    @Override
    public void render(float delta) {
        if (Asset.manager.update(50)) {
            //
        }
        this.tb.setProgress(Asset.manager.getProgress());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        int pixelsPerUnit = (int) (Math.round(Math.sqrt(width * height / 300000f)) + 0.6); //arbitrary number, + 0.6 replaceable e (0.5;1.0) ?
        this.viewport.setUnitsPerPixel(1f / pixelsPerUnit);
        this.viewport.update(width, height, true);
        float worldWidth = this.viewport.getWorldWidth();
        float worldHeight = this.viewport.getWorldHeight();
        this.logo.setX(worldWidth / 2 - this.logo.getWidth() / 2);
        this.logo.setY(worldHeight / 2 - this.logo.getHeight() / 2 - 100);
        this.tb.setX(worldWidth / 2 - this.tb.getWidth() / 2);
        this.tb.setY(worldHeight / 2 - this.tb.getHeight() / 2 + 100);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
