package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ArmourEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        ArmourEntity armour = new ArmourEntity(0, 0, 0);
        assertEntityResponseInfoEquals(armour, "armour-0-0-0", "armour", new Position(0,0), false);
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
