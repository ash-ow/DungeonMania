package dungeonmania.entities.collectableEntities;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class ArmourEntity extends Entity implements ICollectableEntity {
    //TODO: set default durability
    public int durability;

    public ArmourEntity() {
        this(0, 0, 0);
    }

    public ArmourEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ARMOUR);
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
    public boolean isPlacedAfterUsing() {
        // TODO Auto-generated method stub
        return false;
    } 
}
