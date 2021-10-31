package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class OneRingEntity extends Entity implements ICollectableEntity {
    private final float dropChance = 0.5f;

    public OneRingEntity() {
        this(0, 0, 0);
    }

    public OneRingEntity(int x, int y, int layer) {
        super(x, y, layer, "one_ring");
    }

    public float getDropChance() {
        return dropChance;
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