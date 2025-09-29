package me.gabl.fablefields.test;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class OrthoCamController extends InputAdapter {
    final OrthographicCamera cam;
    final Vector3 current = new Vector3();
    final Vector3 lastTouch = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();
    final Actor center;
    boolean followCenter = true;

    public OrthoCamController(OrthographicCamera camera, Actor center) {
        this.cam = camera;
        this.cam.zoom = 2f;
        this.center = center;
    }

    public void update() {
        if (this.followCenter) {
            this.cam.position.set(this.center.getX(), this.center.getY(), 0);
        }
        this.cam.update();
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        this.followCenter = false;
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
        if (this.cam.zoom < 0.3) {
            this.cam.zoom += amountY / 128;
        } else if (this.cam.zoom < 1) {
            this.cam.zoom += amountY / 16;
        } else {
            this.cam.zoom += amountY / 2;
        }

        if (this.cam.zoom <= 0.05) {
            this.cam.zoom = 0.05f;
        }
        if (this.cam.zoom > 10) {
            this.cam.zoom = 10f;
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.C) {
            this.cam.zoom = 0.5f;
            this.cam.position.set(this.center.getX(), this.center.getY(), 0);
            this.followCenter = true;
            return true;
        }
        //debug
        if (keycode == Input.Keys.PAUSE) {
            return true;
        }
        return false;
    }
}
