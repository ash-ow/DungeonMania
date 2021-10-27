package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class SwordEntity extends Entity implements ICollectableEntity {
    public int durability;

    public SwordEntity() {
        this(0, 0, 0);
    }

    public SwordEntity(int x, int y, int layer) {
        super(x, y, layer, "sword");
    }

    @Override
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
        player.setDamage(1);
        if (this.durability > 0) {
            setDurability(this.durability - 1);
            if(this.durability == 0) {
                player.removeEntityFromInventory(this);
            }
        }
    }
}
