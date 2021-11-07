package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonObject;

public class WoodEntity extends CollectableEntity {
    /**
     * Wood constructor
     */
    public WoodEntity() {
        this(0, 0);
    }

    /**
     * Wood constructor
     */
    public WoodEntity(int x, int y) {
        super(x, y, EntityTypes.WOOD);
    }

    public WoodEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }
}
