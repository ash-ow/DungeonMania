package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.IBoss;

public class AndurilEntity extends CollectableEntity implements IWeaponEntity {

    public AndurilEntity() {
        this(0,0,0);
    }

    public AndurilEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ANDURIL);
        this.durability = Integer.MAX_VALUE;
    }

    @Override
    public float getDamage() {
        return 10;
    }

    
    @Override
    public float attack(IBattlingEntity enemy, CharacterEntity player) {
        if (IBoss.class.isInstance(enemy)) {
            
        }
        return enemy.loseHealth(player.getHealth(), this.getDamage());
    }
}
