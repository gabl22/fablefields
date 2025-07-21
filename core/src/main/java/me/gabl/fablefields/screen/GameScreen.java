package me.gabl.fablefields.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import me.gabl.fablefields.map.Map;
import me.gabl.fablefields.test.OrthoCamController;

public class GameScreen implements Screen {

    private TiledMap map;
//    private TiledMap map2;
    private OrthogonalTiledMapRenderer renderer;
//    private OrthogonalTiledMapRenderer renderer2;
    private OrthographicCamera camera;

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        this.map = Map.getMap();
//        this.map = loader.load("map/test1.tmx");
//        this.map2 = loader.load("map/test2.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 2f);
//        this.renderer2 = new OrthogonalTiledMapRenderer(this.map2, 2f);
        this.camera = new OrthographicCamera();
        this.camera.position.set(-50, 0, 0);
        this.renderer.setView(this.camera);
//        this.renderer2.setView(new OrthographicCamera());
        Gdx.input.setInputProcessor(new OrthoCamController(this.camera));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        this.camera.position.set((this.camera.position.x + 1) % 100, 0, 0);
        camera.update();
        this.renderer.setView(camera);
        this.renderer.render();
//        this.renderer2.render();
    }

    @Override
    public void resize(int width, int height) {
        this.camera.viewportWidth = width;
        this.camera.viewportHeight = height;
        this.camera.update();
        this.renderer.setView(this.camera);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.map.dispose();
    }

    @Override
    public void dispose() {
        this.map.dispose();
        this.renderer.dispose();
    }
}
