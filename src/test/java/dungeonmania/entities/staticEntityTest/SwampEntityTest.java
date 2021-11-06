package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IBlockerTest;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwampEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampEntityTest implements IEntityTests, IBlockerTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        SwampEntity swamp = new SwampEntity();
        assertEntityResponseInfoEquals(
            swamp,
            "swamp-0-0-0",
            "swamp",
            new Position(0,0,0),
            false
        );
    }

    @Test
    @Override
    public void TestBlock() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SwampEntity swamp = new SwampEntity(0, 1, 0);
        entities.add(swamp);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 0));
    }

    @Test
    @Override
    public void TestUnblock() {
        // Swamps cannot be unblocked
    }
}
