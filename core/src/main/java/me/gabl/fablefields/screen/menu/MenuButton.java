package me.gabl.fablefields.screen.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import me.gabl.fablefields.asset.Asset;

public class MenuButton extends TextButton {

    public static final TextButtonStyle commonStyle = new TextButtonStyle();

    static {
        NinePatch boxLight = new NinePatch(Asset.UI_BOX_LIGHT);
        boxLight.scale(4f, 4f);
        commonStyle.up = new NinePatchDrawable(boxLight);
        NinePatch boxDark = new NinePatch(Asset.UI_BOX_DARK);
        boxDark.scale(4f, 4f);
        commonStyle.over = new NinePatchDrawable(boxDark);
        commonStyle.font = new BitmapFont(); //use default font, to be changed
    }

    public MenuButton(String text) {
        super(text, commonStyle);
    }
}
