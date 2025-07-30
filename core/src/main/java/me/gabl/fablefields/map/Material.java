package me.gabl.fablefields.map;

import me.gabl.fablefields.asset.Asset;

import java.util.function.Consumer;

public class Material {

    public static final Material SAND = new Material(_ -> {
    });
    //primitive code
    public static final Material WATER = new Material(cell -> {
        int neighbors = 0;
        neighbors += getMaterial(cell.up) == Material.SAND ? 1 : 0;
        neighbors += getMaterial(cell.down) == Material.SAND ? 2 : 0;
        neighbors += getMaterial(cell.left) == Material.SAND ? 4 : 0;
        neighbors += getMaterial(cell.right) == Material.SAND ? 8 : 0;
        switch (neighbors) {
            case 0: {
                break;
            }
            case 1, 2, 4, 8: {
                cell.setTile(Asset.TILESET.getTile(2438));
                break;
            }
            case 5, 6, 9, 10: {
                cell.setTile(Asset.TILESET.getTile(2566));
                break;
            }
            case 3, 12: {
                cell.setTile(Asset.TILESET.getTile(2501));
                break;
            }
            case 7, 11, 13, 14: {
                cell.setTile(Asset.TILESET.getTile(2503));
                break;
            }
            case 15: {
                cell.setTile(Asset.TILESET.getTile(2568));
                break;
            }
        }

        switch (neighbors) {
            case 0, 2, 9, 12, 14, 15: {
                cell.setRotation(MapCell.ROTATE_0);
                break;
            }
            case 1, 3, 6, 13: {
                cell.setRotation(MapCell.ROTATE_180);
                break;
            }
            case 4, 10, 11: {
                cell.setRotation(MapCell.ROTATE_270);
                break;
            }
            case 7, 8, 5: {
                cell.setRotation(MapCell.ROTATE_90);
                break;
            }
        }

    });
    public final Consumer<MapCell<? extends Tile>> renderCell;

    public Material(Consumer<MapCell<? extends Tile>> renderCell) {
        this.renderCell = renderCell;
    }

    public Material() {
        this.renderCell = _ -> {
        };
    }

    private static Material getMaterial(MapCell<? extends Tile> cell) {
        if (cell == null || cell.getTile() == null) {
            return null;
        }
        return cell.getTile().material;
    }
}
