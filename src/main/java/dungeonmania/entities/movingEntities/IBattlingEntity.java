package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IContactingEntity;

public interface IBattlingEntity extends IContactingEntity {
    public float getHealth();
    public void setHealth(float health);
    public float getDamage();
    public void loseHealth(float enemyHealth, float enemyDamage);

    public default void battle(EntitiesControl entitiesControl, CharacterEntity player) {
        while (!(player.isInvisible()) && player.isAlive() && !checkEnemyDeath(entitiesControl)) {
            doBattle(player);
            player.setBattleCount(false);
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
        if (player.isInvincible()){
            this.setHealth(0);
        }
        else {
            float enemyInitialHealth = this.getHealth();
            this.loseHealth(player.getHealth(), player.getDamage());
            for (IBattlingEntity teammate : player.teammates) {
                this.loseHealth(teammate.getHealth(), teammate.getDamage());
            }
            player.loseHealth(enemyInitialHealth, this.getDamage());
        }
    }

    default boolean isAlive() {
        return this.getHealth() > 0;
    }

    @Override
    public default void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        battle(entities, player);
    }

}

    
