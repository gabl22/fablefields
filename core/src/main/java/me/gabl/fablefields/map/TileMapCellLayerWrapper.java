package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Deprecated
@Getter
public class TileMapCellLayerWrapper {

    private final int height, width, tileHeight, tileWidth;
    private final TiledMapTileLayer layer;
    private int update = 0;

    public TileMapCellLayerWrapper(int width, int height, int tileWidth, int tileHeight) {
        this.layer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);
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
                    cell.update(this.update++, 0);
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

    private void linkCells() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.linkCell(x, y);
            }
        }
    }

    private void linkCell(int x, int y) {
        final MapCell<Tile> cell = this.getCell(x, y);
        final MapCell<Tile> up = this.getCell(x, y + 1);
        final MapCell<Tile> down = this.getCell(x, y - 1);
        final MapCell<Tile> left = this.getCell(x - 1, y);
        final MapCell<Tile> right = this.getCell(x + 1, y);

        if (cell != null) {
            cell.up = up;
            cell.down = down;
            cell.left = left;
            cell.right = right;
        }
        if (up != null) {
            up.down = cell;
        }
        if (down != null) {
            down.up = cell;
        }
        if (left != null) {
            left.right = cell;
        }
        if (right != null) {
            right.left = cell;
        }
    }
}
