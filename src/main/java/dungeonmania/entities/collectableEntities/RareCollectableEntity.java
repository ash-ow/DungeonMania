package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class RareCollectableEntity extends Entity implements ICollectableEntity {
    // TODO this class is not implemented, current state is used to pass test
    public RareCollectableEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ONE_RING);
    }

    @Override
    public void used(CharacterEntity player) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isPlacedAfterUsing() {
        // TODO implement
        return false;
    }    
}
