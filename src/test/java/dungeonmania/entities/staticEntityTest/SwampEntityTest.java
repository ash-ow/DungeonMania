package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.staticEntities.SwampEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        SwampEntity swamp = new SwampEntity();
        assertEntityResponseInfoEquals(
            swamp,
            "swamp-0-0-0",
            EntityTypes.SWAMP,
            new Position(0,0,0),
            false
        );
    }

    @Test
    public void TestPlayerNotBlocked() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0);
        SwampEntity swamp = new SwampEntity(0, 1);
        entities.add(swamp);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 1));
        
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 2));
    }

    @Test
    public void TestMercenaryBlocked() {
        ArrayList<IEntity> entities = new ArrayList<>();

        // mercenary will try to move down to follow the player
        MercenaryEntity merc = new MercenaryEntity();
        entities.add(merc);
        SwampEntity swamp = new SwampEntity(0, 1);
        entities.add(swamp);
        CharacterEntity player = new CharacterEntity(0, 3);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);

        // mercenary can move down into the swamp and gets stuck for 3 ticks
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 1));
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 1));
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 1));
        
        // mercenary is finally able to move down to follow the player
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 2));
    }
}
