package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class HealthPotionEntity extends Entity implements ICollectableEntity {
    public HealthPotionEntity() {
        this(0, 0, 0);
    }
    
    public HealthPotionEntity(int x, int y, int layer) {
        super(x, y, layer, "health_potion");
    }

    @Override
    public void used(CharacterEntity player) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    }   
}