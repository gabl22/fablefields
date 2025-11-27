package me.gabl.fablefields.map.logic;

public class Address {

    public final int position;
    public final MapLayer layer;

    public Address(int position, MapLayer layer) {
        this.position = position;
        this.layer = layer;
        assert layer == MapLayer.GROUND || layer == MapLayer.SURFACE || layer == MapLayer.FEATURE;
    }

    public static int position(int x, int y, int width) {
        // assertion for testing
        assert x >= 0 && x < width && y >= 0 && y < width;
        return x + y * width;
    }

    public int x(int width) {
        return position % width;
    }

    public int y(int width) {
        return position / width;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;
        return position == address.position && layer == address.layer;
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 1593131 * result + layer.hashCode();
        return result;
    }
}
