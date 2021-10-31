package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.CollectableEntity;

public abstract class BuildableEntity extends CollectableEntity {
    protected BuildableEntity(int x, int y, int layer, EntityTypes type) {
        super(x, y, layer, type);
        initialiseRequiredComponents();
    }
    
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

    protected int numberOfComponentItemsInInventory(List<CollectableEntity> inventory, EntityTypes component) {
        return inventory
            .stream()
            .filter(ent -> ent.getType() == component)
            .collect(Collectors.toList())
            .size();
    }

    protected abstract void initialiseRequiredComponents();
}
