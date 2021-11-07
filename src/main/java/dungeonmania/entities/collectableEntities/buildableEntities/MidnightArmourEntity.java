package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.collectableEntities.IDefensiveEntity;

public class MidnightArmourEntity extends BuildableEntity implements IDefensiveEntity {
    /**
     * MidnightArmour constructor
     */
    public MidnightArmourEntity() {
        this(0, 0); 
    }
    
    /**
     * MidnightArmour constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public MidnightArmourEntity(int x, int y) {
        super(x, y, EntityTypes.SHIELD);
        this.durability = Integer.MAX_VALUE;
    }
    
    /**
     * Initialises required components to build a bow
     */
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.ARMOUR, 1);
        this.requiredComponents.put(EntityTypes.SUN_STONE, 1);
    }
    
    @Override 
    public float reduceDamage(float damage, CharacterEntity player) {
        this.used(player);
        damage = damage/5;
        return damage;
    }
}
