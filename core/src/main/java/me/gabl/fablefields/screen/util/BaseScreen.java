package me.gabl.fablefields.screen.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.gabl.fablefields.Main;

public class BaseScreen implements Screen {

    protected final Main game;

    protected final Viewport viewport;
    protected final SpriteBatch batch;
    protected final OrthographicCamera camera;
    protected final Stage stage;


    protected BaseScreen(Main game) {
        this(game, new ScreenViewport());
    }

    protected BaseScreen(Main game, Viewport viewport) {
        this(game, viewport, 800, 600);
    }

    protected BaseScreen(Main game, Viewport viewport, int worldWidth, int worldHeight) {
        this.game = game;

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.viewportWidth = worldWidth;
        this.camera.viewportHeight = worldHeight;
//        this.viewport = new ScreenViewport(camera);
        this.viewport = viewport;
        this.viewport.setCamera(camera);
//        this.camera.position.set(this.viewport.getWorldWidth() / 2, this.viewport.getWorldHeight() / 2, 0);
        this.stage = new Stage(viewport, batch);

        // if default stage viewport = screenviewport is used
//        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        viewport.apply(true);
        camera.update();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        //        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //        viewport.update(width, height, true);
        viewport.update(width, height, false);
//        camera.viewportWidth = width;
//        camera.viewportHeight = height;
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
        stage.dispose();
        batch.dispose();
    }
}
