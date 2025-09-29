package me.gabl.fablefields.screen.util;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

public class ScreenMultiplexer implements Screen {

    private final SnapshotArray<Screen> processors = new SnapshotArray<>();

    public ScreenMultiplexer() {

    }

    public ScreenMultiplexer(Screen... processor) {
        processors.addAll(processor);
    }

    public int size() {
        return processors.size;
    }

    public void clear() {
        processors.clear();
    }

    public SnapshotArray<Screen> getProcessors() {
        return processors;
    }

    public void setProcessors(Screen... processors) {
        this.processors.clear();
        this.processors.addAll(processors);
    }

    public void setProcessors(Array<Screen> processors) {
        this.processors.clear();
        this.processors.addAll(processors);
    }

    public void addProcessor(int index, Screen processor) {
        if (processor == null)
            throw new NullPointerException("processor cannot be null");
        processors.insert(index, processor);
    }

    public void removeProcessor(int index) {
        processors.removeIndex(index);
    }

    public void addProcessor(Screen processor) {
        if (processor == null)
            throw new NullPointerException("processor cannot be null");
        processors.add(processor);
    }

    public void removeProcessor(Screen processor) {
        processors.removeValue(processor, true);
    }

    @Override
    public void show() {
        processors.forEach(Screen::show);
    }

    @Override
    public void render(float delta) {
        processors.forEach(screen -> screen.render(delta));
    }

    @Override
    public void resize(int width, int height) {
        processors.forEach(screen -> screen.resize(width, height));
    }

    @Override
    public void pause() {
        processors.forEach(Screen::pause);
    }

    @Override
    public void resume() {
        processors.forEach(Screen::resume);
    }

    @Override
    public void hide() {
        processors.forEach(Screen::hide);
    }

    @Override
    public void dispose() {
        processors.forEach(Screen::dispose);
    }
}
