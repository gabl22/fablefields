package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import me.gabl.fablefields.util.GdxLogger;

public class HoverListener extends InputListener {

    private boolean hovering = false;

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        hovering = false;
        GdxLogger.get(this.getClass()).info(this.getClass().getName() + " exited");
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        hovering = true;
        GdxLogger.get(this.getClass()).info(this.getClass().getName() + " entered");
    }

    public boolean isHovering() {
        return hovering;
    }
}
