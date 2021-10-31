package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.util.Position;

public class SwitchEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        SwitchEntity switchEntity = new SwitchEntity();
        assertEntityResponseInfoEquals(
            switchEntity,
            "switch-0-0-0",
            "switch",
            new Position(0,0,0),
            false
        );
    }

    @Test
    public void BoulderPushToActive() {
        EntitiesControl entitiesControl = new EntitiesControl();
        BoulderEntity boulder = new BoulderEntity();
        SwitchEntity switchEntity = new SwitchEntity(0, 1, 0);
        entitiesControl.addEntity(boulder);
        entitiesControl.addEntity(switchEntity);
        assertFalse(switchEntity.isActive());
        boulder.setPosition(new Position(0, 1, 1));
        switchEntity.tick(entitiesControl);
        assertTrue(switchEntity.isActive());
    }
}
