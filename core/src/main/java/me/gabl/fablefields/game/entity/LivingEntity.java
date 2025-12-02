package me.gabl.fablefields.game.entity;

import me.gabl.fablefields.map.logic.MapChunk;

public class LivingEntity extends AnimatedEntity {

    public float maxHealth;
    public float health;

    public LivingEntity(MapChunk chunk) {
        super(chunk);
    }

    public final void inflictDamage(float damage) {
        setHealth(health - damage);
        onDamage(damage);
        if (isDead()) {
            onDeath();
        }
    }

    private void setHealth(float health) {
        this.health = Math.min(Math.max(0, health), maxHealth);
    }

    public void onDamage(float damage) {
        //todo event bus call & damage inflictor
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void onDeath() {
        this.remove();
    }
}
