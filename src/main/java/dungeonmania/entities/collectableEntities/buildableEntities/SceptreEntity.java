package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.CollectableEntity;
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
        // TODO one wood or two arrows, one key/treasure, and one sun stone
    }
     
    /**
     * Checks if a sceptre is buildable
     * @param inventory items in inventory are compared against required comonents
     * @return true or false depnding on whether a sceptre is buildable
     */
    @Override
    public boolean isBuildable(List<CollectableEntity> inventory) {
        // TODO
        return true;
    }
    
}
