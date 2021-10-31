package dungeonmania.entities.buildableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class BowEntity extends BuildableEntity implements IWeaponEntity{
    int durability;

    public BowEntity() {
        this(0, 0, 0);
    }
    
    public BowEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.BOW);
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(new WoodEntity(), 1);
        this.requiredComponents.put(new ArrowsEntity(), 3);
    }

    public boolean isPassable() {
        return true;
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

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    }
}
