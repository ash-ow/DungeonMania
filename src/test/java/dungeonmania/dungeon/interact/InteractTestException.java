package dungeonmania.dungeon.interact;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawnerEntity;
import dungeonmania.exceptions.InvalidActionException;

public class InteractTestException {
    @Test
    public void testInvalidId() {
        CharacterEntity player = new CharacterEntity(0, 5);
        SpiderEntity spider = new SpiderEntity(0, 4);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);        
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(IllegalArgumentException.class, () -> {dungeon.interact("random-id");});
    }

    @Test
    public void testInvalidType() {
        CharacterEntity player = new CharacterEntity(0, 5);
        SpiderEntity spider = new SpiderEntity(0, 4);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);        
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(IllegalArgumentException.class, () -> {dungeon.interact(spider.getId());});
    }

    @Test
    public void testInvalidBribeNoMoney() {
        CharacterEntity player = new CharacterEntity(0, 5);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(mercenary);        
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(mercenary.getId());});
    }

    @Test
    public void testInvalidBribeTooFar() {
        CharacterEntity player = new CharacterEntity(0, 5);
        MercenaryEntity mercenary = new MercenaryEntity(0, 2);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(mercenary);        
        player.getInventoryItems().add(new TreasureEntity()); 
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(mercenary.getId());});
    }

    @Test
    public void successfulBribe() {
        CharacterEntity player = new CharacterEntity(0, 5);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4);
        TreasureEntity treasure = new TreasureEntity();
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(mercenary);        
        player.getInventoryItems().add(treasure); 
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertDoesNotThrow(() -> {dungeon.interact(mercenary.getId());});
        assertTrue(mercenary.isBribed());
        assertFalse(player.getInventoryItems().contains(treasure));
    }

    @Test
    public void testInvalidDestroyTooFar() {
        CharacterEntity player = new CharacterEntity(0, 5);
        ZombieToastSpawnerEntity spawner = new ZombieToastSpawnerEntity(0, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spawner);        
        player.getInventoryItems().add(new SwordEntity());
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(spawner.getId());});
    }

    @Test
    public void testInvalidDestroyNoWeapon() {
        CharacterEntity player = new CharacterEntity(0, 5);
        ZombieToastSpawnerEntity spawner = new ZombieToastSpawnerEntity(0, 4);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spawner);        
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(spawner.getId());});
    }

    @Test
    public void testSuccessfulDestroy() {
        CharacterEntity player = new CharacterEntity(0, 5);
        ZombieToastSpawnerEntity spawner = new ZombieToastSpawnerEntity(0, 4);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spawner);    
        player.getInventoryItems().add(new SwordEntity());    
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        assertDoesNotThrow(() -> {dungeon.interact(spawner.getId());});
    }
}
