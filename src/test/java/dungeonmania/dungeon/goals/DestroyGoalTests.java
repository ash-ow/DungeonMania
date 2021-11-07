package dungeonmania.dungeon.goals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;

public class DestroyGoalTests implements IGoalTests {
    @Test
    public void SimpleGoalTest() {
        String jsonGoals = "{ \"goal\": \"enemies\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        CharacterEntity player = new CharacterEntity(0, 1);
        SpiderEntity spider = new SpiderEntity(0, 2);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        assertEquals(":mercenary", dungeon.getGoals());
        dungeon.entitiesControl.removeEntity(spider);
        assertEquals("", dungeon.getGoals());
    }
}
