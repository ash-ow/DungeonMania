package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.AndurilEntity;
import dungeonmania.entities.movingEntities.AssassinEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.util.Position;

public class AndurilEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        AndurilEntity anduril = new AndurilEntity();
        assertEntityResponseInfoEquals(anduril, "anduril-0-0-0", EntityTypes.ANDURIL, new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity player = new CharacterEntity();
        AndurilEntity anduril = new AndurilEntity();
        anduril.contactWithPlayer(new EntitiesControl(), player);

        assertEquals(Integer.MAX_VALUE,  anduril.getDurability());
        assertEquals(100, player.getHealth());
        assertEquals(50, zombie.getHealth());

        zombie.contactWithPlayer(new EntitiesControl(), player);
        assertEquals(85, player.getHealth());
        assertEquals(Integer.MAX_VALUE - 1,  anduril.getDurability());
        assertFalse(zombie.isAlive());
    }

    @Test
    public void TestUseAndurilAgainstNormalEnemy() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity player = new CharacterEntity();
        AndurilEntity anduril = new AndurilEntity();
        player.addEntityToInventory(anduril);
        
        assertEquals(200, anduril.attack(zombie, player));
    }

    @Test
    public void TestUseAndurilAgainstBoss() {
        AssassinEntity assassin = new AssassinEntity();
        CharacterEntity player = new CharacterEntity();
        AndurilEntity anduril = new AndurilEntity();
        player.addEntityToInventory(anduril);
        
        assertEquals(600, anduril.attack(assassin, player));
    }

    @Test
    @Override
    public void TestCollect() {
        AndurilEntity anduril = new AndurilEntity();
        assertEntityIsCollected(anduril);
    }
}
