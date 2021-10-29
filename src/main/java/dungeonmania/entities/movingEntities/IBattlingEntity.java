package dungeonmania.entities.movingEntities;

import javax.swing.text.PlainDocument;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;

public interface IBattlingEntity extends IEntity {
    public float getHealth();
    public void setHealth(float health);
    public int getDamage();
    
    public void loseHealth(float enemyHealth, int enemyDamage);

    public default void Battle(EntitiesControl entitiesControl, CharacterEntity player) {
        while (!checkCharacterDeath(entitiesControl, player) && !checkEnemyDeath(entitiesControl)) {
            doBattle(player);
        }
    }

    default boolean checkCharacterDeath(EntitiesControl entitiesControl, CharacterEntity player) {
        if (player.getHealth() <= 0) {
            entitiesControl.removeEntity(player);
            return true;
        }
        return false;
    }

    default boolean checkEnemyDeath(EntitiesControl entitiesControl) {
        if (this.getHealth() <= 0) {
            entitiesControl.removeEntity(this);
            return true;
        }
        return false;
    }
    
    default void doBattle(CharacterEntity player) {
        System.out.println("The character is battling " + this.getId());
        float enemyInitialHealth = this.getHealth();
        this.loseHealth(player.getHealth(), player.getDamage());
        player.loseHealth(enemyInitialHealth, this.getDamage());
    }
}
