package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public class TreasureEntity extends CollectableEntity {
    /**
     * Treasure constructor
     */
    public TreasureEntity() {
        this(0, 0, 0);
    }
    
    /**
     * Treasure constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public TreasureEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.TREASURE);
    }
}
