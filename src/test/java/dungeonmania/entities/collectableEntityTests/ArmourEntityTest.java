package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.util.Position;

public class ArmourEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        ArmourEntity armour = new ArmourEntity(0, 0);
        assertEntityResponseInfoEquals(armour, "armour-0-0-0", EntityTypes.ARMOUR, new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity player = new CharacterEntity();
        ArmourEntity armour = new ArmourEntity();
        armour.contactWithPlayer(new EntitiesControl(), player);

        assertEquals(4,  armour.getDurability());
        assertEquals(100, player.getHealth());
        assertEquals(50, zombie.getHealth());

        zombie.contactWithPlayer(new EntitiesControl(), player);
        assertEquals(3,  armour.getDurability(), "Armour should do two rounds of battle");
        assertFalse(zombie.isAlive());
    }

    @Test
    @Override
    public void TestCollect() {
        ArmourEntity armour = new ArmourEntity(0,0);
        assertEntityIsCollected(armour);
    }
}
