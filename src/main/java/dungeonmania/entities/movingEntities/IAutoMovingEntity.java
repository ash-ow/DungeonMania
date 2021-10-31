package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;

public interface IAutoMovingEntity extends IMovingEntity{
    public void move(EntitiesControl entitiesControl, CharacterEntity player);

    public default void runAway(EntitiesControl entitiesControl, CharacterEntity player) {
        
    }
}
