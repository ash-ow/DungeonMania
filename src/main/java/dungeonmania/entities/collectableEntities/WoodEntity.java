package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;

public class WoodEntity extends CollectableEntity {
    public WoodEntity() {
        this(0, 0, 0);
    }

    public WoodEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.WOOD);
    }
}
