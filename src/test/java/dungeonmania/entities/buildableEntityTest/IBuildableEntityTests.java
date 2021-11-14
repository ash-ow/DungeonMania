package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntityTests.ICollectableEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;

public interface IBuildableEntityTests extends ICollectableEntityTest {
    public void TestIsBuildable();
    public void TestOnlyUsesResourcesFromOneRecipe();
    public void TestIsNotBuildable_EmptyInventory();
    public void TestIsNotBuildable_InventoryFullOfWrongItems();
    public void TestIsNotBuildable_InsufficientCorrectItems();

    public default void assertInventoryTypesAsExpected(List<EntityTypes> expectedInventory, CharacterEntity player) {
        List<EntityTypes> actualInventory = player.getInventoryItems().stream().map(item -> item.getType()).collect(Collectors.toList());
        assertIterableEquals(expectedInventory, actualInventory);
    }
}
