package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;


public interface IAutoMovingEntity extends IMovingEntity{

    public void move(EntitiesControl entitiesControl, CharacterEntity player); 

    public void setMoveBehvaiour(IMovingBehaviour newBehaviour);
}
