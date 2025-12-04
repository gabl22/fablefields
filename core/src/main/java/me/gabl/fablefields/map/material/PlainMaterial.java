package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;

public class PlainMaterial extends Material {

    public PlainMaterial(String id) {
        super(id);
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        return RenderInstruction.of(Cell.get(id), context);
    }
}
