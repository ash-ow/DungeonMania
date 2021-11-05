package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public class SunStoneEntity extends CollectableEntity {
    /**
     * Sun Stone constructor
     */
    public SunStoneEntity() {
        this(0, 0, 0);
    }
    
    /**
     * Sun Stone constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public SunStoneEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SUN_STONE);
    }
}

