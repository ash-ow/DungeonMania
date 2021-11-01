package dungeonmania.dungeon.goals;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.collectableEntities.TreasureEntity;

public class CollectingGoal  implements IGoal {
    private String type;

    /**
     * CollectingGoal constructor
     */
    public CollectingGoal() {
        this.type = "treasure";
    }

    /**
     * Evaluates whether goal has passed
     * @param dungeon  dungeon to be checked
     */
    public boolean checkGoal(Dungeon dungeon) {
        List<TreasureEntity> collectablesOnFloor = dungeon.getAllEntitiesOfType(TreasureEntity.class);
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
