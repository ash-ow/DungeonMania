package dungeonmania.entities.buildableEntities;

import java.util.Map;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntities;

public interface IBuildableEntities extends IEntity {
    public default boolean isBuildable() {
        System.out.println("Building " + this.getId());
        for (Map.Entry<ICollectableEntities, Integer> entry : this.getRequiredComponents().entrySet()) {
            ICollectableEntities component = entry.getKey();
            int quantity = entry.getValue();

            System.out.println(" >" + quantity + "x " + component.toString());
        }

        return true;
    }
    public Map<ICollectableEntities, Integer> getRequiredComponents();
}
