package me.gabl.fablefields.map;

import lombok.Getter;

@Deprecated
public class MapTileLayer extends com.badlogic.gdx.maps.MapLayer {

    @Getter
    private final int width;
    @Getter
    private final int height;

    @Getter
    private final int tileWidth;
    @Getter
    private final int tileHeight;

    private final MapCell<Tile>[] cells;

    public MapTileLayer(int width, int height, int tileWidth, int tileHeight) {
        super();
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.cells = new MapCell[width * height];
    }

    public MapCell<Tile> getCell(int i) {
        return cells[i];
    }

    public MapCell<Tile> getCell(int x, int y) {
        // call to check whether x, y in bounds of height, width deemed unnecessary
        return cells[x + y * this.width];
    }

    public void initCells() {
        for (int i = 0; i < this.cells.length; i++) {
            if (this.cells[i] == null) {
                this.cells[i] = new MapCell<Tile>();
            }
        }
        this.linkCells();
    }

    @Deprecated
    private void linkCells() {
        for (int i = 0; i < this.cells.length; i++) {
            this.linkCell(i);
        }
    }

    private void linkCell(int i) {
        final MapCell<Tile> cell = this.cells[i];
        if (cell != null) {
            cell.up = this.getCellBound(i + width);
            cell.down = this.getCellBound(i - width);
            cell.left = this.getCellBound(i - 1);
            cell.right = this.getCellBound(i + 1);

            // link-back
            if (cell.up != null) {
                cell.up.down = cell;
            }
            if (cell.down != null) {
                cell.down.up = cell;
            }
            if (cell.left != null) {
                cell.left.right = cell;
            }
            if (cell.right != null) {
                cell.right.left = cell;
            }
            return;
        }
        // cell == null
        MapCell<Tile> up = this.getCellBound(i + width);
        MapCell<Tile> down = this.getCellBound(i - width);
        MapCell<Tile> left = this.getCellBound(i - 1);
        MapCell<Tile> right = this.getCellBound(i + 1);
        if (up != null) {
            up.down = null;
        }
        if (down != null) {
            down.up = null;
        }
        if (left != null) {
            left.right = null;
        }
        if (right != null) {
            right.left = null;
        }
    }

    private MapCell<Tile> getCellBound(int i) {
        if (i > 0 && i < this.cells.length) {
            return this.cells[i];
        }
        return null;
    }
}
