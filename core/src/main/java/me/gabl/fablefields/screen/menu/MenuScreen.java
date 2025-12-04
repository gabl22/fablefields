package me.gabl.fablefields.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.screen.game.GameScreen;

public class MenuScreen implements Screen {

    private final Main game;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private TextButton startButton;
    private TextButton exitButton;
    private TextButton creditsButton;
    private Table table;
    private Image logo;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);
        this.table = new Table();
        this.table.setFillParent(true);
        this.stage.addActor(this.table);
        this.camera = new OrthographicCamera();
        this.viewport = new ScreenViewport(this.camera);
        this.viewport.apply();
        this.stage.setViewport(this.viewport);
        this.camera.position.set(this.viewport.getWorldWidth() / 2, this.viewport.getWorldHeight() / 2, 0);
        this.camera.update();


        this.startButton = new MenuButton("Start Game");
        this.startButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MenuScreen.this.game.setScreen(new GameScreen(game));
                return true;
            }
        });
        this.exitButton = new MenuButton("Exit");
        this.exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MenuScreen.this.game.dispose();
                Gdx.app.exit();
                return true;
            }
        });
        this.creditsButton = new MenuButton("Credits");
        this.table.add(this.startButton);
        this.table.add(this.exitButton);
        this.table.add(this.creditsButton);
        this.table.getCell(this.startButton).height(50).width(120).pad(40).padTop(100);
        this.table.getCell(this.exitButton).height(50).width(120).pad(40).padTop(100);
        this.table.getCell(this.creditsButton).height(50).width(120).pad(40).padTop(100);
        this.logo = new Image(this.game.assets.get(Asset.LOGO));
        this.logo.setScale(2f);
        this.stage.addActor(this.logo);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
        this.camera.zoom = 1 / Math.min(width / 640f, height / 480f);

        this.logo.setX(width / 2f - this.logo.getWidth());
        this.logo.setY(height / 2f - this.logo.getHeight() + 100);

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
