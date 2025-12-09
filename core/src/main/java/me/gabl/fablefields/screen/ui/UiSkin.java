package me.gabl.fablefields.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import lombok.Getter;
import me.gabl.fablefields.asset.Asset;

public class UiSkin {

    private UiSkin() {
        throw new UnsupportedOperationException();
    }

    @Getter
    private static TextButton.TextButtonStyle buttonStyle;

    public static Skin skin() {
        Skin skin = new Skin();
        addBoxes(skin);

        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        Label.LabelStyle labelStyleBackground = new Label.LabelStyle();
        labelStyleBackground.font = font;
        labelStyleBackground.fontColor = Color.BLACK;
        labelStyleBackground.background = skin.getDrawable("box-white-2");
        skin.add("background", labelStyleBackground);

        if (buttonStyle == null) {
            buttonStyle = new TextButton.TextButtonStyle();
            buttonStyle.font = font;
            buttonStyle.fontColor = Color.WHITE;

            buttonStyle.up = skin.getDrawable("box-light-2");
            buttonStyle.down = skin.getDrawable("box-dark-2");
            buttonStyle.over = skin.getDrawable("box-dark-2");
            buttonStyle.disabled = skin.getDrawable("box-white-2");
        }
        skin.add("default", buttonStyle);


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
        }
    }

}
