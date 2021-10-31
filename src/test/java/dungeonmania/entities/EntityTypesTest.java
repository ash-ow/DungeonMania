package dungeonmania.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EntityTypesTest {
    @Test
    public void TestCreateEntityType() {
        EntityTypes actual = EntityTypes.getEntityType("wall");
        EntityTypes expected = EntityTypes.WALL;
        assertEquals(expected, actual);
    }
}
