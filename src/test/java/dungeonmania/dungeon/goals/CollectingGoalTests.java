package dungeonmania.dungeon.goals;

import org.junit.jupiter.api.Test;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CollectingGoalTests implements IGoalTests {
    @Override
    @Test
    public void SimpleGoalTest() {
        String jsonGoals = "{ \"goal\": \"treasure\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        TreasureEntity treasure = new TreasureEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(treasure);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getInventory().getEntities().size(), 1);
        assertEquals("", dungeon.getGoals());
    }
}
