package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.CollectableEntity;

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
     * @param inventory items in inventory are compared against required comonents
     * @return true or false depnding on whether the item is buildable
     */
    protected Map<EntityTypes, Integer> requiredComponents = new HashMap<EntityTypes, Integer>();
    public boolean isBuildable(List<CollectableEntity> inventory) {
        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) < quantity) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the number of a certain component in the inventory
     * @param inventory inventory which is checked
     * @param component component which is being searched for
     * @return the number of components
     */
    protected int numberOfComponentItemsInInventory(List<CollectableEntity> inventory, EntityTypes component) {
        return inventory
            .stream()
            .filter(ent -> ent.getType() == component)
            .collect(Collectors.toList())
            .size();
    }

    /**
     * Initialises required components to build
     */
    protected abstract void initialiseRequiredComponents();
}
