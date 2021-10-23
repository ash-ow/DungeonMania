package dungeonmania.entities;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.SpiderEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SpiderTest implements IInteractingEntityTest {
    @Override
    @Test
    public void TestInteraction() {
        SpiderEntity spider = new SpiderEntity();
        CharacterEntity character = new CharacterEntity(0, 1, "CharacterType");

        character.move(Direction.UP);
        assertEquals(spider.getPosition(), character.getPosition());
        
        // TODO This is only testing the stub in the SpiderEntity class - not the actual interaction between the two
        spider.interactWithPlayer(character); // TODO I think this should be run automatically when positions are equal
        assertEquals(new Position(0,1), character.getPosition());
    }
}