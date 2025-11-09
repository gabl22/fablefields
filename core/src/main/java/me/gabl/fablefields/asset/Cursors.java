package me.gabl.fablefields.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

public class Cursors {

    private static Cursor ARROW;
    private static Cursor BUSY;
    private static Cursor GRAB;
    private static Cursor POINTER;
    private static Cursor UNAVAILABLE;

    private static boolean initialized = false;

    public static void load() {
        if (initialized) return;
        ARROW = getCursor("arrow", 0, 8);
        BUSY = getCursor("busy", 16, 14);
        GRAB = getCursor("grab", 10, 9);
        POINTER = getCursor("pointer", 10, 1);
        UNAVAILABLE = getCursor("unavailable", 8, 16);
        initialized = true;
    }

    private static Cursor getCursor(String name, int hotspotX, int hotspotY) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("ui/cursor/" + name + ".png"));
        return Gdx.graphics.newCursor(pixmap, hotspotX, hotspotY);
    }

    public static void arrow() {
        setCursor(ARROW);
    }

    public static void setCursor(Cursor cursor) {
        Gdx.graphics.setCursor(cursor);
    }

    public static void grab() {
        setCursor(GRAB);
    }

    public static void pointer() {
        setCursor(POINTER);
    }

    public static void unavailable() {
        setCursor(UNAVAILABLE);
    }

    public static void busy() {
        setCursor(BUSY);
    }
}
