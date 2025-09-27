package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.gabl.fablefields.map.MapGenerator;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.test.KeyInputManager;
import me.gabl.fablefields.test.OrthoCamController;

public class GameScreen implements Screen {

//    private TiledMap map;
    private MapChunk chunk;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private OrthoCamController controller;
    private Stage stage;
    private Player player;
    private Viewport viewport;
    public KeyInputManager keyManager;

    @Override
    public void show() {
        this.keyManager = new KeyInputManager();

        this.chunk = MapGenerator.getMapx();
        this.chunk.initRenderComponent();
        this.chunk.getRenderComponent().initialRender();
        this.renderer = new OrthogonalTiledMapRenderer(this.chunk.getRenderComponent().map, 1f);
//      this.map = MapGenerator.getMap();
//      this.renderer = new OrthogonalTiledMapRenderer(this.map, 1f);
        this.camera = new OrthographicCamera();
        this.camera.position.set(-50, 0, 0);
        this.renderer.setView(this.camera);
        this.player = new Player(this);
        this.controller = new OrthoCamController(this.camera, this.player);

        this.viewport = new FitViewport(800, 600, this.camera);
        this.viewport.apply();
        this.stage = new Stage(this.viewport);

        Gdx.input.setInputProcessor(new InputMultiplexer(this.controller, this.keyManager));
        this.stage.addActor(this.player);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.controller.update();
        this.renderer.setView(this.camera);
        this.renderer.render();
        this.stage.act(delta);
        this.stage.draw();
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
        this.chunk.getRenderComponent().map.dispose();
    }

    @Override
    public void dispose() {
        this.chunk.getRenderComponent().map.dispose();
        this.renderer.dispose();
    }
}
