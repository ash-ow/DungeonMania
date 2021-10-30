package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.collectableEntities.TheOneRingEntity;
import java.util.Random;


public interface IBattlingEntity extends IInteractingEntity {
    public float getHealth();
    public void setHealth(float health);
    public int getDamage();
    public void loseHealth(float enemyHealth, int enemyDamage);
    //public boolean dropRing

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
            player.loseHealth(enemyInitialHealth, this.getDamage());
        }
    }

    default boolean isAlive() {
        return this.getHealth() > 0;
    }

/* TO DO: Implement One Ring 
//The One Ring Random Drop 
    public boolean getDropRing() {
        return this.dropRing;
    }

    public void setDropRing(boolean one) {
        this.dropRing = one;
    }

    public TheOneRingEntity dropOneRing() {
        return new TheOneRingEntity(this.position.getX(),this.position.getY(),this.position.getLayer());
    }
    
    public default void enemyDrop(EntitiesControl entitiesControl, CharacterEntity player) {
        if (entitiesControl.getOneRingDropStatus()) return;
        if (this.checkEnemyDeath(false);
        {
            Random random = new Random();
            int dropItem = random.nextInt(20);
            //5 % chance enemy drops one ring
            if (dropItem == 11) {
                entitiesControl.addEntities(this.dropOneRing());
                entitiesControl.setOneRingDropStatus(true);
                this.setDropRing(true);
            }
        }
    }
*/


    @Override
    public default void interactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        battle(entities, player);
    }

}

    
