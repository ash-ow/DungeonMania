package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderEntityTests implements IMovingEntityTest, IEntityTests {
    @Test
    public void testBoulderMove() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player, j);
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 2, 0), boulder.getPosition());
    }

    @Override
    public void TestEntityResponseInfo() {
        BoulderEntity boulder = new BoulderEntity(0, 0, 0);
        assertEntityResponseInfoEquals(
            boulder,
            "boulder-0-0-0",
            "boulder",
            new Position(0,0),
            false
        );
        

    @Override
    public void TestMove() {
        // TODO Auto-generated method stub
        
    }
}
