package me.gabl.fablefields.asset;

import com.badlogic.gdx.graphics.Color;

@Deprecated
public final class ColorScheme {
    private ColorScheme() {
        throw new UnsupportedOperationException();
    }

    public static final Color PRIMARY = new Color(0.722f, 0.435f, 0.314f, 1f);
    public static final Color SECONDARY = new Color(0.894f, 0.651f, 0.447f, 1f);
    public static final Color BACKGROUND = new Color(0.094f, 0.078f, 0.145f, 1f);
}
