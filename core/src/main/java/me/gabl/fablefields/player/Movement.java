package me.gabl.fablefields.player;

import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.material.Materials;

import java.util.function.Predicate;

public enum Movement {

    WALK(Action.WALKING, 0.12f),
    RUN(Action.RUN, 0.12f),
    SWIM(Action.SWIMMING, 0.08f);

    public final Action action;
    public final float speedFactor;

    Movement(Action action, float speedFrameFactor) {
        this.action = action;
        this.speedFactor = speedFrameFactor / action.frameDuration;
    }

    public static final Predicate<MapTile> WALKABLE = (mapTile -> !Materials.WATER.equals(mapTile.getMaterial()));
    public static final Predicate<MapTile> SWIMMABLE = (mapTile -> Materials.WATER.equals(mapTile.getMaterial()));
    public static final Predicate<MapTile> ACCESSIBLE = (_any -> true);
}
