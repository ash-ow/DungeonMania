package dungeonmania.dungeon.goals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DestroyGoalTests implements IGoalTests {
    @Test
    public void SimpleGoalTest() {
        String jsonGoals = "{ \"goal\": \"enemies\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        SpiderEntity spider = new SpiderEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        assertEquals(":mercenary", dungeon.getGoals());
        dungeon.removeEntity(spider);
        assertEquals("", dungeon.getGoals());
    }
}
