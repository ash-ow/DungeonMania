package dungeonmania.dungeon.goals;

import java.util.List;

import dungeonmania.dungeon.Dungeon;

public class ANDGoal implements IGoal{
    List<IGoal> subGoals;
    String type;

    public ANDGoal(List<IGoal> subGoals) {
        this.subGoals = subGoals;
        this.type = "AND";
    }

    @Override
    public boolean checkGoal(Dungeon dungeon) {
        boolean check = true;
        for (IGoal goal : subGoals) {
            check &= goal.checkGoal(dungeon);
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
            frontend += subGoals.get(i).getFrontendString() + " AND ";
        }
        frontend += subGoals.get(subGoals.size() - 1).getFrontendString();
        return frontend;
    }
    
}
