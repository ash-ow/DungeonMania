package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.PlayerState;

public class InvisibilityPotionEntity extends Entity implements ICollectableEntity {
    public InvisibilityPotionEntity() {
        this(0, 0, 0);
    }
    
    public InvisibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, "invisibility_potion");
    }

    @Override
    public void used(CharacterEntity player) {
        player.isInvisible();
        player.getDuration();
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    } 
}
