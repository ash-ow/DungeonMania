package dungeonmania.dungeon.goals;

import java.util.HashMap;

import dungeonmania.dungeon.Dungeon;

public class BoulderGoal implements IGoal {
    private String type;

    public BoulderGoal() {
        this.type = "boulders";
    }

    public boolean checkGoal(Dungeon dungeon) {
        return false;
    }
    
    public String getType() {
        return type;
    }
}
