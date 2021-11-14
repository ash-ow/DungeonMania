package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.Inventory;


public class SceptreEntity extends BuildableEntity implements ITicker {
    private CharacterEntity owner;

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
        this.durability = 0;
    }

    public SceptreEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
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
    public boolean isBuildable(Inventory inventory) {
        boolean requiredSunStone = false;
        boolean requiredWood = false;
        boolean requiredArrows = false;
        boolean requiredTreasure = false;
        boolean requiredKey = false;
        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (inventory.numberOfComponentItemsInInventory(component) >= quantity) {
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

    @Override
    public void tick(EntitiesControl entitiesControl) {
        decrementDurability();
        if (this.durability == 0) {
            List<MercenaryEntity> mercenaries = entitiesControl.getAllEntitiesOfType(MercenaryEntity.class);
            mercenaries.stream().forEach(m -> m.bribe(owner));
        } else if (this.durability > 0) {
            List<MercenaryEntity> mercenaries = entitiesControl.getAllEntitiesOfType(MercenaryEntity.class);
            mercenaries.stream().forEach(m -> m.betray(owner));
        }
    }

    @Override
    public void used(CharacterEntity player) {
        this.durability = 10; 
        player.addActiveItem(this);
        this.owner = player;
    }
        
    public void build(Inventory inventory) {
        inventory.addItem(this);
        inventory.removeBuildMaterials(EntityTypes.SUN_STONE, 1);
        if(inventory.containsItemOfType(EntityTypes.TREASURE)) {
            inventory.removeBuildMaterials(EntityTypes.TREASURE, 1);
        } else if (inventory.containsItemOfType(EntityTypes.KEY)) {
            inventory.removeBuildMaterials(EntityTypes.KEY, 1);
        } 
        if(inventory.containsItemOfType(EntityTypes.WOOD)) {
            inventory.removeBuildMaterials(EntityTypes.WOOD, 1);
        } else if (inventory.containsItemOfType(EntityTypes.ARROW)) {
            inventory.removeBuildMaterials(EntityTypes.ARROW, 1);
        }
    }
}
