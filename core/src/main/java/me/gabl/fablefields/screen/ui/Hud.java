package me.gabl.fablefields.screen.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.gabl.fablefields.screen.util.DefaultInputProcessor;

public abstract class Hud implements Screen, DefaultInputProcessor {

    protected final Viewport viewport;
    protected final Stage stage;
    protected final OrthographicCamera camera;

    public Hud(SpriteBatch batch) {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        viewport.apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
    }

    public abstract boolean isHovering();
}
