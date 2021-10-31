package dungeonmania.entities.buildableEntities;

import dungeonmania.entities.EntityTypes;

public class ShieldEntity extends BuildableEntity {
    public ShieldEntity() {
        this(0, 0, 0);
    }
    
    public ShieldEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SHIELD);
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.WOOD, 2);
        this.requiredComponents.put(EntityTypes.TREASURE, 1);
        this.requiredComponents.put(EntityTypes.KEY, 1);
    }
}
