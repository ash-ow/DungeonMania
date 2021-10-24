package dungeonmania.dungeon.goals;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IMovingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.BoulderEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderGoalTests implements IGoalTests {
    @Test
    public void SimpleGoalTest() {
        CharacterEntity player = new CharacterEntity(0, 4, "player");
        BoulderEntity boulder = new BoulderEntity(0, 3, 0, "boulder");
        SwitchEntity switchEntity = new SwitchEntity(0, 2, 0, "switch");
        ArrayList<IEntity> entities = new ArrayList<>();
        String jsonGoals = "{ \"goal\": \"boulders\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player, j);
        dungeon.tick(Direction.UP);
        assertEquals(boulder.getPosition(), switchEntity.getPosition());
        assertEquals("", dungeon.getGoals());
    }
}
