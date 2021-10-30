package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class InvisibilityPotionEntity extends Entity implements ICollectableEntity {
    int duration = 10;
    public InvisibilityPotionEntity() {
        this(0, 0, 0);
    }
    
    public InvisibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, "invisibility_potion");
    }

    public void reduceDuration() {
		duration--;
	}
    
	public int getDuration() {
		return duration;
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
