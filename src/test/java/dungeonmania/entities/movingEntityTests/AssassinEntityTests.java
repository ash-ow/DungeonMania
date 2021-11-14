package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.collectableEntities.SunStoneEntity;
import dungeonmania.entities.movingEntities.AssassinEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class AssassinEntityTests extends MercenaryEntityTests {
    @Test
    public void testBribeTreasure() {
        CharacterEntity player = new CharacterEntity(0, 5);
        AssassinEntity assassin = new AssassinEntity(0, 4);
        TreasureEntity treasure = new TreasureEntity();
        OneRingEntity oneRing = new OneRingEntity();
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(assassin);        
        player.addEntityToInventory(treasure); 
        player.addEntityToInventory(oneRing);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.interact(assassin.getId());
        assertTrue(assassin.isBribed());
    }

    @Test
    //Test if assassin bribed by sun_stone & check that item is not removed
    public void testBribeSunStone() {
        CharacterEntity player = new CharacterEntity(0, 5);
        AssassinEntity assassin = new AssassinEntity(0, 4);
        SunStoneEntity sun_stone = new SunStoneEntity();
        OneRingEntity oneRing = new OneRingEntity();
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(assassin);        
        player.addEntityToInventory(sun_stone); 
        player.addEntityToInventory(oneRing);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.interact(assassin.getId());
        assertTrue(assassin.isBribed());
        assertNotNull(player.getInventoryItem(sun_stone.getId()), "Inventory should contain entity " + sun_stone.getId());
    }

    @Test
    public void testCantBribe() {
        CharacterEntity player = new CharacterEntity(0, 5);
        AssassinEntity assassin = new AssassinEntity(0, 4);
        TreasureEntity treasure = new TreasureEntity();
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(assassin);        
        player.addEntityToInventory(treasure); 
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> dungeon.interact(assassin.getId()));
    }

    @Override
    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        AssassinEntity assassin = new AssassinEntity();

        assertEquals(100, character.getHealth());
        assertEquals(80, assassin.getHealth());

        assassin.doBattle(character);

        assertEquals(60, character.getHealth());
        assertEquals(20.0, assassin.getHealth());
    }

    @Test
    public void moveAgainAfterAttack() {
        EntitiesControl entitiesControl = new EntitiesControl();
        AssassinEntity assassin = new AssassinEntity();
        entitiesControl.addEntity(assassin);
        CharacterEntity player = new CharacterEntity(0, 5);
        entitiesControl.addEntity(player);

        entitiesControl.moveMercenariesAfterAttack(player);
        assertEquals(new Position(0, 1), assassin.getPosition());
    }
}
