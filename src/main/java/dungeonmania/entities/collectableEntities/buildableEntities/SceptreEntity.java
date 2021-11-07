package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.IWeaponEntity;

public class SceptreEntity extends BuildableEntity implements IWeaponEntity {
    
    /**
     * Sceptre constructor
     */
    public SceptreEntity() {
        this(0, 0, 0);
    }
    
    /**
     * Sceptre constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public SceptreEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SCEPTRE);
        this.durability = 2;
    }
    
    /**
     * Initialises required components to build a sceptre
     */
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.WOOD, 1);
        this.requiredComponents.put(EntityTypes.ARROW, 2);

        // one wood or two arrows, one key/treasure, and one sun stone
    }
}
