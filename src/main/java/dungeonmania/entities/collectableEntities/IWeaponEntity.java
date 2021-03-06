package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;

public interface IWeaponEntity extends ICollectable {
    /**
     * Reduces enemy health based on players health and damage
     * @param enemy   enemy which is losing health
     * @param player  player which is attacking the enemy
     */
    public default float attack(IBattlingEntity enemy, CharacterEntity player) {
        return enemy.loseHealth(player.getHealth(), this.getDamage());
    }

    public float getDamage();
}
