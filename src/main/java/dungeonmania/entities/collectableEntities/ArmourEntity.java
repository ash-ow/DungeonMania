package dungeonmania.entities.collectableEntities;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class ArmourEntity extends Entity implements ICollectableEntity {
    public ArmourEntity() {
        this(0, 0, 0);
    }

    public ArmourEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ARMOUR);
    }

    @Override
    public void used(CharacterEntity player) {
        // TODO
    }

    @Override
    public boolean isPlacedAfterUsing() {
        // TODO Auto-generated method stub
        return false;
    } 
}
