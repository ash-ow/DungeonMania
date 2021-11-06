package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;

public interface IWeaponEntity {
    /**
     * Reduces enemy health based on players health and damage
     * @param enemy   enemy which is losing health
     * @param player  player which is attacking the enemy
     */
    public default void attack(IBattlingEntity enemy, CharacterEntity player) {
        enemy.loseHealth(player.getHealth(), this.getDamage());
    }

    public float getDamage();
}
