package me.gabl.fablefields.map;

import me.gabl.fablefields.map.render.MapTileContext;

public class PlainMaterial extends Material {

    private final int tileSetId;

    public PlainMaterial(String id, int tileSetId) {
        super(id);
        this.tileSetId = tileSetId;
    }

    @Override
    public Cell.GfxPair generateCell(MapTileContext context) {
        return Cell.pair(Cell.get(tileSetId));
    }
}
