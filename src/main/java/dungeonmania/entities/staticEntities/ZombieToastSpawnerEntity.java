package dungeonmania.entities.staticEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastSpawnerEntity extends Entity implements IBlocker{
    // TODO make this IBlocker

    public ZombieToastSpawnerEntity() {
        this(0, 0, 0);
    }
    
    public ZombieToastSpawnerEntity(int x, int y, int layer) {
        super(x, y, layer, "zombie_toast_spawner");
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, true);
    }

    public void interactWith(EntitiesControl entitiesControl, CharacterEntity player) throws InvalidActionException {
        List <IEntity> inveEntities = player.getInventory().stream().map(IEntity.class::cast).collect(Collectors.toList());
        List<IWeaponEntity> weaponsFound = EntitiesControl.getEntitiesOfType(inveEntities, IWeaponEntity.class);
        if (weaponsFound.isEmpty()) {
            throw new InvalidActionException("Has No Weapons");
        }
        if (!isPlayerAdjacent(player)) {
            throw new InvalidActionException("Too far away");
        }
        entitiesControl.removeEntity(this);
    }
    
    public boolean isPlayerAdjacent(CharacterEntity player) {
        return this.position.getCardinallyAdjacentPositions().contains(player.getPosition());
    }

    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        // cannot unblock zombie spawners
        return false;
    }
}
