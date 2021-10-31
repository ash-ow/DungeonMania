package dungeonmania.entities.movingEntities;

import javax.swing.text.PlainDocument;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.util.RandomChance;

public interface IBattlingEntity extends IEntity {
    public float getHealth();
    public void setHealth(float health);
    public int getDamage();
    public void loseHealth(float enemyHealth, int enemyDamage);

    public default void Battle(EntitiesControl entitiesControl, CharacterEntity player) {
        while (player.isAlive() && !checkEnemyDeath(entitiesControl, player)) {
            doBattle(player);
        }
    }

    default boolean checkEnemyDeath(EntitiesControl entitiesControl, CharacterEntity player) {
        if (!this.isAlive()) {
            entitiesControl.removeEntity(this);
            return true;
        }
        return false;
    }
    
    default void doBattle(CharacterEntity player) {
        float enemyInitialHealth = this.getHealth();
        this.loseHealth(player.getHealth(), player.getDamage());
        player.loseHealth(enemyInitialHealth, this.getDamage());
    }

    default boolean isAlive() {
        return this.getHealth() > 0;
    }
}
