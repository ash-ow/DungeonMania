package dungeonmania.entities.staticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;

public class ZombieToastSpawnerEntity extends Entity implements IInteractingEntity{
    // TODO make this IBlocker

    public ZombieToastSpawnerEntity() {
        this(0, 0, 0);
    }
    
    public ZombieToastSpawnerEntity(int x, int y, int layer) {
        super(x, y, layer, "zombie_toast_spawner");
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, true);
    }

    @Override
    public void interactWith(CharacterEntity player) throws InvalidActionException {
        List<IWeaponEntity> weapons;  
    }
}
