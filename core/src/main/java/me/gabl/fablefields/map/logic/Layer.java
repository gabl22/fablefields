package me.gabl.fablefields.map.logic;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class Layer<T> {

    public final T[] tiles;

    public final int width;
    public final int height;

    public Layer(T[] tiles, int width, int height) {
        if (tiles.length != width * height || width < 0)
            throw new IllegalArgumentException("Array length must equal width * height, width & height >= 0");
        this.tiles = tiles;
        this.width = width;
        this.height = height;
    }

    //relative x/y coordinates bound by 0 incl and width/height resp. excl.
    public boolean containsTile(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public T get(int x, int y) {
        return tiles[x + y * width];
    }

    public T set(int x, int y, T value) {
        return tiles[x + y * width] = value;
    }

    public int position(int x, int y) {
        return x + y * width;
    }

    public int size() {
        return tiles.length;
    }
}
