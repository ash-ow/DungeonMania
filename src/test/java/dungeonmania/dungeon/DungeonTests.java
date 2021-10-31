package dungeonmania.dungeon;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class DungeonTests {
    @Test
    public void testEntitiesOverlap() {
        CharacterEntity player = new CharacterEntity(0, 4, 0);
        BoulderEntity boulder = new BoulderEntity(0, 5, 0);
        SwitchEntity switches = new SwitchEntity(0, 5, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switches);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        assertDoesNotThrow(() -> new Dungeon(entities, "Standard", player, j));
    }
}
