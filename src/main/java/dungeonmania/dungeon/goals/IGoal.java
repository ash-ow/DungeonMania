package dungeonmania.dungeon.goals;

import dungeonmania.dungeon.Dungeon;

public interface IGoal {
    public boolean checkGoal(Dungeon dungeon);
    public String getType();
    public String getFrontendString();
}
