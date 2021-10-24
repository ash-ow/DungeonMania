package dungeonmania.dungeon;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;

public class DungeonTests {
    @Test
    public void testEntitiesOverlap() {
        CharacterEntity player = new CharacterEntity(0, 4, 0);
        BoulderEntity boulder = new BoulderEntity(0, 5, 0);
        SwitchEntity switches = new SwitchEntity(0, 5, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switches);
        assertDoesNotThrow(() -> new Dungeon(20, 20, entities, "Standard", player));
    }
}
