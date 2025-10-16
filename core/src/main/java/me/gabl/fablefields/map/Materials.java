package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.render.MapTileContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Materials {

    public static final Material SAND = new PlainMaterial("sand", 69);

    public static final Material DIRT = new PlainMaterial("dirt", 267);

    public static final Material GRASS = new RandomMaterial("grass", new int[]{4088, 4089, 4090, 4091, 4092, 4093, 4094, 4095});

    public static final Material WATER = new Material("water") {
        @Override
        public Cell.GfxPair generateCell(MapTileContext context) {
            if (context.layer != MapLayer.GROUND) {
                return null;
            }

            TiledMapTileLayer.Cell base = Cell.get(21 * 64 + 11 + (context.x % 4) - (context.y % 4) * 64);

            CellNeighborAnalysis analysis = CellNeighborAnalysis.get(context);
            if (Materials.SAND.equals(analysis.dominantNeighbor)) {
                return Cell.pair(base, Cell.get(2435, analysis));
            }

            if (Materials.DIRT.equals(analysis.dominantNeighbor)) {
                return Cell.pair(base, Cell.get(2499, analysis));
            }
            return Cell.pair(base);
        }
    };

    public static final Material SOIL = new PlainMaterial("soil", 818); //todo add different soil stages based on plant above - how to store??

    public static final Map<String, Material> FROM_ID;

    static {
        FROM_ID = new HashMap<>();
        for (Field field : Materials.class.getDeclaredFields()) {
            int mod = field.getModifiers();
            if (!(Modifier.isStatic(mod) && Modifier.isFinal(mod) && Modifier.isPublic(mod) && Material.class.isAssignableFrom(field.getType()))) {
                continue;
            }

            try {
                Material material = (Material) field.get(null);
                FROM_ID.put(material.id, material);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
