package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.HydraEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;
import dungeonmania.entities.movingEntities.IBoss;

public class AndurilEntity extends CollectableEntity implements IWeaponEntity {

    public AndurilEntity() {
        this(0,0);
    }

    public AndurilEntity(int x, int y) {
        super(x, y, EntityTypes.ANDURIL);
        this.durability = Integer.MAX_VALUE;
    }
    
    public AndurilEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    @Override
    public float getDamage() {
        return 10;
    }
    
    @Override
    public float attack(IBattlingEntity enemy, CharacterEntity player) {
        if (IBoss.class.isInstance(enemy)) {
            IBoss boss = (IBoss) enemy;
            boss.deactivteSpecialAbility();
            return enemy.loseHealth(player.getHealth(), this.getDamage() * 3);
        } else {
            return enemy.loseHealth(player.getHealth(), this.getDamage());
        }
    }
}
