package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.collectableEntities.IWeaponEntity;

public class BowEntity extends BuildableEntity implements IWeaponEntity {
    
    /**
     * Bow constructor
     */
    public BowEntity() {
        this(0, 0);
    }
    
    /**
     * Bow constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public BowEntity(int x, int y) {
        super(x, y, EntityTypes.BOW);
        this.durability = 2;
    }
    
    /**
     * Initialises required components to build a bow
     */
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.WOOD, 1);
        this.requiredComponents.put(EntityTypes.ARROW, 3);
    }

    @Override
    public float getDamage() {
        return 2;
    }

    @Override
    public void build(List<ICollectable> inventory) {
        inventory.add(this);
        removeBuildMaterials(inventory, EntityTypes.WOOD, 1);
        removeBuildMaterials(inventory, EntityTypes.ARROW, 3);
    }
}
