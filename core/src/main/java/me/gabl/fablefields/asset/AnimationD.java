package me.gabl.fablefields.asset;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationD extends Animation<TextureRegion> {

    public AnimationD(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
    }

    public AnimationD(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
    }

    public AnimationD(float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
    }

    public static AnimationD of(Animation<TextureRegion> animation) {
        return new AnimationD(animation.getFrameDuration(), new Array<>(animation.getKeyFrames()),
                animation.getPlayMode());
    }
}
