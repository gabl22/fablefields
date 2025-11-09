package me.gabl.fablefields.map;

import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;

public class PlainMaterial extends Material {

    private final int tileSetId;

    public PlainMaterial(String id, int tileSetId) {
        super(id);
        this.tileSetId = tileSetId;
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        return RenderInstruction.of(Cell.get(tileSetId), context);
    }
}
