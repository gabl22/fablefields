package me.gabl.fablefields.map;

import me.gabl.fablefields.asset.Asset;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

@Deprecated
public class Material {

    public static final Material SAND = new Material(_ -> {
    }, "sand");
    //primitive code
    public static final Material WATER = new Material(cell -> {
        CellNeighborAnalysis analysis = CellNeighborAnalysis.get(cell);
        if (analysis.dominantNeighbor == null) {
            cell.setTile(Asset.TILESET.getTile(4)); //shadow water tile
            return;
        }
        int baseCellId = 0;
        //        if (analysis.dominantNeighbor.equals(Material.DIRT)) {
        //            baseCellId = 2500 - 1;
        //        } else
        if (analysis.dominantNeighbor.equals(Material.SAND)) {
            baseCellId = 2436 - 1;
        }
        if (baseCellId == 0) {
            return;
        }
        cell.setTile(Asset.TILESET.getTile(analysis.neighborCase + baseCellId));
        cell.setRotation(analysis.rotation);
    }, "water");

    public final Consumer<MapCell<? extends Tile>> renderCell;
    public final String id; //used for equals, logging

    public Material(Consumer<MapCell<? extends Tile>> renderCell, String id) {
        this.renderCell = renderCell;
        this.id = id;
    }

    public Material(String id) {
        this.id = id;
        this.renderCell = _ -> {
        };
    }

    @Nullable
    public static Material getMaterial(MapCell<? extends Tile> cell) {
        if (cell == null || cell.getTile() == null) {
            return null;
        }
        return cell.getTile().material;
    }

    public static Material getMaterial(int cellId) {
        return switch (cellId) {
            case 4, 68, 74, 2438, 2566, 2501, 2503, 2568 -> WATER;
            case 67, 69, 73 -> SAND;
            default -> null;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass())
            return false;

        Material material = (Material) o;
        return Objects.equals(this.id, material.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    // 4: Water Shadow
    // 5: Sand Shadow


    @Override
    public String toString() {
        return "Material{" + "renderCell=" + renderCell + ", id='" + id + '\'' + '}';
    }
}
