package me.gabl.fablefields.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import lombok.Setter;

public class PlainTiledProgressBar extends Actor {

    private final NinePatch completed;
    private final NinePatch notCompleted;
    @Getter
    @Setter
    float progress;

    public PlainTiledProgressBar(NinePatch completed, NinePatch notCompleted) {
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
