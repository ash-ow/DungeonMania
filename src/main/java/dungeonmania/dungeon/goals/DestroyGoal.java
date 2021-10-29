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
        List<IEntity> enemies = new ArrayList<>();
        enemies.addAll(dungeon.getEntities("spider"));
        enemies.addAll(dungeon.getEntities("mercenary"));
        enemies.addAll(dungeon.getEntities("zombie_toast"));
        if (enemies.size() == 0) {
            return true;
        }
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
