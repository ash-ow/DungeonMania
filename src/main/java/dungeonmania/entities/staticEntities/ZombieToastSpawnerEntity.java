package dungeonmania.entities.staticEntities;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IInteractableEntity;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;


public class ZombieToastSpawnerEntity extends Entity implements IBlocker, IInteractableEntity {
    public ZombieToastSpawnerEntity() {
        this(0, 0);
    }
    
    public ZombieToastSpawnerEntity(int x, int y) {
        super(x, y, EntityTypes.ZOMBIE_TOAST_SPAWNER);
    }

    public ZombieToastSpawnerEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, true);
    }

    
    public void interactWith(CharacterEntity player) throws InvalidActionException {
        List<IWeaponEntity> weaponsFound = player.getInventory().getItemsFromInventoryOfType(IWeaponEntity.class);
        if (weaponsFound.isEmpty()) {
            throw new InvalidActionException("Has No Weapons");
        }
        if (!isPlayerAdjacent(player)) {
            throw new InvalidActionException("Too far away");
        }
    }
    
    public boolean isPlayerAdjacent(CharacterEntity player) {
        return this.position.getCardinallyAdjacentPositions().contains(player.getPosition());
    }

    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        // cannot unblock zombie spawners
        return false;
    }

    @Override
    public boolean removeAfterInteraction() {
        return true;
    }
}
