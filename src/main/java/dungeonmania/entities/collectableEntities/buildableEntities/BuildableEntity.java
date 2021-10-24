package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;
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
        System.out.println("Required:");
        for (Map.Entry<ICollectableEntity, Integer> entry : requiredComponents.entrySet()) {
            ICollectableEntity component = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(" >" + quantity + "x " + component.getId());
        }

        System.out.println("\nInventory:\n");
        for (ICollectableEntity item : inventory) {
            System.out.println(" >" + item.getId());
        }
        
        return true;
    }

    protected abstract void initialiseRequiredComponents();
}
