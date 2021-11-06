package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;

public class SwordEntity extends CollectableEntity implements IWeaponEntity {
    /**
     * Sword constructor
     */
    public SwordEntity() {
        this(0, 0, 0);
    }

    /**
     * Sword constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public SwordEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SWORD);
        this.durability = 5;
    }

    @Override
    public float getDamage() {
        return 3;
    }
}
