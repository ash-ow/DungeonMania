package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;
import dungeonmania.response.models.EntityResponse;

public class ZombieToastSpawnerEntity extends Entity {
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
}
