package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;


public class SwordEntity extends CollectableEntity implements IWeaponEntity {
    /**
     * Sword constructor
     */
    public SwordEntity() {
        this(0, 0);
    }
    
    public SwordEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    /**
     * Sword constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
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
