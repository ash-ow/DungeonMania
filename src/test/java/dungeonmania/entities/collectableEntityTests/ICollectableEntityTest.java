package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.movingEntities.CharacterEntity;

public interface ICollectableEntityTest extends IEntityTests {

    public void TestCollect();
    public void TestUseCollectable();

    public default void assertItemInInventory(String id, CharacterEntity player, EntitiesControl entitiesControl) {
        assertNotNull(player.getInventory().getInventoryItemById(id), "Inventory should contain entity " + id);
        assertNull(entitiesControl.getEntityById(id), "EntitiesControl should not contain entity " + id);
    }
    
    public default void assertItemNotInInventory(String id, CharacterEntity player, EntitiesControl entitiesControl, boolean shouldBePlacedInMap) {
        assertNull(player.getInventory().getInventoryItemById(id), "Inventory should not contain entity " + id);
        if (shouldBePlacedInMap) {
            assertNotNull(entitiesControl.getEntityById(id), "EntitiesControl should contain entity " + id);
        } else {
            assertNull(entitiesControl.getEntityById(id), "EntitiesControl should contain entity " + id);
        }
    }

    public default void assertEntityIsCollected(ICollectable entity) {
        CharacterEntity player = new CharacterEntity(0, 1);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntity(entity);
        assertItemNotInInventory(entity.getId(), player, entities, true);
        
        entity.contactWithPlayer(entities, player);
        assertItemInInventory(entity.getId(), player, entities);
    }

    public default void assertEntityIsUsedAndPlacedIfApplicable(ICollectable entity) {
        CharacterEntity player = new CharacterEntity(0, 1);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntity(entity);
        entity.contactWithPlayer(entities, player);
        player.useItem(entity.getId(), entities);
        assertItemNotInInventory(entity.getId(), player, entities, entity.isPlacedAfterUsing());
    }
}
