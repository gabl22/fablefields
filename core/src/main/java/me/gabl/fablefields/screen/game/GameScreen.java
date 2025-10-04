package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.map.MapGenerator;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.util.BaseScreen;
import me.gabl.fablefields.screen.util.ScreenMultiplexer;
import me.gabl.fablefields.test.KeyInputManager;
import me.gabl.fablefields.test.OrthoCamController;

public class GameScreen extends BaseScreen {

    public KeyInputManager keyManager;
    private MapChunk chunk;
    private OrthogonalTiledMapRenderer renderer;
    private OrthoCamController controller;
    private Player player;
    private ScreenMultiplexer multiplexer;

    private GameHud gameHud;
    private InventoryHud inventoryHud;

    public GameScreen(Main game) {
        super(game, new FillViewport(800, 600));

        this.multiplexer = new ScreenMultiplexer();
    }

    @Override
    public void show() {
        this.keyManager = new KeyInputManager();

        this.chunk = MapGenerator.getMap();
        this.chunk.initRenderComponent();
        this.chunk.getRenderComponent().initialRender();
        this.renderer = new OrthogonalTiledMapRenderer(this.chunk.getRenderComponent().map, 1f);
        //      this.map = MapGenerator.getMap();
        //      this.renderer = new OrthogonalTiledMapRenderer(this.map, 1f);
        //        this.camera = new OrthographicCamera();
        this.renderer.setView(this.camera);
        this.player = new Player(this);
        this.controller = new OrthoCamController(this.camera, this.player);

        this.inventoryHud = new InventoryHud(batch);
        this.gameHud = new GameHud(batch, game);
        multiplexer.addProcessor(gameHud);
        multiplexer.addProcessor(inventoryHud);
        Gdx.input.setInputProcessor(new InputMultiplexer(this.inventoryHud, this.inventoryHud.getStage(), this.controller, this.keyManager, this.gameHud.getStage()));
        this.stage.addActor(this.player);


        multiplexer.show();
    }

    @Override
    public void render(float delta) {
        // no call to super!

        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update();
        viewport.apply();
        renderer.setView(camera);
        renderer.render();
        stage.act(delta);
        stage.draw();

        multiplexer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        //        this.camera.update();
        //        this.renderer.setView(this.camera);

        multiplexer.resize(width, height);
    }

    @Override
    public void hide() {
        this.chunk.getRenderComponent().map.dispose();
    }

    @Override
    public void dispose() {
        this.chunk.getRenderComponent().map.dispose();
        this.renderer.dispose();
        multiplexer.dispose();
        super.dispose();
    }
}
