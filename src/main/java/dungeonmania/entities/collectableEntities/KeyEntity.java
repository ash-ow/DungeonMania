package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class KeyEntity extends Entity implements ICollectableEntity {
    public KeyEntity() {
        this(0, 0, 0);
    }
    
    
    public KeyEntity(int x, int y, int layer) {
        super(x, y, layer, "key");
    }

    @Override
    public void used(CharacterEntity player){

    }

}
