package me.gabl.fablefields.map.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gabl.fablefields.map.MaterialX;

@AllArgsConstructor
@NoArgsConstructor
public class MapTile implements GridNeighbor<MapTile> {

    @Getter
    protected MaterialX material;

    @Override
    public GridNeighborProvider<MapTile> provider() {
        return null;
    }

}
