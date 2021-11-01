package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class OneRingEntity extends CollectableEntity {
    private final float dropChance = 0.5f;

    public OneRingEntity() {
        this(0, 0, 0);
    }

    public OneRingEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ONE_RING);
    }

    public float getDropChance() {
        return dropChance;
    }

    @Override
    public void used(CharacterEntity player) {
        player.setHealth(100);
        player.removeEntityFromInventory(this);
    }
}