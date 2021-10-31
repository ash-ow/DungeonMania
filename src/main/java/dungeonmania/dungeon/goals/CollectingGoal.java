package dungeonmania.dungeon.goals;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;

public class CollectingGoal  implements IGoal {
    private String type;

    public CollectingGoal() {
        this.type = "treasure";
    }

    public boolean checkGoal(Dungeon dungeon) {
        List<IEntity> collectablesOnFloor = dungeon.getEntities(EntityTypes.TREASURE);
        if (collectablesOnFloor.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public String getType() {
        return type;
    }

    @Override
    public String getFrontendString() {
        return ":treasure";
    }
}
