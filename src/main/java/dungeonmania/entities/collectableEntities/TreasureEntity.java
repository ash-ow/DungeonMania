package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;

public class TreasureEntity extends Entity implements ICollectableEntity {
    public TreasureEntity() {
        this(0, 0, 0);
    }
    
    
    public TreasureEntity(int x, int y, int layer) {
        super(x, y, layer, "treasure");
    }

    @Override
    public boolean isPlacedAfterUsing() {
        // TODO Auto-generated method stub
        return false;
    }

}
