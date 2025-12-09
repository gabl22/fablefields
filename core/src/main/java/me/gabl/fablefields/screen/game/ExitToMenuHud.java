package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.gabl.fablefields.Main;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.asset.Cursors;
import me.gabl.fablefields.screen.menu.MenuButton;
import me.gabl.fablefields.screen.menu.MenuScreen;
import me.gabl.fablefields.screen.ui.Hud;
import me.gabl.fablefields.screen.ui.UiSkin;

public class ExitToMenuHud extends Hud {

    private final MenuButton button;

    public ExitToMenuHud(SpriteBatch batch, Main game) {
        super(batch);
        this.button = new MenuButton(Asset.LANGUAGE_SERVICE.get("navigation/exit_game"), UiSkin.getButtonStyle());
        this.button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                Cursors.reset();
                return true;
            }
        });
    }

    @Override
    public void show() {
        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(this.button).pad(10);

        stage.addActor(table);
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public boolean isHovering() {
        return this.button.isOver();
    }
}
