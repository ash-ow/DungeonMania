package dungeonmania.dungeon.goals;

import java.util.HashMap;


import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.util.Position;

public class ExitGoal implements IGoal {
    private String type;
    public ExitGoal(String type) {
        this.type = type;
    }

    public void checkGoal(Dungeon dungeon, HashMap<String, Boolean> goalsMap) {
        for (IEntity e : dungeon.getEntities()) {
            if (e.getInfo().getType().equals("exit") && dungeon.getPlayer().isInSamePositionAs(e)) {
                goalsMap.put(type, true);
            }
        }
    }
}
