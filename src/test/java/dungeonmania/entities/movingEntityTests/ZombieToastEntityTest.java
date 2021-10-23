package dungeonmania.entities.movingEntityTests;

import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.IMovingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ZombieToastEntityTest implements IInteractingEntityTest, IMovingEntityTest, IEntityTests {
    @Override
    @Test
    public void TestInteraction() {
        ZombieToastEntity spider = new ZombieToastEntity();
        CharacterEntity character = new CharacterEntity(0, 1, "CharacterType");

        character.move(Direction.UP);
        assertEquals(spider.getPosition(), character.getPosition());
        
        // TODO This is only testing the stub in the ZombieToastEntity class - not the actual interaction between the two
        spider.interactWithPlayer(character); // TODO I think this should be run automatically when positions are equal
        assertEquals(new Position(0,1), character.getPosition());
    }

    @Override
    @Test
    public void TestMove() {
        // TODO The zombie toast is supposed to move randomly - this test does not reflect that
        ZombieToastEntity zombie = new ZombieToastEntity();
        assertPositionEquals(zombie.getPosition(), 0, 0);
        
        zombie.move(Direction.DOWN);
        assertPositionEquals(zombie.getPosition(), 0, 1);
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        ZombieToastEntity zombie = new ZombieToastEntity(0, 0, "ZombieToastType");
        assertEntityResponseInfoEquals(
            zombie,
            "zombieToast-0-0",
            "ZombieToastType",
            new Position(0,0),
            true
        );
    }
}