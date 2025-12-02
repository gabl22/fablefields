package me.gabl.fablefields.game.entity;

public interface HitBox {

    static RectangularHitBox rect(float xMin, float xMax, float yMin, float yMax) {
        return new RectangularHitBox(xMin, xMax, yMin, yMax);
    }

    /*
     * in-actor width/height coordinate system
     */
    boolean hit(float x, float y);

    class RectangularHitBox implements HitBox {

        public float xMin, xMax, yMin, yMax;

        public RectangularHitBox(float xMin, float xMax, float yMin, float yMax) {
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
