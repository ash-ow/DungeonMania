package dungeonmania.dungeon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawnerEntity;
import dungeonmania.util.Direction;

public class EntitiesControlTests {
    @Test
    public void TestSpawnZombie() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        ZombieToastSpawnerEntity spawner = new ZombieToastSpawnerEntity(5, 5, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spawner);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.tick(Direction.DOWN);

        assertNotNull(dungeon.getEntities("zombie_toast"));

        for (int i = 0; i < 10; i++) {
            dungeon.tick(Direction.DOWN);
        }
        assertEquals(3, dungeon.getEntities("zombie_toast").size());
    }
}
