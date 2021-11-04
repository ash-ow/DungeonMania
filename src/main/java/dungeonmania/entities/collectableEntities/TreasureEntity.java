package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.JsonControl;

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
     * @param layer layer on the map 
     */
    public TreasureEntity(int x, int y) {
        super(x, y, EntityTypes.TREASURE);
    }

    public TreasureEntity(JsonControl info) {
        this(info.getPosition().getX(), info.getPosition().getY());
    }
}
