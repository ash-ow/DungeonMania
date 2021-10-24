package dungeonmania.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public interface IEntityTests {
    public void TestEntityResponseInfo();

    public default void assertEntityResponseInfoEquals(Entity actualEntity, String id, String type, Position position, boolean isInteractable) {
        EntityResponse expected = new EntityResponse(id, type, position, isInteractable);
        EntityResponse actual = actualEntity.getInfo();
        assertEquals(
            expected, actual,
            "The following EntityResponses were not equal\n\n" +
            "Expected:\n" + expected.toString() +
            "\nActual:\n" + actual.toString()
        );
    }
}