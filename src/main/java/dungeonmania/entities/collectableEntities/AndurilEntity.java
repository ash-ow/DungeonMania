package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;
import dungeonmania.entities.movingEntities.IBoss;
import dungeonmania.util.DungeonEntityJsonParser;

public class AndurilEntity extends CollectableEntity implements IWeaponEntity {

    public AndurilEntity() {
        this(0,0);
    }

    public AndurilEntity(int x, int y) {
        super(x, y, EntityTypes.ANDURIL);
        this.durability = Integer.MAX_VALUE;
    }
    
    public AndurilEntity(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }

    @Override
    public float getDamage() {
        return 10;
    }

    
    @Override
    public float attack(IBattlingEntity enemy, CharacterEntity player) {
        if (IBoss.class.isInstance(enemy)) {
            return enemy.loseHealth(player.getHealth(), this.getDamage() * 3);
        } else {
            return enemy.loseHealth(player.getHealth(), this.getDamage());
        }
    }
}
