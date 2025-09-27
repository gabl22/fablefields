package me.gabl.fablefields.map.logic;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MapChunkLayers<T> {

    final T ground;
    final T gfx_ground;
    final T surface;
    final T gfx_surface;
    final T feature;

    final boolean containsRenderLayers;

    public MapChunkLayers(Supplier<T> supplier) {
        this(supplier.get(), supplier.get(), supplier.get(), supplier.get(), supplier.get());
    }

    public MapChunkLayers(T ground, T gfx_ground, T surface, T gfx_surface, T feature) {
        this.ground = ground;
        this.gfx_ground = gfx_ground;
        this.surface = surface;
        this.gfx_surface = gfx_surface;
        this.feature = feature;

        assert ground != null;
        assert gfx_ground != null;
        assert surface != null;
        assert gfx_surface != null;
        assert feature != null;

        containsRenderLayers = true;
    }

    public MapChunkLayers(T ground, T surface, T feature) {
        this.ground = ground;
        this.surface = surface;
        this.feature = feature;
        this.gfx_ground = null;
        this.gfx_surface = null;

        assert ground != null;
        assert surface != null;
        assert feature != null;

        containsRenderLayers = false;
    }

    public Stream<T> stream() {
        if (containsRenderLayers) {
            return Stream.of(ground, gfx_ground, surface, gfx_surface, feature);
        }
        return Stream.of(ground, surface, feature);
    }

    public <S> MapChunkLayers<S> map(Function<? super T, ? extends S> function) {
        if (!containsRenderLayers) {
            return new MapChunkLayers<>(function.apply(ground), function.apply(surface), function.apply(feature));
        }
        return new MapChunkLayers<>(function.apply(ground), function.apply(gfx_ground), function.apply(surface),
            function.apply(gfx_surface), function.apply(feature)
        );
    }

    @NotNull
    public T get(MapLayer layer) {
        if (layer == null) {
            throw new IllegalArgumentException("layer is null");
        }

        if (containsRenderLayers) {
            return getRenderLayer(layer);
        }
        return switch (layer) {
            case GROUND -> ground;
            case SURFACE -> surface;
            case FEATURE -> feature;
            default -> throw new IllegalArgumentException("illegal layer");
        };
    }

    @NotNull
    private T getRenderLayer(MapLayer layer) {
        if (layer == null) {
            throw new IllegalArgumentException("layer is null");
        }

        if (!containsRenderLayers && (layer == MapLayer.GFX_GROUND_OVERLAY || layer == MapLayer.GFX_SURFACE_OVERLAY)) {
            throw new IllegalArgumentException("illegal layer: does not contain renderlayers");
        }

        return switch (layer) {
            case GROUND -> ground;
            case GFX_GROUND_OVERLAY -> gfx_ground;
            case SURFACE -> surface;
            case GFX_SURFACE_OVERLAY -> gfx_surface;
            case FEATURE -> feature;
        };
    }
}
