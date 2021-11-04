package dungeonmania.dungeon.goals;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ExitGoalTests implements IGoalTests {
    @Override
    @Test
    public void SimpleGoalTest() {
        CharacterEntity player = new CharacterEntity(0, 4);
        ExitEntity exit = new ExitEntity(0, 3);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(exit);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        assertEquals(player.getPosition(), new Position(0, 4));
        dungeon.tick(Direction.UP);
        assertEquals("", dungeon.getGoals());
    }    
}
