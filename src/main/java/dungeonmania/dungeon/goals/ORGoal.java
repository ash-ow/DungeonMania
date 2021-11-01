package dungeonmania.dungeon.goals;

import java.util.List;

import dungeonmania.dungeon.Dungeon;

public class ORGoal implements IGoal{
    List<IGoal> subGoals;
    String type;

    /**
     * ORGoal constructor
     * @param subGoals  list of subGoals
     */
    public ORGoal(List<IGoal> subGoals) {
        this.subGoals = subGoals;
        this.type = "OR";
    }

    /**
     * Evaluates whether goal has passed
     * @param dungeon  dungeon to be checked
     */
    @Override
    public boolean checkGoal(Dungeon dungeon) {
        boolean check = false;
        for (IGoal goal : subGoals) {
            check |= goal.checkGoal(dungeon);
        }
        return check;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFrontendString() {
        String frontend = "";
        for (int i = 0; i < subGoals.size() - 1; i++) {
            frontend += subGoals.get(i).getFrontendString() + "/";
        }
        frontend += subGoals.get(subGoals.size() - 1).getFrontendString();
        return frontend;
    }
}
