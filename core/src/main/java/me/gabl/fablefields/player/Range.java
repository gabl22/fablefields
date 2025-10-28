package me.gabl.fablefields.player;

public enum Range {

    WEAPON_SHORT(2f),
    WEAPON_LONG(5f),
    TOOL(4f),
    PLACE(4f);

    public final float rangeTiles;

    Range(float rangeTiles) {
        this.rangeTiles = rangeTiles;
    }
}
