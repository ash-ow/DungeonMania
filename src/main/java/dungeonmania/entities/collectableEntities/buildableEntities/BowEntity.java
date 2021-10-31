package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;

public class BowEntity extends BuildableEntity implements IWeaponEntity {
    public BowEntity() {
        this(0, 0, 0);
    }
    
    public BowEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.BOW);
        this.durability = 10;
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
