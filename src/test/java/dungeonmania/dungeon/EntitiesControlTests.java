package dungeonmania.dungeon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawnerEntity;
import dungeonmania.util.Direction;

public class EntitiesControlTests {
    @Test
    public void TestSpawnZombie() {
        CharacterEntity player = new CharacterEntity(0, 5);
        ZombieToastSpawnerEntity spawner = new ZombieToastSpawnerEntity(5, 5);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spawner);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertEquals(0, dungeon.getAllEntitiesOfType(ZombieToastEntity.class).size());

        for (int i = 0; i < 20; i++) {
            dungeon.tick(Direction.DOWN);
        }
        assertEquals(1, dungeon.getAllEntitiesOfType(ZombieToastEntity.class).size());
    }
}
