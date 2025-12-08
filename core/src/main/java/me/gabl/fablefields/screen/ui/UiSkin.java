package me.gabl.fablefields.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import me.gabl.fablefields.asset.Asset;

public class UiSkin {

    private UiSkin() {
        throw new UnsupportedOperationException();
    }

    public static Skin skin() {
        Skin skin = new Skin();
        addBoxes(skin);

        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;

        textButtonStyle.up = skin.getDrawable("box-light-2");
        textButtonStyle.down = skin.getDrawable("box-dark-2");
        textButtonStyle.over = skin.getDrawable("box-dark-2");
        textButtonStyle.disabled = skin.getDrawable("box-white-2");

        skin.add("default", textButtonStyle);


        return skin;
    }

    private static void addBoxes(Skin skin) {
        for (int i : new int[]{1, 2, 4}) {
            NinePatch white = new NinePatch(Asset.UI_BOX_WHITE);
            NinePatch light = new NinePatch(Asset.UI_BOX_LIGHT);
            NinePatch dark = new NinePatch(Asset.UI_BOX_DARK);
            white.scale(i, i);
            light.scale(i, i);
            dark.scale(i, i);
            skin.add("box-white-" + i, white);
            skin.add("box-light-" + i, light);
            skin.add("box-dark-" + i, dark);
            //            skin.add("box-white-" + i, new NinePatchDrawable(white));
            //            skin.add("box-light-" + i, new NinePatchDrawable(light));
            //            skin.add("box-dark-" + i, new NinePatchDrawable(dark));
        }
    }

}
