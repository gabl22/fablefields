package me.gabl.fablefields.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import me.gabl.fablefields.game.entity.Entity;
import me.gabl.fablefields.screen.game.GameScreen;

public class ScreenUtil {

    private static final Vector3 position3 = new Vector3();
    private static final Vector2 position2 = new Vector2();

    public static Entity hit(float screenX, float screenY, GameScreen screen) {
        Vector2 position = getPosition(screenX, screenY, screen);
        Actor hit = screen.getStage().hit(position.x, position.y, true);
        if (hit instanceof Entity) return (Entity) hit;
        return null;
    }

    public static Vector2 getPosition(float screenX, float screenY, GameScreen screen) {
        position3.set(screenX, screenY, 0);
        // need (!) to unproject using viewport (zoom)
        screen.getViewport().unproject(position3);
        position2.set(position3.x, position3.y);
        return position2;
    }
}
