package dungeonmania.dungeon.goals;

import java.util.HashMap;

import dungeonmania.dungeon.Dungeon;

public class DestroyGoal implements IGoal {

    private String type;

    public DestroyGoal() {
        this.type = "enemies";
    }

    public boolean checkGoal(Dungeon dungeon) {
        return false;
    }
    
    public String getType() {
        return type;
    }

    @Override
    public String getFrontendString() {
        return ":mercenary";
    }
}
