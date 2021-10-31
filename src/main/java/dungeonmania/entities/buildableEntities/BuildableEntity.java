package dungeonmania.entities.buildableEntities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.ICollectableEntity;

public abstract class BuildableEntity extends Entity {
    protected BuildableEntity(int x, int y, int layer, EntityTypes type) {
        super(x, y, layer, type);
        initialiseRequiredComponents();
    }
    
    protected Map<EntityTypes, Integer> requiredComponents = new HashMap<EntityTypes, Integer>();
    public boolean isBuildable(List<ICollectableEntity> inventory) {
        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) < quantity) {
                System.out.println("Needs more " + component.toString());
                return false;
            }
        }
        return true;
    }

    private int numberOfComponentItemsInInventory(List<ICollectableEntity> inventory, EntityTypes component) {
        return inventory
            .stream()
            .filter(ent -> ent.getType() == component)
            .collect(Collectors.toList())
            .size();
    }

    protected abstract void initialiseRequiredComponents();
}
