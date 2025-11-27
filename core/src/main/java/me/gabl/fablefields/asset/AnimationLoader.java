package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationLoader extends SynchronousAssetLoader<AnimationD, AnimationLoader.Parameters> {

    public AnimationLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public static Parameters params(int frameHeight, int frameWidth, int frames, float frameDuration,
            Animation.PlayMode playMode) {
        return new Parameters(frameHeight, frameWidth, frames, frameDuration, playMode);
    }

    @Override
    public AnimationD load(AssetManager manager, String fileName, FileHandle file, Parameters params) {
        Texture texture = new Texture(file);

        if (texture.getHeight() % params.frameHeight != 0) {
            throw new IllegalTextureException(file + ": Texture height must be a positive multiple of " + params.frameHeight);
        }
        if (texture.getWidth() % params.frameWidth != 0) {
            throw new IllegalTextureException(file + ": Texture width must be a positive multiple of " + params.frameWidth);
        }

        int xFrames = texture.getWidth() / params.frameWidth;

        TextureRegion[] textureRegions = new TextureRegion[params.frames];
        int x, y;
        for (int i = 0; i < params.frames; i++) {
            x = i % xFrames;
            y = i / xFrames;
            textureRegions[i] = new TextureRegion(texture, x * params.frameWidth, y * params.frameHeight,
                    params.frameWidth, params.frameHeight);
        }
        Animation<TextureRegion> animation = new Animation<>(params.frameDuration, textureRegions);
        animation.setPlayMode(params.playMode);
        return AnimationD.of(animation);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameters parameter) {
        return null;
    }

    public static class Parameters extends AssetLoaderParameters<AnimationD> {

        public final int frameHeight;
        public final int frameWidth;
        public final int frames;
        public final float frameDuration;
        public final Animation.PlayMode playMode;

        private Parameters(int frameHeight, int frameWidth, int frames, float frameDuration,
                Animation.PlayMode playMode) {
            this.frameHeight = frameHeight;
            this.frameWidth = frameWidth;
            this.frames = frames;
            this.frameDuration = frameDuration;
            this.playMode = playMode;
        }
    }
}
