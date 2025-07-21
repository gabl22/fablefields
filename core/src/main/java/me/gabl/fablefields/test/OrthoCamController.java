package me.gabl.fablefields.test;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
    final OrthographicCamera cam;
    final Vector3 current = new Vector3();
    final Vector3 lastTouch = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    public OrthoCamController(OrthographicCamera camera) {
        this.cam = camera;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        this.cam.unproject(this.current.set(x, y, 0));
        if (this.lastTouch.x != -1) {
            this.cam.unproject(this.delta.set(this.lastTouch));
            this.delta.sub(this.current);
            this.cam.position.add(this.delta);
        }
        this.lastTouch.set(x, y, 0);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.lastTouch.x = -1;
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return true;
    }
}
