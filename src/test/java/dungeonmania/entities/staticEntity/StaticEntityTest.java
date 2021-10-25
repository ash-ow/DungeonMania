package dungeonmania.entities.staticEntity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import com.google.gson.*;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import org.junit.jupiter.api.Test;

public class StaticEntityTest {
    @Test
    public void testEntitiesOverlap() {
        CharacterEntity player = new CharacterEntity(0, 4, "player");
        BoulderEntity boulder = new BoulderEntity(0, 5, 0, "boulder");
        SwitchEntity switches = new SwitchEntity(0, 5, 0, "switches");
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switches);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        assertDoesNotThrow(() -> new Dungeon(20, 20, entities, "Standard", player, j));
    }
}
