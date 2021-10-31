package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;

public interface IWeaponEntity {
    public default void attack(IBattlingEntity enemy, CharacterEntity player) {
        enemy.loseHealth(player.getHealth(), player.getDamage());
    }
}
