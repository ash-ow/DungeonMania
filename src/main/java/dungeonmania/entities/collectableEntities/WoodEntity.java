package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public class WoodEntity extends Entity implements CollectableEntity {
    public WoodEntity() {
        this(0, 0, 0);
    }

    public WoodEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.WOOD);
    }

    @Override
    public boolean isPlacedAfterUsing() {
        // TODO Auto-generated method stub
        // What happens when you build with this? Do you run 'used'?
        return false;
    }
}
