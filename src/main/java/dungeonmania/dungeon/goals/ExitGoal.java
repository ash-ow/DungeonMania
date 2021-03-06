package dungeonmania.dungeon.goals;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.staticEntities.ExitEntity;

public class ExitGoal implements IGoal {
    private String type;
    
    /**
     * ExitGoal constructor
     */
    public ExitGoal() {
        this.type = "exit";
    }

    /**
     * Evaluates whether goal has passed
     * @param dungeon  dungeon to be checked
     */
    public boolean checkGoal(Dungeon dungeon) {
        for (IEntity e : dungeon.getAllEntitiesOfType(ExitEntity.class)) {
            if (exitPlayerCheck(dungeon, e)) {
                return true;
            }
        }
        return false;
    }

    public boolean exitPlayerCheck(Dungeon dungeon, IEntity entity) {
        return dungeon.getPlayer().isInSamePositionAs(entity);
    }

    public String getType() {
        return type;
    }

    @Override
    public String getFrontendString() {
        return ":exit";
    }
}
