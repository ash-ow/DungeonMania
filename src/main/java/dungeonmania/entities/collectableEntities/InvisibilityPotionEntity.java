package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class InvisibilityPotionEntity extends Entity implements ICollectableEntity {
    public InvisibilityPotionEntity() {
        this(0, 0, 0);
    }
    
    public InvisibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, "invisibility_potion");
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public void used(CharacterEntity player){

    }

}