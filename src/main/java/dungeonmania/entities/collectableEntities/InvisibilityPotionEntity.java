package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class InvisibilityPotionEntity extends Entity implements ICollectableEntity {
    public InvisibilityPotionEntity() {
        this(0, 0, 0);
    }
    
    public InvisibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.INVISIBILITY_POTION);
    }

    @Override
    public void used(CharacterEntity player) {
        // TODO implement
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    } 
}