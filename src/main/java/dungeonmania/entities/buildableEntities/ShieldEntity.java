package dungeonmania.entities.buildableEntities;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;

public class ShieldEntity extends BuildableEntity {
    //TODO: set default durability
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
        boolean wood = false;
        boolean treasure = false;
        boolean key = false;
        for (Map.Entry<ICollectableEntity, Integer> entry : requiredComponents.entrySet()) {
            ICollectableEntity component = entry.getKey();
            int quantity = entry.getValue();
            if (numberOfComponentItemsInInventory(inventory, new WoodEntity()) >= quantity) {
                System.out.println("Needs more " + component.getId());
                wood = true;
            }
            if (numberOfComponentItemsInInventory(inventory, new TreasureEntity()) >= quantity) {
                System.out.println("Needs more " + component.getId());
                treasure = true;
            } 
            if (numberOfComponentItemsInInventory(inventory, new KeyEntity()) >= quantity) {
                System.out.println("Needs more " + component.getId());
                key = true;
            }
        }
        if (treasure || key) {
            if (wood) {
                return true;
            }
        }
        return false;
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
    
    public float reduceDamage(float damage, CharacterEntity player) {
        this.used(player);
        damage = damage/2;
        return damage;
    }

}
