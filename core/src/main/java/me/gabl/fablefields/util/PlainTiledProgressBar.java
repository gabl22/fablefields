package me.gabl.fablefields.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import lombok.Getter;
import lombok.Setter;

public class PlainTiledProgressBar extends Actor {

    private final Drawable completed;
    private final Drawable notCompleted;
    @Getter
    @Setter
    float progress;

    public PlainTiledProgressBar(Drawable completed, Drawable notCompleted) {
        this.completed = completed;
        this.notCompleted = notCompleted;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float visualProgress = Math.min(Math.max(0, this.progress), 1);
        this.notCompleted.draw(batch, super.getX(), super.getY(), super.getWidth(), super.getHeight());
        this.completed.draw(batch, super.getX(), super.getY(), super.getWidth() * visualProgress, super.getHeight());
    }
}
