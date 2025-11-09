package me.gabl.fablefields.map.logic;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MapChunkLayers<T> {

    public final T ground;
    public final T surface;
    public final T feature;

    public MapChunkLayers(Supplier<T> supplier) {
        this(supplier.get(), supplier.get(), supplier.get());
    }

    public MapChunkLayers(T ground, T surface, T feature) {
        this.ground = ground;
        this.surface = surface;
        this.feature = feature;

        assert ground != null;
        assert surface != null;
        assert feature != null;
    }

    public Stream<T> stream() {
        return Stream.of(ground, surface, feature);
    }

    public <S> MapChunkLayers<S> map(Function<? super T, ? extends S> function) {
        return new MapChunkLayers<>(function.apply(ground), function.apply(surface),
             function.apply(feature)
        );
    }

    @NotNull
    public T get(MapLayer layer) {
        if (layer == null) {
            throw new IllegalArgumentException("layer is null");
        }

        return switch (layer) {
            case GROUND -> ground;
            case SURFACE -> surface;
            case FEATURE -> feature;
        };
    }

    public void forEach(Consumer<? super T> consumer) {
        consumer.accept(ground);
        consumer.accept(surface);
        consumer.accept(feature);
    }
}
