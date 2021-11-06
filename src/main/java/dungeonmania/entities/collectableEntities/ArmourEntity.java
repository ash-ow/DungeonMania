package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.DungeonEntityJsonParser;

public class ArmourEntity extends CollectableEntity {
    /**
     * Armour constructor
     */
    public ArmourEntity() {
        this(0, 0);
    }

    /**
     * Armour constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public ArmourEntity(int x, int y) {
        super(x, y, EntityTypes.ARMOUR);
        this.durability = 4;
    }
    
    public ArmourEntity(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }
    
    /**
     * Reduces input damage for the player
     * @param damage input damage for the player
     * @param player player which is being damaged
     * @return redecued value for damage as a float
     */
    public float reduceDamage(float damage, CharacterEntity player) {
        this.used(player);
        damage = damage/2;
        return damage;
    }
}
