package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import me.gabl.fablefields.asset.Asset;

public class Cell {


    @Deprecated //TODO
    public static TiledMapTileLayer.Cell get(int baseTileSetId, CellNeighborAnalysis analysis) {
        TiledMapTileLayer.Cell cell = get(baseTileSetId + analysis.neighborCase);
        cell.setRotation(analysis.rotation);
        return cell;
    }

    public static TiledMapTileLayer.Cell get(String name) {
        return get(Asset.REGISTRY.getTile("tile/" + name));
    }

    @Deprecated
    public static TiledMapTileLayer.Cell get(int tileSetId) {
        return get(Asset.TILESET.getTile(tileSetId));
    }

    public static TiledMapTileLayer.Cell get(TiledMapTile tile) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tile);
        return cell;
    }

    public static TiledMapTileLayer.Cell get(TiledMapTile tile, int rotation, boolean flipHorizontally,
            boolean flipVertically) {
        TiledMapTileLayer.Cell cell = get(tile);
        cell.setRotation(rotation);
        cell.setFlipHorizontally(flipHorizontally);
        cell.setFlipVertically(flipVertically);
        return cell;
    }
}
