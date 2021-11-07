package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.DungeonEntityJsonParser;

public class TimeTurnerEntity extends CollectableEntity{
    public TimeTurnerEntity() {
        this(0, 0);
    }

    public TimeTurnerEntity(int x, int y) {
        super(x, y, EntityTypes.TIME_TURNER);
    }

    public TimeTurnerEntity(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }

    @Override
    public void used(CharacterEntity player) {
        player.timeTravel();
    }   
}
