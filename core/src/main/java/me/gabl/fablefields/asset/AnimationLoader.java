package me.gabl.fablefields.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLoader {

    public Animation<TextureRegion> loadAnimation(String file, int frameHeight, int frameWidth, int frames) {
        Texture texture = new Texture(Gdx.files.internal(file));

        if (texture.getHeight() % frameHeight != 0) {
            throw new IllegalTextureException(file + ": Texture height must be a positive multiple of " + frameHeight);
        }
        if (texture.getWidth() % frameWidth != 0) {
            throw new IllegalTextureException(file + ": Texture width must be a positive multiple of " + frameWidth);
        }

        int xFrames = texture.getWidth() / frameWidth;

        TextureRegion[] textureRegions = new TextureRegion[frames];
        int x, y;
        for (int i = 0; i < frames; i++) {
            x = i % xFrames;
            y = i / xFrames;
            textureRegions[i] = new TextureRegion(texture, x, y, frameWidth, frameHeight);
        }
        return new Animation<>(1f / frames, textureRegions);
    }
}
