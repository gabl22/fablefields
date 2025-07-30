package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class TileMapCellLayerWrapper {

    private final int height, width, tileHeight, tileWidth;
    private int update = 0;
    private TiledMapTileLayer layer;

    public TileMapCellLayerWrapper(int width, int height, int tileWidth, int tileHeight) {
        layer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public TileMapCellLayerWrapper(TiledMapTileLayer layer) {
        this.layer = layer;
        this.width = layer.getWidth();
        this.height = layer.getHeight();
        this.tileWidth = layer.getTileWidth();
        this.tileHeight = layer.getTileHeight();
    }

    public void update() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                MapCell<Tile> cell = this.getCell(x, y);
                if (cell != null) {
                    cell.update(update, 0);
                }
            }
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public MapCell<Tile> getCell(int x, int y) {
        return (MapCell<Tile>) this.layer.getCell(x, y);
    }

    public void initCells() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (this.getCell(x, y) == null) {
                    this.layer.setCell(x, y, new MapCell<>());
                }
            }
        }
        this.linkCells();
    }

    @Deprecated
    private void linkCells() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.linkCell(x, y);
            }
        }
    }

    private void linkCell(int x, int y) {
        final MapCell<Tile> cell = this.getCell(x, y);
        if (cell != null) {
            cell.up = this.getCell(x, y + 1);
            cell.down = this.getCell(x, y - 1);
            cell.left = this.getCell(x - 1, y);
            cell.right = this.getCell(x + 1, y);

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
        MapCell<Tile> up = this.getCell(x, y + 1);
        MapCell<Tile> down = this.getCell(x, y - 1);
        MapCell<Tile> left = this.getCell(x - 1, y);
        MapCell<Tile> right = this.getCell(x + 1, y);
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
}
