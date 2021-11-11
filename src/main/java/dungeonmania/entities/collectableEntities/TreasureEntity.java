package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonObject;

public class TreasureEntity extends CollectableEntity {
    /**
     * Treasure constructor
     */
    public TreasureEntity() {
        this(0, 0);
    }
    
    /**
     * Treasure constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public TreasureEntity(int x, int y) {
        this(x, y, EntityTypes.TREASURE);
    }

    public TreasureEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    public TreasureEntity(int x, int y, EntityTypes type) {
        super(x, y, type);
    }

}
