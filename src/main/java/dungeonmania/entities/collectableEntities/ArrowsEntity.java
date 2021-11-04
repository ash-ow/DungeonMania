package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.util.JsonControl;

public class ArrowsEntity extends CollectableEntity {
    /**
     * Arrows constructor
     */
    public ArrowsEntity() {
        this(0, 0);
    }
    
    /**
     * Arrows constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public ArrowsEntity(int x, int y) {
        super(x, y, EntityTypes.ARROW);
    }

    public ArrowsEntity(JsonControl info) {
        this(info.getPosition().getX(), info.getPosition().getY());
    }
}
