package dungeonmania.dungeon.goals;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderGoalTests implements IGoalTests {
    @Override
    @Test
    public void SimpleGoalTest() {
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        BoulderEntity boulder = new BoulderEntity(0, 2, 0);
        SwitchEntity switchEntity = new SwitchEntity(0, 3, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switchEntity);
        String jsonGoals = "{ \"goal\": \"boulders\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        dungeon.tick(Direction.DOWN);
        assertEquals(boulder.getPosition(), switchEntity.getPosition());
        assertEquals("", dungeon.getGoals());
    }
}
