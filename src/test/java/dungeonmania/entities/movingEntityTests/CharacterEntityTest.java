package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IMovingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntityTest implements IMovingEntityTest, IEntityTests, IBattlingEntityTest {
    @Test
    public void TestMove() {
        CharacterEntity character = new CharacterEntity();
        assertPositionEquals(character.getPosition(), 0, 0);
        
        character.move(Direction.DOWN);
        assertPositionEquals(character.getPosition(), 0, 1);
        
        character.move(Direction.UP);
        assertPositionEquals(character.getPosition(), 0, 0);
        
        character.move(Direction.LEFT);
        assertPositionEquals(character.getPosition(), -1, 0);
        
        character.move(Direction.RIGHT);
        assertPositionEquals(character.getPosition(), 0, 0);
    }

    @Test
    public void testSimpleRestriction() {
        CharacterEntity player = new CharacterEntity(0, 4, "player");
        WallEntity wall = new WallEntity(0, 3, 0, "wall");
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(wall);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player, j);
        assertEquals(player.getPosition(), new Position(0, 4));
        dungeon.tick(Direction.UP);
        assertEquals(player.getPosition(), new Position(0, 4));
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        CharacterEntity player = new CharacterEntity(0, 4, "player");
        assertEntityResponseInfoEquals(
            player,
            "character-0-4",
            "player",
            new Position(0,4),
            true
        );
    }

    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        CharacterEntity character2 = new CharacterEntity();

        assertEquals(100, character.getHealth());
        assertEquals(100, character2.getHealth());

        character2.Battle(character);

        assertEquals(100, character.getHealth());
        assertEquals(100, character2.getHealth());
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        
        character.setHealth(2);
        zombie.Battle(character);

        assertEquals(-28, character.getHealth());
        assertEquals(98.8, zombie.getHealth(), 0.1);

        // TODO add assertions for character death
    }
}
