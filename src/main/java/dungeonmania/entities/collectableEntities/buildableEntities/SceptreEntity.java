package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.util.DungeonEntityJsonObject;

public class SceptreEntity extends BuildableEntity implements IWeaponEntity {
    
    /**
     * Sceptre constructor
     */
    public SceptreEntity() {
        this(0, 0);
    }
    
    /**
     * Sceptre constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public SceptreEntity(int x, int y) {
        super(x, y, EntityTypes.SCEPTRE);
        this.durability = 2;
    }

    public SceptreEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }
    
    /**
     * Initialises required components to build a sceptre
     */
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.WOOD, 1);
        this.requiredComponents.put(EntityTypes.ARROW, 2);
        this.requiredComponents.put(EntityTypes.TREASURE, 1);
        this.requiredComponents.put(EntityTypes.KEY, 1);
        this.requiredComponents.put(EntityTypes.SUN_STONE, 1);
    }
     
    /**
     * Checks if a sceptre is buildable
     * @param inventory items in inventory are compared against required comonents
     * @return true or false depnding on whether a sceptre is buildable
     */
    @Override
    public boolean isBuildable(List<CollectableEntity> inventory) {
        boolean requiredSunStone = false;
        boolean requiredWood = false;
        boolean requiredArrows = false;
        boolean requiredTreasure = false;
        boolean requiredKey = false;

        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) >= quantity) {
                switch (component) {
                    case SUN_STONE:
                        requiredSunStone = true;
                        break;
                    case WOOD:
                        requiredWood = true;
                        break;
                    case ARROW:
                        requiredArrows = true;
                        break;
                    case TREASURE:
                        requiredTreasure = true;
                        break;
                    case KEY:
                        requiredKey = true;
                        break;
                    default:
                        break;
                }
            }
        }

        return (
            requiredSunStone &&
            (requiredWood || requiredArrows) &&
            (requiredTreasure || requiredKey)
        );
    }
    
}
