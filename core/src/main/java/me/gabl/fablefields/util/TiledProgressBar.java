package me.gabl.fablefields.util;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import lombok.Getter;

public class TiledProgressBar extends PlainTiledProgressBar {

    @Getter
    private float value, min, max;

    public TiledProgressBar(float value, float min, float max, Drawable completed, Drawable notCompleted) {
        super(completed, notCompleted);
        this.value = value;
        this.min = min;
        this.max = max;
        super.progress = (value - min) / (max - min);
    }

    @Override
    public void setProgress(float progress) {
        this.progress = progress;
        this.value = this.min + progress * (this.max - this.min);
    }

    public void setValue(float value) {
        this.value = value;
        this.updateProgress();
    }

    private void updateProgress() {
        this.progress = (this.value - this.min) / (this.max - this.min);
    }

    public void setMin(float min) {
        this.min = min;
        this.updateProgress();
    }

    public void setMax(float max) {
        this.max = max;
        this.updateProgress();
    }
}

