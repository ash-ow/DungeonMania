package dungeonmania.dungeon.interact;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.exceptions.InvalidActionException;

public class InteractTestException {
    @Test
    public void testInvalidId() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        SpiderEntity spider = new SpiderEntity(0, 4, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);        
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(IllegalArgumentException.class, () -> {dungeon.interact("random-id");});
    }

    @Test
    public void testInvalidType() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        SpiderEntity spider = new SpiderEntity(0, 4, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);        
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(spider.getId());});
    }

    @Test
    public void testInvalidBribeNoMoney() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(mercenary);        
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(mercenary.getId());});
    }

    @Test
    public void testInvalidBribeTooFar() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(mercenary);        
        player.addEntityToInventory(new TreasureEntity()); 
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(mercenary.getId());});
    }

    @Test
    public void successfulBribe() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(mercenary);        
        player.addEntityToInventory(new TreasureEntity()); 
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertDoesNotThrow(() -> {dungeon.interact(mercenary.getId());});
        assertTrue(mercenary.isBribed());
    }
}
