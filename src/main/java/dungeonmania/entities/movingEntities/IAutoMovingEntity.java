package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;


public interface IAutoMovingEntity extends IMovingEntity{
    /**
     * Move the automoving entity
     * @param entitiesControl  list of all entities
     * @param player           the player which automoving entities are moving to or from
     */
    public void move(EntitiesControl entitiesControl, CharacterEntity player); 

    /**
     * Determines the move behaviour of the automovingentity
     * @param newBehaviour     input of behaviour
     */
    public void setMoveBehaviour(IMovingBehaviour newBehaviour);
}
