package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class ArmourEntity extends CollectableEntity {
    public ArmourEntity() {
        this(0, 0, 0);
    }

    public ArmourEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ARMOUR);
        this.durability = 4;
    }
    
    public float reduceDamage(float damage, CharacterEntity player) {
        this.used(player);
        damage = damage/2;
        return damage;
    }
}
