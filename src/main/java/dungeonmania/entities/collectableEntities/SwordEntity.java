package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class SwordEntity extends CollectableEntity implements IWeaponEntity{
    public int durability;

    public SwordEntity() {
        this(0, 0, 0);
    }

    public SwordEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SWORD);
        this.durability = 5;
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return true;
    }
}
