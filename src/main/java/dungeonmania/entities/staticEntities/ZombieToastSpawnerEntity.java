package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;

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
    
}
