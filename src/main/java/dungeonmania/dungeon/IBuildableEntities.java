package dungeonmania.dungeon;

import java.util.HashMap;
import java.util.Map;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntities;

public interface IBuildableEntities extends IEntity {
    public static Map<ICollectableEntities, Integer> requiredComponents = new HashMap<ICollectableEntities, Integer>();
    public static boolean isBuildable(Inventory inventory) {
        for (Map.Entry<ICollectableEntities, Integer> entry : requiredComponents.entrySet()) {
            ICollectableEntities component = entry.getKey();
            int quantity = entry.getValue();

            System.out.println(" >" + quantity + "x " + component.toString());
        }

        return true;
    }
}
