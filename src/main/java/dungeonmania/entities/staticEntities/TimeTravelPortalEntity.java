package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.DungeonEntityJsonObject;

public class TimeTravelPortalEntity extends Entity implements IContactingEntity {
    public TimeTravelPortalEntity() {
        this(0, 0);
    }
    
    public TimeTravelPortalEntity(int x, int y) {
        super(x, y, EntityTypes.TIME_TRAVEL_PORTAL);
    }

    public TimeTravelPortalEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    @Override
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        player.setTimeTravelling(true);
    }
}
