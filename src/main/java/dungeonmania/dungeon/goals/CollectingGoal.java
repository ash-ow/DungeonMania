package dungeonmania.dungeon.goals;

import java.util.HashMap;

import dungeonmania.dungeon.Dungeon;

public class CollectingGoal  implements IGoal {
    private String type;

    public CollectingGoal() {
        this.type = "treasure";
    }

    public boolean checkGoal(Dungeon dungeon) {
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
