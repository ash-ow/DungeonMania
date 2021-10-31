package dungeonmania;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DungeonManiaController {
    private Dungeon dungeon;

    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("dungeonName does not exist");
        }
        if (!gameMode.equals("Standard") && !gameMode.equals("Peaceful") && !gameMode.equals("Hard")) {
            throw new IllegalArgumentException("invalid gameMode");
        }
        try {
            String dungeonJson = FileLoader.loadResourceFile("dungeons/" + dungeonName + ".json");
            JsonObject jsonObject = new Gson().fromJson(dungeonJson, JsonObject.class);
            String id = UUID.randomUUID().toString(); 
            JsonObject goalCondition = jsonObject.getAsJsonObject("goal-condition");
            this.dungeon = new Dungeon(jsonObject.get("entities").getAsJsonArray(), goalCondition , gameMode, id, dungeonName);           
            
        } catch (IOException e) {
        }
        return dungeon.getInfo();
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        return null;
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        return null;
    }

    public List<String> allGames() {
        return new ArrayList<>();
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        if (itemUsed != null) {
            dungeon.tick(itemUsed);
        }
        dungeon.tick(movementDirection);
        return dungeon.getInfo();
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        dungeon.interact(entityId);
        return dungeon.getInfo();
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        dungeon.build(buildable);
        return dungeon.getInfo();
    }
}