package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Entities extends Group {

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {
        getChildren().sort((o1, o2) -> Float.compare(o2.getY(), o1.getY()));
        super.drawChildren(batch, parentAlpha);
    }
}
