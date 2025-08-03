package me.gabl.fablefields.map;

import java.util.HashMap;
import java.util.Map;

public class CellNeighborAnalysis {

    public final int neighbors;
    public final Material dominantNeighbor;
    public final int locations;

    public final int neighborCase;
    public final int rotation;

    private CellNeighborAnalysis(int neighbors, Material dominantNeighbor, int locations, int neighborCase, int rotation) {
        this.neighbors = neighbors;
        this.dominantNeighbor = dominantNeighbor;
        this.locations = locations;
        this.neighborCase = neighborCase;
        this.rotation = rotation;
    }

    public static CellNeighborAnalysis get(MapCell<? extends Tile> centerCell) {
        Material cellMaterial = centerCell.getTile().material;
        Map<Material, Integer> neighborCounts = new HashMap<>(); //overkill
        for (MapCell mapCell : new MapCell[]{centerCell.up, centerCell.right, centerCell.down, centerCell.left}) {
            Material mapCellMaterial = Material.getMaterial(mapCell);
            if (!neighborCounts.containsKey(mapCellMaterial)) {
                neighborCounts.put(mapCellMaterial, 1);
            } else {
                neighborCounts.put(mapCellMaterial, neighborCounts.get(mapCellMaterial) + 1);
            }
        }

        //find dominant neighbor material defined as material most present != null != cell material
        Material dominantMaterial = null;
        int dominantMaterialCount = 0;
        for (Map.Entry<Material, Integer> entry : neighborCounts.entrySet()) {
            Material material = entry.getKey();
            Integer count = entry.getValue();
            if (count > dominantMaterialCount && material != null && material != cellMaterial) {
                dominantMaterial = material;
                dominantMaterialCount = count;
            }
        }


        int locations = 0;
        locations += Material.getMaterial(centerCell.up) == dominantMaterial ? 1 : 0;
        locations += Material.getMaterial(centerCell.down) == dominantMaterial ? 2 : 0;
        locations += Material.getMaterial(centerCell.left) == dominantMaterial ? 4 : 0;
        locations += Material.getMaterial(centerCell.right) == dominantMaterial ? 8 : 0;

        int neighborCase = switch (locations) {
            case 1, 2, 4, 8 -> 3;
            case 5, 6, 9, 10 -> 1;
            case 3, 12 -> 2;
            case 7, 11, 13, 14 -> 4;
            case 15 -> 5;
            default -> 0;
        };

        int rotation = switch (locations) {
            case 2, 9, 12, 14, 15 -> MapCell.ROTATE_0;
            case 1, 3, 6, 13 -> MapCell.ROTATE_180;
            case 4, 10, 11 -> MapCell.ROTATE_270;
            case 7, 8, 5 -> MapCell.ROTATE_90;
            default -> MapCell.ROTATE_0;
        };
        return new CellNeighborAnalysis(dominantMaterialCount, dominantMaterial, locations, neighborCase, rotation);
    }

    @Override
    public String toString() {
        return "CellNeighborAnalysis{" + "neighbors=" + neighbors + ", dominantNeighbor=" + dominantNeighbor + ", locations=" + locations + ", neighborCase=" + neighborCase + ", rotation=" + rotation + '}';
    }
}
