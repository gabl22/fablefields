package me.gabl.fablefields.map.render;

import me.gabl.fablefields.map.logic.Address;
import me.gabl.fablefields.map.logic.MapLayer;

public class ContextAddress extends Address {

    public final int width;

    public ContextAddress(int position, MapLayer layer, int width) {
        super(position, layer);
        this.width = width;
    }

    public ContextAddress up() {
        return getRelative(0, 1);
    }

    public ContextAddress getRelative(int dx, int dy) {
        return new ContextAddress(Address.position(x() + dx, y() + dy, width), layer, width);
    }

    public int x() {
        return super.x(width);
    }

    public int y() {
        return super.y(width);
    }

    public ContextAddress down() {
        return getRelative(0, -1);
    }

    public ContextAddress left() {
        return getRelative(-1, 0);
    }

    public ContextAddress right() {
        return getRelative(1, 0);
    }

    @Override
    public int x(int width) {
        if (this.width != width) {
            throw new AssertionError("Address requested with width different to contextual width! (%s != %s)".formatted(width, width));
        }
        return super.x(width);
    }

    @Override
    public int y(int width) {
        if (this.width != width) {
            throw new AssertionError("Address requested with width different to contextual width! (%s != %s)".formatted(width, width));
        }
        return super.y(width);
    }
}
