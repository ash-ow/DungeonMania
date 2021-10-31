package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.util.RandomChance;

public interface IDroppingEntities extends IBattlingEntity {

    public default float getDropProbability() {
        return this.getDropProbability();
    }
    
    public void setDropProbability(float probability);

    @Override
    default boolean checkEnemyDeath(EntitiesControl entitiesControl, CharacterEntity player) {
        if (!this.isAlive()) {
            if (RandomChance.getRandomBoolean((float) this.getDropProbability())){
                player.addEntityToInventory(new OneRingEntity());
            }
            entitiesControl.removeEntity(this);
            return true;
        }
        return false;
    }
}
