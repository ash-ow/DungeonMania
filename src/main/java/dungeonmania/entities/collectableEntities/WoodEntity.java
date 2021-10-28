package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class WoodEntity extends Entity implements ICollectableEntity {
    public WoodEntity() {
        this(0, 0, 0);
    }

    public WoodEntity(int x, int y, int layer) {
        super(x, y, layer, "wood");
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    public void used(CharacterEntity player){
        // TODO: Remove from inventory
    }
}
