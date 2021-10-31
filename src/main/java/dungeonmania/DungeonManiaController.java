package dungeonmania;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
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
import java.util.stream.Collectors;

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
        if (!getGameModes().contains(gameMode)) {
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
        dungeon.saveGame(name);
        return dungeon.getInfo();
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (!allGames().contains(name)) {
            throw new IllegalArgumentException("game doesn't exist");
        }
        try {
            String dungeonJson = FileLoader.loadResourceFile("savedGames/" + name + ".json");
            JsonObject jsonObject = new Gson().fromJson(dungeonJson, JsonObject.class);
            String id = UUID.randomUUID().toString(); 
            JsonObject goalCondition = jsonObject.getAsJsonObject("goal-condition");
            String gameMode = jsonObject.get("gameMode").getAsString();
            String dungeonName = jsonObject.get("dungeonName").getAsString();
            this.dungeon = new Dungeon(jsonObject.get("entities").getAsJsonArray(), goalCondition , gameMode, id, dungeonName);                  
        } catch (IOException e) {
        }
        dungeon.tick(Direction.NONE);
        return dungeon.getInfo();
    }

    public List<String> allGames() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/savedGames");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        // List<BuildableEntity> buildableEntities = dungeon.entitiesControl.getAllEntitiesOfType(BuildableEntity.class);
        // List<String> buildableEntityID = buildableEntities.stream().map(BuildableEntity::getId).collect(Collectors.toList());
        // if (!buildableEntityID.contains(itemUsed)) {
        //     throw new IllegalArgumentException();
        // }
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