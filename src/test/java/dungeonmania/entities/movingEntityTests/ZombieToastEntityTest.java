package dungeonmania.entities.movingEntityTests;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.entities.staticEntityTest.WallEntityTest;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ZombieToastEntityTest implements IInteractingEntityTest, IMovingEntityTest, IEntityTests, IBattlingEntityTest {
    @Override
    @Test
    public void TestInteraction() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity character = new CharacterEntity(0, 1, 0);

        assertEquals(100, character.getHealth());
        assertEquals(100, zombie.getHealth());

        character.move(Direction.UP);
        assertEquals(zombie.getPosition(), character.getPosition());

        zombie.contactWithPlayer(new EntitiesControl(), character);
        assertEquals(58, character.getHealth());
        assertFalse(zombie.isAlive());
    }

    @Override
    @Test
    public void TestMove() {
        CharacterEntity player = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity(5, 5, 0, 1f, 10);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntities(zombie);

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(4, 4), new Position(5, 4), new Position(6, 4), 
            new Position(6, 5), new Position(6, 4));

        for (Position expectPosition : expectPositions) {
            zombie.move(entities, player);
            assertEquals(zombie.getPosition(), expectPosition);
        }
    }

    @Test
    public void TestBlockMove() {
        CharacterEntity player = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity(5, 5, 0, 1f, 10);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntities(zombie);
        entities.createEntity(6, 4, 0, "wall");

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(4, 4), new Position(5, 4), new Position(5, 4));

        for (Position expectPosition : expectPositions) {
            zombie.move(entities, player);
            assertEquals(zombie.getPosition(), expectPosition);
        }
    }

    @Test
    public void testDropArmour() {
        CharacterEntity player = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity(0, 0, 0, 1f, 10);
        zombie.dropEntities(player, 1f);
        List<ICollectableEntity> inventory = player.getInventory();
        assertTrue(inventory.size() == 1);
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        ZombieToastEntity zombie = new ZombieToastEntity(0, 0, 0);
        assertEntityResponseInfoEquals(
            zombie,
            "zombie_toast-0-0-0",
            "zombie_toast",
            new Position(0,0),
            false
        );
    }

    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();

        assertEquals(100, character.getHealth());
        assertEquals(100, zombie.getHealth());

        zombie.doBattle(character);

        assertEquals(70, character.getHealth());
        assertEquals(40, zombie.getHealth());
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(zombie);
        zombie.battle(entitiesControl, character);
        assertFalse(entitiesControl.contains(zombie));
    }
}
