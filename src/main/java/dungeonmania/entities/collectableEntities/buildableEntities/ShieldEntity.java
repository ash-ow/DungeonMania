package dungeonmania.entities.collectableEntities.buildableEntities;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

import dungeonmania.entities.collectableEntities.ICollectableEntity;

public class ShieldEntity extends BuildableEntity {
    int durability;
    
    public ShieldEntity() {
        this(0, 0, 0);
    }
    
    public ShieldEntity(int x, int y, int layer) {
        super(x, y, layer, "shield");
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(new WoodEntity(), 2);
        this.requiredComponents.put(new TreasureEntity(), 1);
        this.requiredComponents.put(new KeyEntity(), 1);
    }
    
    @Override
    public boolean isBuildable(List<ICollectableEntity> inventory) {
        for (Map.Entry<ICollectableEntity, Integer> entry : requiredComponents.entrySet()) {
            ICollectableEntity component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, new WoodEntity()) < quantity) {
                System.out.println("Needs more " + component.getId());
                return false;
            }
            if (numberOfComponentItemsInInventory(inventory, new TreasureEntity()) < quantity) {
                System.out.println("Needs more " + component.getId());
            } else if (numberOfComponentItemsInInventory(inventory, new KeyEntity()) < quantity) {
                System.out.println("Needs more " + component.getId());
                return false;
            }
        }
        return true;
    }

    public boolean isPassable() {
        // TODO Auto-generated method stub
        return false;
    }

    public int getDurability(){
        return this.durability;
    }

    public void setDurability(int durability){
        this.durability = durability;
    }

    @Override
    public void used(CharacterEntity player){
        if (this.durability > 0) {
            setDurability(this.durability - 1);
            if(this.durability == 0) {
                player.removeEntityFromInventory(this);
            }
        }
    }
    
    public float reduceDamage(float enemyHealth, int enemyDamage) {
        float damage = ((enemyHealth * enemyDamage) / 10)/2;
        return damage;
    }

}
