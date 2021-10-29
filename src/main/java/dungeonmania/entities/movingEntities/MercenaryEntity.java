package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity{

    private float health;
    private int damage;

    protected MercenaryEntity(int x, int y, int layer, String type) {
        super(x, y, layer, "mercenary");
        this.health = 20;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
        
    }

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        // TODO Auto-generated method stub

        

        return false;
    }
    
}
