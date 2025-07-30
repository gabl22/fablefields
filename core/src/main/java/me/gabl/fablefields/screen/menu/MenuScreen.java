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

    private transient TextButton startButton;
    private transient TextButton exitButton;
    private transient Table table;
    private transient Image logo;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);
        this.table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();
        stage.setViewport(viewport);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

//        NinePatch ninePatchLight = new NinePatch(Asset.UI_BOX_LIGHT);
//        ninePatchLight.scale(4f, 4f);
//        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(new NinePatchDrawable(ninePatchLight),
//            null, null, new BitmapFont()
//        );
//        NinePatch ninePatch = new NinePatch(Asset.UI_BOX_DARK);
//        ninePatch.scale(4f, 4f);
//        style.over = new NinePatchDrawable(ninePatch);
//        TextButton.TextButtonStyle style2 = new TextButton.TextButtonStyle(new NinePatchDrawable(Asset.UI_BOX_WHITE),
//            new NinePatchDrawable(Asset.UI_BOX_WHITE), new NinePatchDrawable(Asset.UI_BOX_WHITE), new BitmapFont()
//        );
//        style.font = new BitmapFont();
//        this.startButton = new TextButton("Start Game PipaPo", style);
//        startButton.pad(50);
//        startButton.addListener(new ClickListener() {
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                MenuScreen.this.game.setScreen(new GameScreen());
//                startButton.setStyle(style2);
//                return true;
//            }
//        });
//
//        table.add(startButton).fillX().uniformX();

        startButton = new MenuButton("Start Game");
        startButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MenuScreen.this.game.setScreen(new GameScreen());
                return true;
            }
        });
        exitButton = new MenuButton("Exit");
        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MenuScreen.this.game.dispose();
                Gdx.app.exit();
                return true;
            }
        });
//        startButton.setHeight(120);
//        startButton.setWidth(4000);
//        startButton.pad(30);
        table.add(startButton);
        table.add(exitButton);
        table.getCell(startButton).height(50).width(120).pad(50);
        table.getCell(exitButton).height(50).width(120).pad(50);
        this.logo = new Image(Asset.get(Asset.LOGO));
        this.logo.setScale(2f);
        this.stage.addActor(this.logo);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
//        this.camera.zoom = (float) (1/ (Math.floor(Math.min(width / 640f, height / 480f)) + 1));
        this.camera.zoom = 1/Math.min(width /640f, height / 480f);

        this.logo.setX(width / 2 - this.logo.getWidth());
        this.logo.setY(height / 2 - this.logo.getHeight() + 100);

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
