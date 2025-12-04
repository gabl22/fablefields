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

    public static final Material SAND = new PlainMaterial("sand");

    public static final Material DIRT = new RandomMaterial("dirt", 6);

    public static final Material GRASS = new RandomMaterial("grass", 7);

    // todo outsource code
    public static final Material WATER = new Material("water") {
        @Override
        public RenderInstruction[] render(MapTileContext context) {
            if (context.layer != MapLayer.GROUND) {
                return null;
            }

            TiledMapTileLayer.Cell base = Cell.get("water/pattern/" + (context.x % 4) + "." + (context.y % 4));
//            TiledMapTileLayer.Cell base = Cell.get(21 * 64 + 11 + (context.x % 4) - (context.y % 4) * 64);
            TiledMapTileLayer.Cell level = null;

            CellNeighborAnalysis analysis = CellNeighborAnalysis.get(context);
            if (Materials.SAND.equals(analysis.dominantNeighbor)) {
                level = Cell.get(2435, analysis);
            }
            if (level != null) {
                return RenderInstruction.of(context.getAddress(), base, ZIndex.BASE_WATER, level,
                        ZIndex.LEVEL_COUNTRY_OVERLAY);
            }
            return RenderInstruction.of(context.getAddress(), base, ZIndex.BASE_WATER);
        }
    };

    public static final Material SOIL = new SingleLayerMaterial("soil") {
        @Override
        public String name(MapTileContext context) {
            int stage = 0;
            if (context.inLayer(MapLayer.FEATURE).tile instanceof PlantTile tile) {
                stage = tile.needsWater() ? tile.getGrowthStage() - 1 : tile.getGrowthStage();
            }
            return "soil/stage/" + stage;
        }
    };

    public static final PlantMaterial CARROT = new PlantMaterial("carrot", new float[]{10f, 10f, 10f});
    public static final PlantMaterial CAULIFLOWER = new PlantMaterial("cauliflower", new float[]{10f, 10f, 10f});
    public static final PlantMaterial PUMPKIN = new PlantMaterial("pumpkin", new float[]{10f, 10f, 10f});
    public static final PlantMaterial SUNFLOWER = new PlantMaterial("sunflower", new float[]{10f, 10f, 10f});
    public static final PlantMaterial RADISH = new PlantMaterial("radish", new float[]{10f, 10f, 10f});
    public static final PlantMaterial PARSNIP = new PlantMaterial("parsnip", new float[]{10f, 10f, 10f});
    public static final PlantMaterial POTATO = new PlantMaterial("potato", new float[]{10f, 10f, 10f});
    public static final PlantMaterial CABBAGE = new PlantMaterial("cabbage", new float[]{10f, 10f, 10f});
    public static final PlantMaterial BEETROOT = new PlantMaterial("beetroot", new float[]{10f, 10f, 10f});
    public static final PlantMaterial WHEAT = new PlantMaterial("wheat", new float[]{10f, 10f, 10f});
    public static final PlantMaterial LETTUCE = new PlantMaterial("lettuce", new float[]{10f, 10f, 10f});

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
