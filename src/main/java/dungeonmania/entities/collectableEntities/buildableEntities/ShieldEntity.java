package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;

public class ShieldEntity extends BuildableEntity {
    /**
     * Shield constructor
     */
    public ShieldEntity() {
        this(0, 0); 
    }
    
    /**
     * Shield constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public ShieldEntity(int x, int y) {
        super(x, y, EntityTypes.SHIELD);
        this.durability = 4;
    }
    
    /**
     * Initialises required components to build a bow
     */
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.WOOD, 2);
        this.requiredComponents.put(EntityTypes.TREASURE, 1);
        this.requiredComponents.put(EntityTypes.KEY, 1);
    }
    
    /**
     * Checks if a shield is buildable
     * @param inventory items in inventory are compared against required comonents
     * @return true or false depnding on whether a shield is buildable
     */
    @Override
    public boolean isBuildable(List<CollectableEntity> inventory) {
        boolean requiredWood = false;
        boolean requiredTreasure = false;
        boolean requiredKey = false;
        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) >= quantity) {
                if (component.equals(EntityTypes.WOOD)) {
                    requiredWood = true;
                } else if (component.equals(EntityTypes.TREASURE)) {
                    requiredTreasure = true;
                } else if (component.equals(EntityTypes.KEY)) {
                    requiredKey = true;
                }
            }
        }
        if (requiredTreasure || requiredKey) {
            if (requiredWood) {
                return true;
            }
        }
        return false;
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
