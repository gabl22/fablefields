package me.gabl.fablefields.map;

import me.gabl.fablefields.map.render.MapTileContext;

public class RandomMaterial extends Material {

    private final int[] tileSetIds;
    private final double interval;

    public RandomMaterial(String id, int[] tileSetIds) {
        super(id);
        this.tileSetIds = tileSetIds;
        this.interval = 1.0 / tileSetIds.length;
    }

    @Override
    public Cell.GfxPair generateCell(MapTileContext context) {
        int n = (int) (context.chunk.noise.getValue(context.x, context.y) / interval);
        return Cell.pair(Cell.get(tileSetIds[n]));
    }
}
