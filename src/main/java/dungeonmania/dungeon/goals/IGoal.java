package dungeonmania.dungeon.goals;

import java.util.HashMap;

import dungeonmania.dungeon.Dungeon;

/* to do:
    subscriber pattern called by Dungeon- goal logic occurs within each class based on information given by dungeon after move

*/
public interface IGoal {
    public boolean checkGoal(Dungeon dungeon);
    public String getType();
    public String getFrontendString();
}
