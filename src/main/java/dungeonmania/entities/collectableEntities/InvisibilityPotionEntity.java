package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class InvisibilityPotionEntity extends CollectableEntity implements IUseableEntity {
    public InvisibilityPotionEntity() {
        this(0, 0, 0);
    }
    
    public InvisibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.INVISIBILITY_POTION);
    }

    @Override
    public void used(CharacterEntity player) {
        player.setInvisiblilityRemaining(10);
        player.removeEntityFromInventory(this);
    }
}
