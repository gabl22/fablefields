package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import me.gabl.fablefields.player.Player;

@Deprecated
public class GameScreenController extends InputAdapter {

    private final Player player;

    public GameScreenController(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W: {

            }
        }
        return true;
    }
}
