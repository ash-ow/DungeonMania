package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.DungeonEntityJsonParser;

public class TimeTravelPortal extends Entity implements IContactingEntity {
    public TimeTravelPortal() {
        this(0, 0);
    }
    
    public TimeTravelPortal(int x, int y) {
        super(x, y, EntityTypes.TIME_TRAVEL_PORTAL);
    }

    public TimeTravelPortal(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }

    @Override
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        player.timeTravel();
    }
}
