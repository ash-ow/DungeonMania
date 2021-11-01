package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class HealthPotionEntity extends CollectableEntity implements IUseableEntity {
    public HealthPotionEntity() {
        this(0, 0, 0);
    }
    
    public HealthPotionEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.HEALTH_POTION);
    }

    @Override
    public void used(CharacterEntity player) {
        player.setHealth(100);
        player.removeEntityFromInventory(this);
    }   
}