package me.gabl.fablefields.map.logic;

import lombok.AllArgsConstructor;
import me.gabl.fablefields.map.Material;
import me.gabl.fablefields.map.Materials;

@AllArgsConstructor
public class MapTile {

    public final Material material;

    public boolean isWalkable() {
        return !Materials.WATER.equals(material);
    }
}
