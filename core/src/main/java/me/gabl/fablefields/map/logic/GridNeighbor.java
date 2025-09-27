package me.gabl.fablefields.map.logic;

public interface GridNeighbor<T extends GridNeighbor<T>> {

    default T up() {
        return provider().up((T) this);
    }

    default T down() {
        return provider().down((T) this);
    }

    default T left() {
        return provider().left((T) this);
    }

    default T right() {
        return provider().right((T) this);
    }

    GridNeighborProvider<T> provider();
}
