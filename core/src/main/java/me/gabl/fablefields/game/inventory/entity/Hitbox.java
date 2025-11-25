package me.gabl.fablefields.game.inventory.entity;

public interface Hitbox {
    static RectangularHitbox rect(float xMin, float xMax, float yMin, float yMax) {
        return new RectangularHitbox(xMin, xMax, yMin, yMax);
    }

    /*
     * in-actor width/height coordinate system
     */
    boolean hit(float x, float y);

    class RectangularHitbox implements Hitbox {

        public float xMin, xMax, yMin, yMax;

        public RectangularHitbox(float xMin, float xMax, float yMin, float yMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
        }

        @Override
        public boolean hit(float x, float y) {
            return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
        }
    }
}
