package me.gabl.fablefields.screen.util;

import com.badlogic.gdx.math.Rectangle;
import me.gabl.fablefields.game.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * not a classic quad tree
 * @param <T>
 */
public class QuadTree<T extends Entity> {

    private static final int MAX_ACTORS = 10;
    private static final int MAX_LEVEL = 6;
    private final int level;

    public final Rectangle bounds;

    public QuadTree(int level, Rectangle bounds) {
        this.level = level;
        this.bounds = bounds;
        this.nodes = new ArrayList<>(MAX_ACTORS);
    }

    private QuadTree<T>[] children; //0123 tl tr bl br, bl is min-min, lr is x
    private List<T> nodes;

    private void split() {
        children = new QuadTree[4];
        float width = bounds.width / 2;
        float height = bounds.height / 2;
        int newLevel = level + 1;
        children[0] = new QuadTree<>(newLevel, new Rectangle(bounds.x, bounds.y + height, width, height));
        children[1] = new QuadTree<>(newLevel, new Rectangle(bounds.x + width, bounds.y + height, width, height));
        children[2] = new QuadTree<>(newLevel, new Rectangle(bounds.x, bounds.y, width, height));
        children[3] = new QuadTree<>(newLevel, new Rectangle(bounds.x + width, bounds.y, width, height));
        insertNodes();
    }

    private void insertNodes() {
        for (T node : nodes) {
            insertNode(node);
        }
        this.nodes = null;
    }

    public void add(T node) {
        if (nodes != null) {
            if (nodes.size() >= MAX_ACTORS && level < MAX_LEVEL) {
                split();
                add(node);
                return;
            }
            nodes.add(node);
            return;
        }

        insertNode(node);
    }

    private void insertNode(T node) {
        for (QuadTree<T> child : children) {
            if (node.getCollisionBox().overlaps(child.bounds)) {
                child.add(node);
            }
        }
    }

    /**
     * @param consumer is called at least once per colliding entity, can be called more than once
     * @param bounds
     */
    public void getCollidingEntities(Consumer<T> consumer, Rectangle bounds) {
        if (nodes != null) {
            for (T node : nodes) {
                if (node.getCollisionBox().overlaps(bounds)) {
                    consumer.accept(node);
                }
            }
            return;
        }
        for (QuadTree<T> child : children) {
            child.getCollidingEntities(consumer, bounds);
        }
    }

    public boolean overlapsAny(Entity entity) {
        return overlapsAny(entity.getCollisionBox());
    }

    public boolean overlapsAny(Rectangle bounds) {
        if (nodes != null) {
            for (T node : nodes) {
                if (node.getCollisionBox().overlaps(bounds)) {
                    return true;
                }
            }
            return false;
        }
        for (QuadTree<T> child : children) {
            if (child.bounds.overlaps(bounds) && child.overlapsAny(bounds)) return true;
        }
        return false;
    }
}
