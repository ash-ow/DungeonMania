package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlockerTest;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class WallEntityTest implements IEntityTests, IBlockerTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        WallEntity wall = new WallEntity();
        assertEntityResponseInfoEquals(
            wall,
            "wall-0-0-0",
            EntityTypes.WALL,
            new Position(0,0,0),
            false
        );
    }

    @Test
    @Override
    public void TestBlock() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        WallEntity wall = new WallEntity(0, 1, 0);
        entities.add(wall);

        Dungeon dungeon = new Dungeon(entities, "standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 0));
    }

    @Test
    @Override
    public void TestUnblock() {
        // Walls cannot be unblocked
    }
}
