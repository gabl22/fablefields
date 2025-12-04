package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import me.gabl.fablefields.map.material.Material;
import me.gabl.fablefields.map.render.MapTileContext;

import java.util.Objects;

public class CellNeighborAnalysis {

    public final int neighbors;
    public final int locations;
    public final int neighborCase;
    public final int rotation;
    public Material dominantNeighbor;

    private CellNeighborAnalysis(int neighbors, Material dominantNeighbor, int locations, int neighborCase,
            int rotation) {
        this.neighbors = neighbors;
        this.dominantNeighbor = dominantNeighbor;
        this.locations = locations;
        this.neighborCase = neighborCase;
        this.rotation = rotation;
    }

    public static CellNeighborAnalysis get(MapTileContext context) {
        Material centralMaterial = getMaterial(context);
        Material materialUp = getMaterial(context.up());
        Material materialDown = getMaterial(context.down());
        Material materialLeft = getMaterial(context.left());
        Material materialRight = getMaterial(context.right());

        Material dominantMaterial = null;
        int dominantCount = 0;

        Material[] neighbors = new Material[]{materialUp, materialDown, materialLeft, materialRight};
        for (int i = 0; i < neighbors.length; i++) {
            if (Objects.equals(centralMaterial, neighbors[i]) || neighbors[i] == null) {
                continue;
            }
            Material checkMaterial = neighbors[i];
            int checkCount = 0;
            for (Material neighbor : neighbors) {
                if (checkMaterial.equals(neighbor)) {
                    checkCount++;
                }
            }
            if (checkCount > dominantCount) {
                dominantCount = checkCount;
                dominantMaterial = checkMaterial;
            }
        }

        int locations = 0;
        locations += materialUp == dominantMaterial ? 1 : 0;
        locations += materialDown == dominantMaterial ? 2 : 0;
        locations += materialLeft == dominantMaterial ? 4 : 0;
        locations += materialRight == dominantMaterial ? 8 : 0;

        int neighborCase = switch (locations) {
            case 1, 2, 4, 8 -> 2;
            case 5, 6, 9, 10 -> 0;
            case 3, 12 -> 1;
            case 7, 11, 13, 14 -> 3;
            case 15 -> 4;
            default -> -1;
        };

        int rotation = switch (locations) {
            //            case 2, 9, 12, 14, 15 -> TiledMapTileLayer.Cell.ROTATE_0;
            case 1, 6, 13 -> TiledMapTileLayer.Cell.ROTATE_180;
            case 4, 10, 7 -> TiledMapTileLayer.Cell.ROTATE_270;
            case 11, 3, 8, 5 -> TiledMapTileLayer.Cell.ROTATE_90;
            default -> TiledMapTileLayer.Cell.ROTATE_0;
        };
        return new CellNeighborAnalysis(dominantCount, dominantMaterial, locations, neighborCase, rotation);
    }

    private static Material getMaterial(MapTileContext context) {
        if (context.tile == null) return null;
        return context.tile.material;
    }

    @Override
    public String toString() {
        return "CellNeighborAnalysis{" + "neighbors=" + neighbors + ", dominantNeighbor=" + dominantNeighbor + ", " +
                "locations=" + locations + ", neighborCase=" + neighborCase + ", rotation=" + rotation + '}';
    }
}
