package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class InvincibilityPotionEntity extends Entity implements ICollectableEntity {

    public InvincibilityPotionEntity() {
        this(0, 0, 0);
    }
    

    public InvincibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, "invincibility_potion");
    }

    @Override
    public void used(CharacterEntity player){

    }
}
