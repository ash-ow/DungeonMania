package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class OneRingEntity extends Entity implements ICollectableEntity {
    public OneRingEntity() {
        this(0, 0, 0);
    }

    public OneRingEntity(int x, int y, int layer) {
        super(x, y, layer, "one_ring");
    }

    @Override
    public void used(CharacterEntity player) {
        // TODO
    }

    @Override
    public boolean isPlacedAfterUsing() {
        // TODO Auto-generated method stub
        return false;
    } 
}