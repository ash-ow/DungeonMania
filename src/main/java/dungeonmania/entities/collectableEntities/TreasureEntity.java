package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public class TreasureEntity extends CollectableEntity {
    public TreasureEntity() {
        this(0, 0, 0);
    }
    
    public TreasureEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.TREASURE);
    }
}
