package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class ArrowsEntity extends Entity implements ICollectableEntity {
    public ArrowsEntity() {
        this(0, 0, 0);
    }
    
    public ArrowsEntity(int x, int y, int layer) {
        super(x, y, layer, "arrow");
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }

    public void used(CharacterEntity player){
        // TODO: Remove from inventory
    }
}
