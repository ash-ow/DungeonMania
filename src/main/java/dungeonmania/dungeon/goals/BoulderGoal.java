package dungeonmania.dungeon.goals;

import java.util.HashMap;
import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.util.Position;

public class BoulderGoal implements IGoal {
    private String type;

    public BoulderGoal() {
        this.type = "boulders";
    }

    public boolean checkGoal(Dungeon dungeon) {
        boolean switchesCovered = false;
        for (IEntity entity : dungeon.getEntities()) {
            if (entity.getInfo().getType().equals("switch")) {
                if (switchCoveredByBoulder(entity, dungeon)) {
                    switchesCovered = true;
                } else {
                    return false;
                }
                    
            }
        }
        return switchesCovered;
    }
    
    public boolean switchCoveredByBoulder(IEntity switchEntity, Dungeon dungeon) {
        for (IEntity entity : dungeon.getEntities()) {
            if (entity.getInfo().getType().equals("boulder") && entity.isInSamePositionAs(switchEntity)) {
                return true;
            }
        }
        return false;
    }

    public String getType() {
        return type;
    }
}
