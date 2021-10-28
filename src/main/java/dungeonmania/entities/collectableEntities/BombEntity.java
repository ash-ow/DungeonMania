package dungeonmania.entities.collectableEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;

public class BombEntity extends Entity implements ICollectableEntity, ITicker {
    public BombEntity() {
        this(0, 0, 0);
    }
    
    public BombEntity(int x, int y, int layer) {
        super(x, y, layer, "bomb");
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }
    

    @Override
    public void used(CharacterEntity player){
        this.position = player.getPosition();
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        List<IEntity> adjacentEntities = entitiesControl.getAllAdjacentEntities(this.getPosition());
        if (isAdjacentSwitchActive(entitiesControl, adjacentEntities)) {
            for (IEntity entity : adjacentEntities) {
                explodeEntityIfPossible(entity, entitiesControl);
            }
            entitiesControl.removeEntity(this);
        }
    }

    private void explodeEntityIfPossible(IEntity entity, EntitiesControl entitiesControl) {
        if ( 
            !(entity instanceof CharacterEntity) && 
            !(entity instanceof ICollectableEntity)
        ) {
            entitiesControl.removeEntity(entity);
        }
    }

    private boolean isAdjacentSwitchActive(EntitiesControl entitiesControl, List<IEntity> adjacentEntities) {
        return entitiesControl
        .entitiesOfType(adjacentEntities, "switch")
        .stream()
        .map(SwitchEntity.class::cast)
        .anyMatch(SwitchEntity::isActive);
    }
}
