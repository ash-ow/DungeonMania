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
    public void used(CharacterEntity player) {
    }

    @Override
    public boolean isPlacedAfterUsing() {
        // TODO Auto-generated method stub
        return false;
    } 
}
