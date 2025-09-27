package me.gabl.fablefields.map.logic;

public enum MapLayer {

    GROUND,                 // Boden
    GFX_GROUND_OVERLAY, SURFACE, GFX_SURFACE_OVERLAY, FEATURE;     // Pflanzen


    public boolean isGfxLayer() {
        return this == GFX_SURFACE_OVERLAY || this == GFX_GROUND_OVERLAY;
    }

    public MapLayer getGfxLayer() {
        return gfx(this);
    }

    public MapLayer getLgxLayer() {
        return lgx(this);
    }

    public static MapLayer gfx(MapLayer layer) {
        if (layer == null) return null;
        return switch (layer) {
            case GROUND -> GFX_GROUND_OVERLAY;
            case SURFACE -> GFX_SURFACE_OVERLAY;
            case GFX_SURFACE_OVERLAY, GFX_GROUND_OVERLAY -> layer;
            default -> null;
        };
    }

    public static MapLayer lgx(MapLayer layer) {
        if (layer == null) return null;
        return switch (layer) {
            case GFX_GROUND_OVERLAY -> GROUND;
            case GFX_SURFACE_OVERLAY -> SURFACE;
            case GROUND, SURFACE, FEATURE -> layer;
            default -> null;
        };
    }
}
