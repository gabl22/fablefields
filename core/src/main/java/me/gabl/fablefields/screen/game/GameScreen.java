package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.game.inventory.InventoryHud;
import me.gabl.fablefields.game.inventory.Item;
import me.gabl.fablefields.game.inventory.Slot;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.game.inventory.item.Tool;
import me.gabl.fablefields.map.MapGenerator;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.util.BaseScreen;
import me.gabl.fablefields.screen.util.ScreenMultiplexer;
import me.gabl.fablefields.test.KeyInputManager;

public class GameScreen extends BaseScreen {

    private final ScreenMultiplexer multiplexer;
    public Inventory inventory;

    public KeyInputManager keyManager;
    public OrthographicCameraController controller;
    @Getter
    private MapChunk chunk;
    private OrthogonalTiledMapRenderer renderer;
    private Player player;
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
        this.renderer = new OrthogonalTiledMapRenderer(this.chunk.getRenderComponent().map, 1f / Asset.TILE_SIZE,
            super.batch
        );
        this.renderer.setView(this.camera);
        this.player = new Player(this, this.chunk);
        this.controller = new OrthographicCameraController(viewport, this.player);

        this.inventory = new Inventory(30);
        //TODO
        inventory.setSlot(0, new Slot(new Item(Tool.SHOVEL), 1));
        inventory.setSlot(1, new Slot(new Item(Tool.AXE), 1));
        inventory.setSlot(2, new Slot(new Item(Tool.HOE), 1));
        inventory.setSlot(3, new Slot(new Item(Seed.CARROT), 50));

        this.inventoryHud = new InventoryHud(batch, inventory);
        this.gameHud = new GameHud(batch, game);
        multiplexer.addProcessor(gameHud);
        multiplexer.addProcessor(inventoryHud);
        Gdx.input.setInputProcessor(
            new InputMultiplexer(this.gameHud.getStage(), this.inventoryHud, this.inventoryHud.getStage(), this.controller, this.keyManager,
                this.player.worldController
            ));
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

    public Viewport getViewport() {
        return viewport;
    }
}
