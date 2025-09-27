package me.gabl.fablefields.map.render;

import me.gabl.fablefields.map.Cell;

public interface RenderMaterial {

    Cell.GfxPair generateCell(MapTileContext context);

}
