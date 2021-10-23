package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IMovingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntityTest implements IMovingEntityTest, IEntityTests {
    @Test
    public void TestMove() {
        IMovingEntity character = new CharacterEntity();
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
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
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
            false
        );
    }
}
