package dungeonmania.dungeon.goals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;

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
