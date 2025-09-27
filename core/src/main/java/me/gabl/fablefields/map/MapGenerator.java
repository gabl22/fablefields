package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.articdive.jnoise.core.api.noisegen.NoiseGenerator;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapChunkLayers;
import me.gabl.fablefields.map.logic.MapTile;

public class MapGenerator {

    private static final int size = 1500; //TODO

    public static MapChunk getMapx() {
        NoiseGenerator noise = FastSimplexNoiseGenerator.newBuilder().setSeed(5).setVariant2D(Simplex2DVariant.CLASSIC)
            .build();

        MapTile[] ground = new MapTile[size * size];
        MapTile[] feature = new MapTile[size * size];
        MapTile[] surface = new MapTile[size * size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                ground[x * size + y] = getMapTile(noise.evaluateNoise(x / 150d, y / 150d));
                feature[x * size + y] = null;
                surface[x * size + y] = null;
            }
        }

        MapChunk chunk = new MapChunk(new MapChunkLayers<>(ground, feature, surface), size, size);
        return chunk;
    }

//    public static TiledMap getMap() {
//        TiledMap tmap = new TiledMap();
//        tmap.getTileSets().addTileSet(Asset.TILESET);
//        //        MapTileLayer mapTileLayer = new MapTileLayer(1000, 1000, 16, 16);
//        TileMapCellLayerWrapper mapTileLayer = new TileMapCellLayerWrapper(size, size, 16, 16);
//        NoiseGenerator noise = FastSimplexNoiseGenerator.newBuilder().setSeed(5).setVariant2D(Simplex2DVariant.CLASSIC)
//            .build();
//        mapTileLayer.initCells();
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                //                mapTileLayer.setCell(x, y, new MapCell<Tile>());
//                mapTileLayer.getCell(x, y).setTile(getTile(noise.evaluateNoise(x / 150d, y / 150d)));
//            }
//        }
//
//        TiledMapTileLayer sea = new TiledMapTileLayer(size, size, 16, 16);
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                sea.setCell(x, y, new TiledMapTileLayer.Cell());
//                sea.getCell(x, y).setTile(Asset.TILESET.getTile(21 * 64 + 11 + (x % 4) - (y % 4) * 64));
//            }
//        }
//        tmap.getLayers().add(sea);
//        tmap.getLayers().add(mapTileLayer.getLayer());
//        mapTileLayer.update();
//        return tmap;
//    }

//    private static TiledMapTile getTile(double noise) {
//        return Asset.TILESET.getTile((int) Math.floor((noise + 1) * 5) + 65);
//    }

    private static MapTile getMapTile(double noise) {
        Material material = ((Tile) Asset.TILESET.getTile((int) Math.floor((noise + 1) * 5) + 65)).getMaterial();
        return new MapTile(getMaterial(material));
    }

    private static MaterialX getMaterial(Material material) {
        if (material == null) return null;
        if (material.equals(Material.WATER)) return MaterialX.WATER;
        if (material.equals(Material.SAND)) return MaterialX.SAND;
        return MaterialX.DIRT;
    }
}
