package me.gabl.fablefields.map.logic;

public interface GridNeighborProvider<T> {

    T up(T cell);
    T down(T cell);
    T left(T cell);
    T right(T cell);
}
