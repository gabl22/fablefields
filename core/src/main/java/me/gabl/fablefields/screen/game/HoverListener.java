package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class HoverListener extends InputListener {

    private boolean hovering = false;

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        hovering = false;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        hovering = true;
    }

    public boolean isHovering() {
        return hovering;
    }
}
