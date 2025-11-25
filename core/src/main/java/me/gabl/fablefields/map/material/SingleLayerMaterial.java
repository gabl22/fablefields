package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;

public abstract class SingleLayerMaterial extends Material {

    public SingleLayerMaterial(String id) {
        super(id);
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        return RenderInstruction.of(Cell.get(renderTileSetId(context)), context.getAddress());
    }

    public abstract int renderTileSetId(MapTileContext context);
}
