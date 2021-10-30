package dungeonmania.entities.movingEntities;

import javax.swing.text.PlainDocument;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;

public interface IBattlingEntity extends IEntity {
    public float getHealth();
    public void setHealth(float health);
    public float getDamage();
    public void loseHealth(float enemyHealth, float enemyDamage);

    public default void Battle(EntitiesControl entitiesControl, CharacterEntity player) {
        while (player.isAlive() && !checkEnemyDeath(entitiesControl)) {
            doBattle(player);
        }
    }

    default boolean checkEnemyDeath(EntitiesControl entitiesControl) {
        if (!this.isAlive()) {
            entitiesControl.removeEntity(this);
            return true;
        }
        return false;
    }
    
    default void doBattle(CharacterEntity player) {
        float enemyInitialHealth = this.getHealth();
        this.loseHealth(player.getHealth(), player.getDamage());
        for (IBattlingEntity teammate : player.teammates) {
            this.loseHealth(teammate.getHealth(), teammate.getDamage());
        }
        player.loseHealth(enemyInitialHealth, this.getDamage());
    }

    default boolean isAlive() {
        return this.getHealth() > 0;
    }
}
