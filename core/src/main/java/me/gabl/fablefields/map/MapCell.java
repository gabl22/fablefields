package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MapCell<T extends Tile> extends TiledMapTileLayer.Cell {

    MapCell<T> up;
    MapCell<T> down;
    MapCell<T> left;
    MapCell<T> right;

    protected long lastUpdateIndex = Long.MIN_VALUE;

    public void update(long updateIndex, int reach) {
        if (updateIndex <= lastUpdateIndex) {
            return;
        }
        this.lastUpdateIndex = updateIndex;
        if (reach > 0) {
            reach--;
            up.update(updateIndex, reach);
            down.update(updateIndex, reach);
            left.update(updateIndex, reach);
            right.update(updateIndex, reach);
        }
        this.update();
    }

    private void update() {
        Material material = getTile().getMaterial();
        if (material != null && material.renderCell != null) {
            this.getTile().material.renderCell.accept(this);
        }
    }

    //todo fishy

    @Override
    @SuppressWarnings("unchecked")
    public T getTile() {
        return (T) super.getTile();
    }

    @Override
    public TiledMapTileLayer.Cell setTile(TiledMapTile tile) {
        return super.setTile(tile);
    }
}
