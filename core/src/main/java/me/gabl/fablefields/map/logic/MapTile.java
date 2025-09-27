package me.gabl.fablefields.map.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gabl.fablefields.map.Material;

@AllArgsConstructor
@NoArgsConstructor
public class MapTile implements GridNeighbor<MapTile> {

    @Getter
    protected Material material;

    @Override
    public GridNeighborProvider<MapTile> provider() {
        return null;
    }

}
