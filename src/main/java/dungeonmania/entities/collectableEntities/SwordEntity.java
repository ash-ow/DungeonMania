package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonObject;

public class SwordEntity extends CollectableEntity implements IWeaponEntity {
    /**
     * Sword constructor
     */
    public SwordEntity() {
        this(0, 0);
    }
    
    public SwordEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    /**
     * Sword constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public SwordEntity(int x, int y) {
        super(x, y, EntityTypes.SWORD);
        this.durability = 5;
    }

    @Override
    public float getDamage() {
        return 3;
    }
}
