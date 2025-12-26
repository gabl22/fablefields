package me.gabl.fablefields.map.logic;

import me.gabl.fablefields.game.entity.Entity;

import java.util.*;
import java.util.function.Predicate;

public class PathFinder {

    static class Pos {

        int x, y, dist;

        Pos(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    /**
     * @return true if the entity is now safe
     */
    public static boolean moveToSafety(Entity entity, MapChunk chunk, int startX, int startY, int maxRadius,
            Predicate<MapTile> movement) {
        if (chunk.is(movement, entity.getX(), entity.getY()) && !chunk.collision.overlapsAny(entity.getCollisionBox())) {
            return true;
        }

        Queue<Pos> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();

        queue.add(new Pos(startX, startY, 0));
        visited.add(pack(startX, startY));

        final int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            Pos p = queue.poll();

            if (chunk.is(movement, p.x + 0.5f, p.y + 0.5f) && !chunk.collision.overlapsAny(entity.getCollisionBox(p.x + 0.5f, p.y + 0.5f))) {
                entity.setPosition(p.x + 0.5f, p.y + 0.5f);
                return true;
            }

            if (p.dist >= maxRadius) continue;

            for (int[] d : dirs) {
                int nx = p.x + d[0];
                int ny = p.y + d[1];

                long key = pack(nx, ny);
                if (visited.add(key)) {
                    queue.add(new Pos(nx, ny, p.dist + 1));
                }
            }
        }
        return false;
    }

    private static long pack(int x, int y) {
        return (((long) x) << 32) ^ (y & 0xffffffffL);
    }
}
