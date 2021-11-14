package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.Map;
import java.util.HashMap;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.movingEntities.Inventory;

public abstract class BuildableEntity extends CollectableEntity {
    /**
     * Constructor for buildable entities
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param type type of entity 
     */
    protected BuildableEntity(int x, int y, EntityTypes type) {
        super(x, y, type);
        initialiseRequiredComponents();
    }
    
    /**
     * Checks if a buildable entity is buildable
     * @param items items in inventory are compared against required comonents
     * @return true or false depnding on whether the item is buildable
     */
    protected Map<EntityTypes, Integer> requiredComponents = new HashMap<EntityTypes, Integer>();
    public boolean isBuildable(Inventory inventory) {
        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (inventory.numberOfComponentItemsInInventory(component) < quantity) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initialises required components to build
     */
    protected abstract void initialiseRequiredComponents();

    /**
     * Builds the BuildableEntity
     */
    public abstract void build(Inventory inventory);
}
