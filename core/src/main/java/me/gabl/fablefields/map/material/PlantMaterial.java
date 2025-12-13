package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.render.ContextAddress;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;
import me.gabl.fablefields.screen.game.GameScreen;

public class PlantMaterial extends Material {

    // times in seconds for that stage to complete.
    // stage in the end = growthStages.length;
    private final float[] growthStages;

    public PlantMaterial(String id, float[] growthStages) {
        super(id);
        this.growthStages = growthStages;
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        throw new UnsupportedOperationException("Delegated to PlantTile.");
    }

    @Override
    public PlantTile createMapTile(ContextAddress address, GameScreen screen) {
        return new PlantTile(this, growthStages, address, screen.getChunk(), screen.eventBus);
    }
}
