package dungeonmania.entities.collectableEntities;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class ArmourEntity extends Entity implements ICollectableEntity {
    public ArmourEntity() {
        this(0, 0, 0);
    }

    public ArmourEntity(int x, int y, int layer) {
        super(x, y, layer, "armour");
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public void used(CharacterEntity player){
    } 
}
