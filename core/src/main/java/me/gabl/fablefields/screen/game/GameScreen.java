package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.entity.Tree;
import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.game.inventory.InventoryHud;
import me.gabl.fablefields.game.entity.Chicken;
import me.gabl.fablefields.game.entity.Entity;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.map.MapGenerator;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.CursorManager;
import me.gabl.fablefields.player.Movement;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.util.BaseScreen;
import me.gabl.fablefields.screen.util.ScreenMultiplexer;
import me.gabl.fablefields.task.ActSynchronousScheduler;
import me.gabl.fablefields.util.MathUtil;
import me.gabl.fablefields.util.ScreenUtil;

public class GameScreen extends BaseScreen {

    private final ScreenMultiplexer multiplexer;

    public KeyInputManager keyManager;
    public OrthographicCameraController camController;
    public InventoryHud inventoryHud;
    public ActSynchronousScheduler syncScheduler;
    @Getter
    private MapChunk chunk;
    private OrthogonalTiledMapRenderer renderer;
    private Player player;
    private ExitToMenuHud exitToMenuHud;
    private CursorManager cursorManager;

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
                super.batch);
        this.renderer.setView(this.camera);
        this.player = new Player(this, this.chunk);
        this.player.setPosition(chunk.width / 2 + 0.5f, chunk.height / 2 + 0.5f);
        this.camController = new OrthographicCameraController(viewport, this.player);

        Inventory inventory = new Inventory(InventoryHud.SLOTS);
        //TODO
        inventory.addItem(Tools.SWORD, 1);
        inventory.addItem(Tools.SHOVEL, 1);
        inventory.addItem(Tools.HOE, 1);
        inventory.addItem(Tools.WATERING_CAN, 1);
        inventory.addItem(Tools.AXE, 1);
        inventory.addItem(Seed.CARROT, 10);
        inventory.addItem(Seed.CAULIFLOWER, 10);
        inventory.addItem(Seed.PUMPKIN, 10);
        inventory.addItem(Seed.SUNFLOWER, 10);
        inventory.addItem(Seed.RADISH, 10);
        inventory.addItem(Seed.PARSNIP, 10);
        inventory.addItem(Seed.POTATO, 10);
        inventory.addItem(Seed.CABBAGE, 10);
        inventory.addItem(Seed.BEETROOT, 10);
        inventory.addItem(Seed.WHEAT, 10);
        inventory.addItem(Seed.LETTUCE, 10);


        player.inventory = inventory;

        this.inventoryHud = new InventoryHud(batch, inventory);
        this.exitToMenuHud = new ExitToMenuHud(batch, game);
        multiplexer.addProcessor(exitToMenuHud);
        multiplexer.addProcessor(inventoryHud);
        Gdx.input.setInputProcessor(new InputMultiplexer(this.exitToMenuHud.getStage(), this.inventoryHud,
                this.inventoryHud.getStage(), this.camController, this.keyManager, this.player.worldController));

        Entities entities = new Entities();
        stage.addActor(entities);

        entities.addActor(player);
        entities.addActor(new Chicken(chunk));

        for (int i = 0; i < 500; i++) {
            float x = MathUtil.RANDOM.nextFloat() * chunk.width;
            float y = MathUtil.RANDOM.nextFloat() * chunk.height;
            if (chunk.is(Movement.WALKABLE, x, y)) {
                Tree tree = new Tree(chunk, Tree.TYPES[i % Tree.TYPES.length]);
                entities.addActor(tree);
                tree.setPosition(x, y);
            }
        }

        this.cursorManager = new CursorManager(player, this, chunk);
        multiplexer.show();

        this.syncScheduler = new ActSynchronousScheduler();
    }

    @Override
    public void render(float delta) {
        // no call to super!

        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camController.update();
        cursorManager.update();
        viewport.apply();
        renderer.setView(camera);
        renderer.render();
        stage.act(delta);
        stage.draw();

        multiplexer.render(delta);
        syncScheduler.commitExecute(delta);
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

    public Stage getStage() {
        return stage;
    }

    public Entity entityHitCursor() {
        return ScreenUtil.hit(Gdx.input.getX(), Gdx.input.getY(), this);
    }
}
