package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;

public class WoodEntity extends CollectableEntity {
    /**
     * Wood constructor
     */
    public WoodEntity() {
        this(0, 0, 0);
    }

    /**
     * Wood constructor
     */
    public WoodEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.WOOD);
    }
}
