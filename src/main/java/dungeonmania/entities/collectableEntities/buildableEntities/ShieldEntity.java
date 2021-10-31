package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;

public class ShieldEntity extends BuildableEntity {
    
    public ShieldEntity() {
        this(0, 0, 0); 
    }
    
    public ShieldEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SHIELD);
        this.durability = 10;
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(new WoodEntity(), 2);
        this.requiredComponents.put(new TreasureEntity(), 1);
        this.requiredComponents.put(new KeyEntity(0,0,0,1), 1);
    }
    
    @Override
    public boolean isBuildable(List<CollectableEntity> inventory) {
        boolean requiredWood = false;
        boolean requiredTreasure = false;
        boolean requiredKey = false;
        for (Map.Entry<CollectableEntity, Integer> entry : requiredComponents.entrySet()) {
            CollectableEntity component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, component) >= quantity) {
                System.out.println("Needs more " + component.getType());
                if (component.getType().equals(EntityTypes.WOOD)) {
                    requiredWood = true;
                } else if (component.getType().equals(EntityTypes.TREASURE)) {
                    requiredTreasure = true;
                } else if (component.getType().equals(EntityTypes.KEY)) {
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

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    }
}
