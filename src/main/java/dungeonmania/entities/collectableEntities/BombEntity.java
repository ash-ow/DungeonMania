package dungeonmania.entities.collectableEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;

public class BombEntity extends Entity implements ICollectableEntity, ITicker {
    boolean isArmed = false;

    public BombEntity() {
        this(0, 0, 0);
    }
    
    public BombEntity(int x, int y, int layer) {
        super(x, y, layer, "bomb");
    }
    
    @Override
    public boolean isPassable() {
        return !isArmed;
    }
    
    public boolean isArmed() {
        return this.isArmed;
    }
    
    @Override
    public void used(CharacterEntity player){
        player.getInventory().removeEntity(this);
        this.position = player.getPosition();
        this.isArmed = true;
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        if (this.isArmed) {
            List<IEntity> adjacentEntities = entitiesControl.getAllAdjacentEntities(this.getPosition());
            adjacentEntities.addAll(entitiesControl.getAllEntitiesFromPosition(this.getPosition()));
            if (isAdjacentSwitchActive(entitiesControl, adjacentEntities)) {
                for (IEntity entity : adjacentEntities) {
                    explodeNonCharacterEntity(entity, entitiesControl);
                }
            }
        }
    }

    private void explodeNonCharacterEntity(IEntity entity, EntitiesControl entitiesControl) {
        if (!(entity instanceof CharacterEntity)) {
            entitiesControl.removeEntity(entity);
        }
    }

    private boolean isAdjacentSwitchActive(EntitiesControl entitiesControl, List<IEntity> adjacentEntities) {
        return entitiesControl
        .getEntitiesOfType(adjacentEntities, "switch")
        .stream()
        .map(SwitchEntity.class::cast)
        .anyMatch(SwitchEntity::isActive);
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return true;
    }
}
