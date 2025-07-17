package me.gabl.fablefields.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStageContainer implements NothingApplicationListener {

    public final Stage stage;

    public GameStageContainer(Stage stage) {
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
    }


}
