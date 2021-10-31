package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class SwordEntity extends Entity implements ICollectableEntity, IWeaponEntity{
    public int durability;

    public SwordEntity() {
        this(0, 0, 0);
    }

    public SwordEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SWORD);
    }

    public int getDurability(){
        return this.durability;
    }

    public void setDurability(int durability){
        this.durability = durability;
    }

    @Override
    public void used(CharacterEntity player) {

    }

    @Override
    public boolean isPlacedAfterUsing() {
        // TODO Auto-generated method stub
        return false;
    }
}
