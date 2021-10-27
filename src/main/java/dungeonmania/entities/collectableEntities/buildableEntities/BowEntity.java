package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class BowEntity extends BuildableEntity {
    public BowEntity() {
        this(0, 0, 0);
    }
    
    public BowEntity(int x, int y, int layer) {
        super(x, y, layer, "bow");
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(new WoodEntity(), 1);
        this.requiredComponents.put(new ArrowsEntity(), 3);
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public void used(CharacterEntity player){
    }

}
