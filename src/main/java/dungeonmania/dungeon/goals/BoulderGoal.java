package dungeonmania.dungeon.goals;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.util.Position;

public class BoulderGoal implements IGoal {
    private String type;

    public BoulderGoal() {
        this.type = "boulders";
    }

    public boolean checkGoal(Dungeon dungeon) {
        List<Position> switchPosition = dungeon.getAllEntitiesOfType(SwitchEntity.class).stream().map(IEntity::getPosition).collect(Collectors.toList());
        List<Position> boulderPosition = dungeon.getAllEntitiesOfType(BoulderEntity.class).stream().map(IEntity::getPosition).collect(Collectors.toList());
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
