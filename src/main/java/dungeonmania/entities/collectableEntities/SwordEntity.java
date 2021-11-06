package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.DungeonEntityJsonParser;

public class SwordEntity extends CollectableEntity implements IWeaponEntity{
    /**
     * Sword constructor
     */
    public SwordEntity() {
        this(0, 0);
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

    public SwordEntity(DungeonEntityJsonParser info) {
        this(info.getPosition().getX(), info.getPosition().getY());
    }
}
