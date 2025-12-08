package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.asset.Cursors;
import me.gabl.fablefields.screen.menu.MenuButton;
import me.gabl.fablefields.screen.menu.MenuScreen;
import me.gabl.fablefields.screen.ui.Hud;

public class ExitToMenuHud extends Hud {

    private final Main game;


    public ExitToMenuHud(SpriteBatch batch, Main game) {
        super(batch);

        this.game = game;
    }

    @Override
    public void show() {
        MenuButton exitButton = new MenuButton("Exit");
        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                Cursors.reset();
                return true;
            }
        });

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(exitButton).pad(10);

        stage.addActor(table);
    }

    public Stage getStage() {
        return stage;
    }
}
