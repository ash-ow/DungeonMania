package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;

public abstract class BuildableEntity extends Entity implements ICollectableEntity {
    protected BuildableEntity(String type) {
        super(type);
        initialiseRequiredComponents();
    }

    protected BuildableEntity(String id, int x, int y, String type) {
        super(id, x, y, type);
        initialiseRequiredComponents();
    }
    
    public Map<ICollectableEntity, Integer> requiredComponents = new HashMap<ICollectableEntity, Integer>();
    public boolean isBuildable(List<ICollectableEntity> inventory) {
        for (Map.Entry<ICollectableEntity, Integer> entry : requiredComponents.entrySet()) {
            ICollectableEntity component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) < quantity) {
                System.out.println("Needs more " + component.getId());
                return false;
            }
        }
        return true;
    }

    private int numberOfComponentItemsInInventory(List<ICollectableEntity> inventory, ICollectableEntity component) {
        return inventory
            .stream()
            .filter(ent -> ent.getClass().equals(component.getClass()))
            .collect(Collectors.toList())
            .size();
    }

    protected abstract void initialiseRequiredComponents();
}
