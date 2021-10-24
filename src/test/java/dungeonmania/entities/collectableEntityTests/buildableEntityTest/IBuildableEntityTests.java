package dungeonmania.entities.collectableEntityTests.buildableEntityTest;

public interface IBuildableEntityTests {
    public void TestIsBuildable();
    public void TestIsNotBuildable_EmptyInventory();
    public void TestIsNotBuildable_InventoryFullOfWrongItems();
    public void TestIsNotBuildable_InsufficientCorrectItems();
}
