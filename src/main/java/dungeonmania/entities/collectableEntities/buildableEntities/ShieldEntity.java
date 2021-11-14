package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.Map;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.Inventory;
import dungeonmania.entities.collectableEntities.IDefensiveEntity;

public class ShieldEntity extends BuildableEntity implements IDefensiveEntity {
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
     */
    public ShieldEntity(int x, int y) {
        super(x, y, EntityTypes.SHIELD);
        this.durability = 4;
    }
    
    /**
     * Initialises required components to build a shield
     */
    @Override
    public void initialiseRequiredComponents() {

        this.requiredComponents.put(EntityTypes.WOOD, 2);
        this.requiredComponents.put(EntityTypes.TREASURE, 1);
        this.requiredComponents.put(EntityTypes.KEY, 1);
        this.requiredComponents.put(EntityTypes.SUN_STONE, 1);
    }
    
    /**
     * Checks if a shield is buildable
     * @param inventory items in inventory are compared against required comonents
     * @return true or false depnding on whether a shield is buildable
     */
    @Override
    public boolean isBuildable(Inventory inventory) {
        boolean requiredWood = false;
        boolean requiredTreasure = false;
        boolean requiredKey = false;
        boolean requiredStone = false;
        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (inventory.numberOfComponentItemsInInventory(component) >= quantity) {
                switch (component) {
                    case WOOD:
                        requiredWood = true;
                        break;
                    case TREASURE:
                        requiredTreasure = true;
                        break;
                    case KEY:
                        requiredKey = true;
                        break;
                    case SUN_STONE:
                        requiredStone = true;
                        break;
                    default:
                        break;
                }
            }
        }
        return (
            requiredWood &&
            (requiredTreasure || requiredKey || requiredStone)
        );
    }


    
    @Override 
    public float reduceDamage(float damage, CharacterEntity player) {
        this.used(player);
        damage = damage/2;
        return damage;
    }

    @Override
    public void build(Inventory inventory) {
        inventory.addItem(this);
        
        inventory.removeBuildMaterials(EntityTypes.WOOD, 2);
        if(inventory.containsItemOfType(EntityTypes.TREASURE)) {
            inventory.removeBuildMaterials(EntityTypes.TREASURE, 1);
        } else if (inventory.containsItemOfType(EntityTypes.KEY)) {
            inventory.removeBuildMaterials(EntityTypes.KEY, 1);
        } else if (inventory.containsItemOfType(EntityTypes.SUN_STONE)) {
            inventory.removeBuildMaterials(EntityTypes.SUN_STONE, 1);
        }
    }
}
