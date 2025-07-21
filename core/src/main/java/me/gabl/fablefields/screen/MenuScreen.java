package me.gabl.fablefields.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.asset.Asset;

public class MenuScreen implements Screen {

    private final Main game;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();
        stage.setViewport(viewport);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(new NinePatchDrawable(Asset.UI_BOX_LIGHT),
            new NinePatchDrawable(Asset.UI_BOX_DARK), new NinePatchDrawable(Asset.UI_BOX_WHITE), new BitmapFont()
        );
        style.over = new NinePatchDrawable(Asset.UI_BOX_DARK);
        TextButton.TextButtonStyle style2 = new TextButton.TextButtonStyle(new NinePatchDrawable(Asset.UI_BOX_WHITE),
            new NinePatchDrawable(Asset.UI_BOX_WHITE), new NinePatchDrawable(Asset.UI_BOX_WHITE), new BitmapFont()
        );
        style.font = new BitmapFont();
        TextButton startButton = new TextButton("Start Game PipaPo", style);
        startButton.pad(50);
        startButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MenuScreen.this.game.setScreen(new GameScreen());
                startButton.setStyle(style2);
                return true;
            }
        });

        table.add(startButton).fillX().uniformX();
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
