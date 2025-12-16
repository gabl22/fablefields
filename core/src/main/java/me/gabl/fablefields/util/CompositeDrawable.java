package me.gabl.fablefields.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Collection;

public class CompositeDrawable extends BaseDrawable {

    private final Drawable[] drawables;

    public CompositeDrawable(Drawable... drawables) {
        this.drawables = drawables;

        if (drawables.length > 0) {
            setMinSize(drawables[0].getMinWidth(), drawables[0].getMinHeight());
        }
    }

    public CompositeDrawable(Collection<? extends Drawable> drawables) {
        this(drawables.toArray(new Drawable[0]));
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch, x, y, width, height);
        }
    }
}
