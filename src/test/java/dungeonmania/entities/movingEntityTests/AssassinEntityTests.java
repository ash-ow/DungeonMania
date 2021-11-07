package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.AssassinEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class AssassinEntityTests {
    @Test
    public void testBribe() {
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
    public void testCantBribe() {
        CharacterEntity player = new CharacterEntity(0, 5);
        AssassinEntity assassin = new AssassinEntity(0, 4);
        TreasureEntity treasure = new TreasureEntity();
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(assassin);        
        player.addEntityToInventory(treasure); 
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.interact(assassin.getId());
        assertFalse(assassin.isBribed());
    }
}
