package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class InvincibilityPotionEntity extends Entity implements ICollectableEntity {

    public InvincibilityPotionEntity() {
        this(0, 0, 0);
    }
    

    public InvincibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.INVINCIBILITY_POTION);
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
