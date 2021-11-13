package dungeonmania.dungeon.goals;

import org.junit.jupiter.api.Test;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.collectableEntities.SunStoneEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;

public class CollectingGoalTests implements IGoalTests {
    @Override
    @Test
    public void SimpleGoalTest() {
        String jsonGoals = "{ \"goal\": \"treasure\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        CharacterEntity player = new CharacterEntity(0, 1);
        TreasureEntity treasure = new TreasureEntity(0, 2);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(treasure);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getInventory().size(), 1);
        assertEquals("", dungeon.getGoals());
    }

    @Test
    public void SimpleGoalTestSunStone() {
        String jsonGoals = "{ \"goal\": \"treasure\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        CharacterEntity player = new CharacterEntity(0, 1);
        SunStoneEntity sun_stone = new SunStoneEntity(0, 2);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(sun_stone);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getInventory().size(), 1);
        assertEquals("", dungeon.getGoals());
    }

}
