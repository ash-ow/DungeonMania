package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;

public class ShieldEntity extends BuildableEntity {
    
    public ShieldEntity() {
        this(0, 0, 0); 
    }
    
    public ShieldEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SHIELD);
        this.durability = 4;
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(EntityTypes.WOOD, 2);
        this.requiredComponents.put(EntityTypes.TREASURE, 1);
        this.requiredComponents.put(EntityTypes.KEY, 1);
    }
    
    @Override
    public boolean isBuildable(List<CollectableEntity> inventory) {
        boolean requiredWood = false;
        boolean requiredTreasure = false;
        boolean requiredKey = false;
        for (Map.Entry<EntityTypes, Integer> entry : requiredComponents.entrySet()) {
            EntityTypes component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) >= quantity) {
                if (component.equals(EntityTypes.WOOD)) {
                    requiredWood = true;
                } else if (component.equals(EntityTypes.TREASURE)) {
                    requiredTreasure = true;
                } else if (component.equals(EntityTypes.KEY)) {
                    requiredKey = true;
                }
            }
        }
        if (requiredTreasure || requiredKey) {
            if (requiredWood) {
                return true;
            }
        }
        return false;
    }
    
    public float reduceDamage(float damage, CharacterEntity player) {
        this.used(player);
        damage = damage/2;
        return damage;
    }
}
