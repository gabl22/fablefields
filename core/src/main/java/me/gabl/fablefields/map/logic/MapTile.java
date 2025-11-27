package me.gabl.fablefields.map.logic;

import lombok.Getter;
import me.gabl.fablefields.map.material.Material;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.render.ContextAddress;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;

public class MapTile {

    @Getter
    public final Material material;
    public final ContextAddress address;

    @Deprecated
    public MapTile(Material material) {
        this(material, null);
    }

    public MapTile(Material material, ContextAddress address) {
        this.material = material;
        this.address = address;
    }

    public boolean isWalkable() {
        return !Materials.WATER.equals(material);
    }

    public RenderInstruction[] render(MapTileContext context) {
        return material.render(context);
    }
}
