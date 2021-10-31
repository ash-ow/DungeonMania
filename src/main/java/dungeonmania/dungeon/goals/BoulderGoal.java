package dungeonmania.dungeon.goals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.util.Position;

public class BoulderGoal implements IGoal {
    private String type;

    public BoulderGoal() {
        this.type = "boulders";
    }

    public boolean checkGoal(Dungeon dungeon) {
        List<Position> switchPosition = dungeon.getEntities(EntityTypes.SWITCH).stream().map(IEntity::getPosition).collect(Collectors.toList());
        List<Position> boulderPosition = dungeon.getEntities(EntityTypes.BOULDER).stream().map(IEntity::getPosition).collect(Collectors.toList());
        return boulderPosition.containsAll(switchPosition);
    }

    public String getType() {
        return type;
    }

    @Override
    public String getFrontendString() {
        return ":boulder";
    }
}
