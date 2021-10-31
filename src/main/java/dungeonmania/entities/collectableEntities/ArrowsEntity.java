package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;

public class ArrowsEntity extends CollectableEntity {
    public ArrowsEntity() {
        this(0, 0, 0);
    }
    
    public ArrowsEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ARROW);
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return true;
    }
}
