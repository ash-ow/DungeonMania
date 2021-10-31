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
    
    protected Map<CollectableEntity, Integer> requiredComponents = new HashMap<CollectableEntity, Integer>();
    public boolean isBuildable(List<CollectableEntity> inventory) {
        for (Map.Entry<CollectableEntity, Integer> entry : requiredComponents.entrySet()) {
            CollectableEntity component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) < quantity) {
                System.out.println("Needs more " + component.getType());
                return false;
            }
        }
        return true;
    }

    protected int numberOfComponentItemsInInventory(List<CollectableEntity> inventory, CollectableEntity component) {
        return inventory
            .stream()
            .filter(ent -> ent.getClass().equals(component.getClass()))
            .collect(Collectors.toList())
            .size();
    }

    protected abstract void initialiseRequiredComponents();
}
