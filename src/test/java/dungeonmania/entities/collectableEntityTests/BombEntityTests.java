package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BombEntityTests implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        BombEntity bomb = new BombEntity(0, 0, 0);
        assertEntityResponseInfoEquals(bomb, "bomb-0-0-0", "bomb", new Position(0,0), false);
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }

    @Test
    @Override
    public void TestCollect() {
        BombEntity bomb = new BombEntity(0,0,0);
        assertEntityIsCollected(bomb);
    }
}
