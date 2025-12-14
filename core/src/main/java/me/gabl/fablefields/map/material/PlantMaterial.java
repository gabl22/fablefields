package me.gabl.fablefields.map.material;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.render.ContextAddress;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;
import me.gabl.fablefields.screen.game.GameScreen;
import org.jetbrains.annotations.NotNull;

public class PlantMaterial extends Material {

    // times in seconds for that stage to complete.
    // stage in the end = growthStages.length;
    private final float[] growthStages;
    public final PlantDropChances dropChances;

    public PlantMaterial(@NotNull String id, float[] growthStages, PlantDropChances chances) {
        super(id);
        this.growthStages = growthStages;
        dropChances = chances;
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        throw new UnsupportedOperationException("Delegated to PlantTile.");
    }

    @Override
    public PlantTile createMapTile(ContextAddress address, GameScreen screen) {
        return new PlantTile(this, growthStages, address, screen.getChunk(), screen.eventBus);
    }



    @Override
    public String toString() {
        return Asset.LANGUAGE_SERVICE.get("material/" + id);
    }
}
