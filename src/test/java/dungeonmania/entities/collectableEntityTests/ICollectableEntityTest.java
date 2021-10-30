package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;

public interface ICollectableEntityTest extends IEntityTests {

    public void TestCollect();
    public void TestUseCollectable();
    
    public default void assertItemInInventory(String id, CharacterEntity player, EntitiesControl entitiesControl) {
        assertNotNull(player.getInventory().getEntityById(id), "Inventory should contain entity " + id);
        assertNull(entitiesControl.getEntityById(id), "EntitiesControl should not contain entity " + id);
    }
    
    public default void assertItemNotInInventory(String id, CharacterEntity player, EntitiesControl entitiesControl) {
        assertNull(player.getInventory().getEntityById(id), "Inventory should not contain entity " + id);
        assertNotNull(entitiesControl.getEntityById(id), "EntitiesControl should contain entity " + id);
    }

    public default void assertEntityIsCollected(ICollectableEntity entity) {
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(entity);
        assertItemNotInInventory(entity.getId(), player, entities);
        
        entity.interactWithPlayer(entities, Direction.UP, player);
        assertItemInInventory(entity.getId(), player, entities);
    }

    public default void assertEntityIsUsed(ICollectableEntity entity) {
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntities(entity);
        entity.interactWithPlayer(entities, Direction.UP, player);
        entity.used(player);
        assertNull(player.getInventory().getEntityById(entity.getId()), "Inventory should not contain entity " + entity.getId());
    }

}
