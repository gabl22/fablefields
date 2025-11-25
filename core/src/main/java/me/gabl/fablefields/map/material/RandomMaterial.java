package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;

public class RandomMaterial extends Material {

    private final int[] tileSetIds;
    private final double interval;

    public RandomMaterial(String id, int[] tileSetIds) {
        super(id);
        this.tileSetIds = tileSetIds;
        this.interval = 1.0 / tileSetIds.length;
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        int n = (int) (context.chunk.noise.getValue(context.x, context.y) / interval);
        return RenderInstruction.of(Cell.get(tileSetIds[n]), context);
    }
}
