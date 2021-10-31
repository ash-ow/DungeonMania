package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.KeyEntity;

import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class KeyEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        KeyEntity key = new KeyEntity(0,0,0, 0);
        assertEntityResponseInfoEquals(
            key,
            "key-0-0-0",
            EntityTypes.KEY,
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestCollect() {
        KeyEntity key = new KeyEntity(0,0,0,0);
        assertEntityIsCollected(key);
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }
}