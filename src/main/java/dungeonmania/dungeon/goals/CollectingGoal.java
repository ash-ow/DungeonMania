package dungeonmania.dungeon.goals;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.collectableEntities.TreasureEntity;

public class CollectingGoal  implements IGoal {
    private String type;

    public CollectingGoal() {
        this.type = "treasure";
    }

    public boolean checkGoal(Dungeon dungeon) {
        List<TreasureEntity> collectablesOnFloor = dungeon.getEntities(TreasureEntity.class);
        if (collectablesOnFloor.size() == 0) {
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
