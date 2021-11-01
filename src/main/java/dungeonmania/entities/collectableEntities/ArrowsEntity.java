package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;

public class ArrowsEntity extends CollectableEntity {
    /**
     * Arrows constructor
     */
    public ArrowsEntity() {
        this(0, 0, 0);
    }
    
    /**
     * Arrows constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public ArrowsEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ARROW);
    }
}
