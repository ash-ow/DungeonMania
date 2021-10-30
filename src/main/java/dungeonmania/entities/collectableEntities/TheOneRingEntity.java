package dungeonmania.entities.collectableEntities;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;

import java.util.List;
import java.util.Random;

public class TheOneRingEntity extends CollectableEntity {

    public TheOneRingEntity() {
        this(0, 0, 0);
    }
    
    public TheOneRingEntity(int x, int y, int layer) {
        super(x, y, layer, "one_ring");
    }
    
    public void used(CharacterEntity player){
        if (player.getHealth() <= 0) {
            player.setHealth(100);
        } 
    }
    
    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    }    

}

