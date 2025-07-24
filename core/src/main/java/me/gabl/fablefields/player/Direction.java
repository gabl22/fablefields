package me.gabl.fablefields.player;

public enum Direction {
    LEFT(true),
    RIGHT(false);

    public final boolean flip;
    Direction(boolean flip) {
        this.flip = flip;
    }
}
