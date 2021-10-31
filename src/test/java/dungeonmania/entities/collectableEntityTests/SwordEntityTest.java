package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;

public class SwordEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        SwordEntity sword = new SwordEntity(0, 0, 0);
        assertEntityResponseInfoEquals(sword, "sword-0-0-0", EntityTypes.SWORD, new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        // TODO see armour tests 
    }

    @Test
    @Override
    public void TestCollect() {
        SwordEntity sword = new SwordEntity(0,0,0);
        assertEntityIsCollected(sword);
    }
}
