package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.AndurilEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.HydraEntity;
import dungeonmania.util.Position;

public class HydraEntityTests implements IMovingEntityTest, IBattlingEntityTest {

    @Test
    public void testBattleIncreaseHealth() {
        CharacterEntity character = new CharacterEntity();
        HydraEntity hydra = new HydraEntity();
        hydra.loseHealth(character.getHealth(), character.getDamage(), 420);

        assertEquals(90, hydra.getHealth());
    }

    @Test
    public void testBattleDecreaseHealth() {
        CharacterEntity character = new CharacterEntity();
        HydraEntity hydra = new HydraEntity();
        hydra.loseHealth(character.getHealth(), character.getDamage(), 4100);

        assertEquals(-30, hydra.getHealth());
    }

    @Override
    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        HydraEntity hydra = new HydraEntity();

        assertEquals(100, character.getHealth());
        assertEquals(30, hydra.getHealth());

        hydra.doBattle(character);

        // Change in health is tested here, since hydra can gain or lose health
        // loseHeath method is tested above
        assertNotEquals(100, character.getHealth());
        assertNotEquals(30, hydra.getHealth());
    }

    @Override
    @Test
    public void TestDeath() {
        // This also test anduril effectiveness, Hydra will die if character carries Anduril
        CharacterEntity character = new CharacterEntity();
        HydraEntity hydra = new HydraEntity();
        AndurilEntity anduril = new AndurilEntity();
        
        character.addEntityToInventory(anduril);

        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(hydra);

        hydra.battle(entitiesControl, character);

        assertFalse(entitiesControl.contains(hydra));        
    }

    @Override
    @Test
    public void testDropOneRing() {
        CharacterEntity player = new CharacterEntity();
        HydraEntity hydra = new HydraEntity();
        hydra.dropEntities(player, 1f);
        List<CollectableEntity> inventory = player.getInventory();
        assertNotNull(EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class));
    }

    @Override
    @Test
    public void TestMove() {
        CharacterEntity player = new CharacterEntity();
        HydraEntity hydra = new HydraEntity(5, 5, 10);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntity(hydra);

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(4, 4), new Position(5, 4), new Position(6, 4), 
            new Position(6, 5), new Position(6, 4));

        for (Position expectPosition : expectPositions) {
            hydra.move(entities, player);
            assertEquals(hydra.getPosition(), expectPosition);
        }
        
    }
}
