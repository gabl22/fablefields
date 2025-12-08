package me.gabl.fablefields.screen.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.screen.game.ExitToMenuHud;
import me.gabl.fablefields.screen.ui.UiSkin;
import me.gabl.fablefields.screen.util.BaseScreen;
import me.gabl.fablefields.screen.util.ScreenMultiplexer;

public class CreditsScreen extends BaseScreen {

    private final Skin skin = UiSkin.skin();
    private ScreenMultiplexer multiplexer;

    public CreditsScreen(Main game) {
        super(game);
    }

    @Override
    public void show() {
        ExitToMenuHud exitToMenuHud = new ExitToMenuHud(this.batch, this.game);
        Gdx.input.setInputProcessor(new InputMultiplexer(exitToMenuHud.getStage(), stage));

        Table root = new Table();
        root.setFillParent(true);
        root.pad(20);
        Label title = new Label("Credits", skin);
        root.add(title).center().padBottom(50).row();

        root.add(creditTable("Graphics", "DanielDiggle", "https://danieldiggle.itch.io/sunnyside")).row();
        root.add().padBottom(40).row();

        stage.addActor(root);

        multiplexer = new ScreenMultiplexer(exitToMenuHud);
        multiplexer.show();
    }

    public Table creditTable(String function, String reference, String url) {
        Table table = new Table();
        table.pad(10);
        Label functionLabel = new Label(function + ":", skin);
        Label referenceLabel = new Label(reference, skin);

        TextButton link = new TextButton("Open Link", skin);
        link.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.net.openURI(url);
                return true;
            }
        });

        table.add(functionLabel).left().width(120);
        table.add(referenceLabel).left().padRight(20).width(200);
        table.add(link).right().width(80);
        return table;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        stage.act(delta);
        stage.draw();
        multiplexer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.zoom = 1f / Math.round(Math.min(width / 640f, height / 480f));
        stage.getViewport().update(width, height, true);
        multiplexer.resize(width, height);
    }

    @Override
    public void hide() {
        multiplexer.hide();
    }

    @Override
    public void dispose() {
        multiplexer.dispose();
    }
}
