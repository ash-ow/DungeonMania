package dungeonmania.entities.buildableEntityTest;

import dungeonmania.entities.collectableEntityTests.ICollectableEntityTest;

public interface IBuildableEntityTests extends ICollectableEntityTest {
    public void TestIsBuildable();
    public void TestOnlyUsesResourcesFromOneRecipe();
    public void TestIsNotBuildable_EmptyInventory();
    public void TestIsNotBuildable_InventoryFullOfWrongItems();
    public void TestIsNotBuildable_InsufficientCorrectItems();
}
