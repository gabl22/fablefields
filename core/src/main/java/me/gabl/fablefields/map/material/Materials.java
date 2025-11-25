package me.gabl.fablefields.map.material;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.CellNeighborAnalysis;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;
import me.gabl.fablefields.map.render.ZIndex;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Materials {

    public static final Material SAND = new PlainMaterial("sand", 69);

    public static final Material DIRT = new PlainMaterial("dirt", 267);

    public static final Material GRASS = new RandomMaterial("grass", new int[]{4088, 4089, 4090, 4091, 4092, 4093, 4094, 4095});

    // todo outsource code
    public static final Material WATER = new Material("water") {
        @Override
        public RenderInstruction[] render(MapTileContext context) {
            if (context.layer != MapLayer.GROUND) {
                return null;
            }

            TiledMapTileLayer.Cell base = Cell.get(21 * 64 + 11 + (context.x % 4) - (context.y % 4) * 64);
            TiledMapTileLayer.Cell level = null;

            CellNeighborAnalysis analysis = CellNeighborAnalysis.get(context);
            if (Materials.SAND.equals(analysis.dominantNeighbor)) {
                level = Cell.get(2435, analysis);
            }
            if (Materials.DIRT.equals(analysis.dominantNeighbor)) {
                level = Cell.get(2499, analysis);
            }
            if (level != null) {
                return RenderInstruction.of(context.getAddress(), base, ZIndex.BASE_WATER, level, ZIndex.LEVEL_COUNTRY_OVERLAY);
            }
            return RenderInstruction.of(context.getAddress(), base, ZIndex.BASE_WATER);
        }
    };

    public static final Material SOIL = new SingleLayerMaterial("soil") {
        @Override
        public int renderTileSetId(MapTileContext context) {
            if (context.inLayer(MapLayer.FEATURE).tile instanceof PlantTile tile) {
                return switch (tile.getGrowthStage()) {
                    case 0 -> 754;
                    case 1 -> tile.needsWater() ? 818 : 882;
                    case 2 -> 1010;
                    case 3 -> 1138;
                    default -> 818;
                };
            }
            return 754;
        }
    };

    public static final PlantMaterial CARROT = new PlantMaterial("carrot", new float[]{10f, 10f, 10f}, 0);
    public static final PlantMaterial CAULIFLOWER = new PlantMaterial("cauliflower", new float[]{10f, 10f, 10f}, 1);
    public static final PlantMaterial PUMPKIN = new PlantMaterial("pumpkin", new float[]{10f, 10f, 10f}, 2);
    public static final PlantMaterial SUNFLOWER = new PlantMaterial("sunflower", new float[]{10f, 10f, 10f}, 3);
    public static final PlantMaterial RADISH = new PlantMaterial("radish", new float[]{10f, 10f, 10f}, 4);
    public static final PlantMaterial PARSNIP = new PlantMaterial("parsnip", new float[]{10f, 10f, 10f}, 5);
    public static final PlantMaterial CABBAGE = new PlantMaterial("cabbage", new float[]{10f, 10f, 10f}, 6);
    public static final PlantMaterial BEETROOT = new PlantMaterial("beetroot", new float[]{10f, 10f, 10f}, 7);
    public static final PlantMaterial LETTUCE = new PlantMaterial("lettuce", new float[]{10f, 10f, 10f}, 8);

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
