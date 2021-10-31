package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntity;
import dungeonmania.dungeon.*;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvincibilityPotionEntity;
import dungeonmania.entities.movingEntities.spiderEntity.*;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.EntityTypes;

public class InvincibilityPotionEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            invincibility_potion,
            "invincibility_potion-0-0-0",
            EntityTypes.INVINCIBILITY_POTION,
            new Position(0,0),
            false
        );
    }

    @Test
    @Override
    public void TestCollect() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0, 0, 0);
        assertEntityIsCollected(invincibility_potion);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0, 1, 0);
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(invincibility_potion);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        
        dungeon.tick(Direction.DOWN);
        assertItemInInventory("invincibility_potion-0-1-0", player, dungeon.entitiesControl);
        assertEquals(new Position(0, 1, 0), player.getPosition());

        dungeon.tick("invincibility_potion-0-1-0");
        invincibility_potion.used(player);
        assertTrue(player.isInvincible());
    }

    @Test
    public void TestWinBattle() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0, 0, 0);
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SpiderEntity spider = new SpiderEntity(0, 1, 0);
        ArrayList<IEntity> entities = new ArrayList<>();

        entities.add(spider);
        entities.add(invincibility_potion);

        invincibility_potion.used(player);
        player.move(Direction.DOWN);
        spider.doBattle(player);
        //Player receives no damage while invincible, enemy should be dead
        assertEquals(100, player.getHealth());
        assertEquals(0, spider.getHealth());
        assertFalse(spider.isAlive());
    }

    @Test
    public void TestInvincibilityRunsOut() {
        ArrayList<IEntity> entities = new ArrayList<>();
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,1,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        entities.add(invincibility_potion);
        
        dungeon.tick(Direction.DOWN);
        assertItemInInventory("invincibility_potion-0-1-0", player, dungeon.entitiesControl);
        assertEquals(new Position(0, 1, 0), player.getPosition());

        invincibility_potion.used(player);
        assertTrue(player.isInvincible());
            
        // Player moves down 10 times
        for (int i = 0; i < 9;i++) {
            dungeon.tick(Direction.DOWN);
        }
        //to do check 
        assertFalse(player.isInvincible());
        assertItemInInventory("invincibility_potion-0-1-0", player, dungeon.entitiesControl);
    }

}