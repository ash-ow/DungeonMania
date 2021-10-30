package dungeonmania.entities.collectableEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.util.Direction;

public class BombEntity extends Entity implements ICollectableEntity, ITicker, IBlocker {
    boolean isArmed = false;

    public BombEntity() {
        this(0, 0, 0);
    }
    
    public BombEntity(int x, int y, int layer) {
        super(x, y, layer, "bomb");
    }
    
    public boolean isArmed() {
        return this.isArmed;
    }
    
    @Override
    public void used(CharacterEntity player){
        this.isArmed = true;
        player.removeEntityFromInventory(this);
    }

    @Override    
    public void interactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        if (!this.isArmed) {
            player.addEntityToInventory(this);
            entities.removeEntity(this);
        }
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        if (this.isArmed) {
            explode(entitiesControl);
        }
    }

    private void explode(EntitiesControl entitiesControl) {
        List<IEntity> adjacentEntities = entitiesControl.getAllAdjacentEntities(this.getPosition());
        adjacentEntities.addAll(entitiesControl.getAllEntitiesFromPosition(this.getPosition()));
        if (isAdjacentSwitchActive(entitiesControl, adjacentEntities)) {
            for (IEntity entity : adjacentEntities) {
                explodeNonCharacterEntity(entity, entitiesControl);
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

// region Blocking
    @Override
    public boolean isBlocking() {
        return isArmed;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        // Bombs cannot be unblocked once placed
        return false;
    }
// endregion
}
