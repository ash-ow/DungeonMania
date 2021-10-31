package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.IWeaponEntity;

public class BowEntity extends BuildableEntity implements IWeaponEntity {
    public BowEntity() {
        this(0, 0, 0);
    }
    
    public BowEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.BOW);
        this.durability = 2;
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.WOOD, 1);
        this.requiredComponents.put(EntityTypes.ARROW, 3);
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    }
}
