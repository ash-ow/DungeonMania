package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.util.Position;

public class ArmourEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        ArmourEntity armour = new ArmourEntity(0, 0, 0);
        assertEntityResponseInfoEquals(armour, "armour-0-0-0", EntityTypes.ARMOUR, new Position(0,0), false);
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }

    @Test
    @Override
    public void TestCollect() {
        ArmourEntity armour = new ArmourEntity(0,0,0);
        assertEntityIsCollected(armour);
    }
}
