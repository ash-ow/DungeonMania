package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Direction;

public interface IAutoMovingEntity extends IMovingEntity{
    public void move(EntitiesControl entitiesControl, CharacterEntity player);
}
