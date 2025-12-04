package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;

public class RandomMaterial extends Material {

    private final int options;

    public RandomMaterial(String id, int options) {
        super(id);
        this.options = options;
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        int n = (int) (context.chunk.noise.getValue(context.x, context.y) * options);
        return RenderInstruction.of(Cell.get(id + "/random/" + n), context);
    }
}
