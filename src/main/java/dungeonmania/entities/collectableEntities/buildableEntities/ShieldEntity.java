package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class ShieldEntity extends BuildableEntity {
    public ShieldEntity() {
        this(0, 0, 0);
    }
    
    public ShieldEntity(int x, int y, int layer) {
        super(x, y, layer, "shield");
    }
    
    @Override
    public void initialiseRequiredComponents() {
        // TODO Get real required components
        this.requiredComponents.put(new WoodEntity(), 1);
        this.requiredComponents.put(new ArrowsEntity(), 3);
    }

    @Override
    public boolean isPassable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void used(CharacterEntity player){
    }

}
