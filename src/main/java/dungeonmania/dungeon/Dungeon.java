package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.*;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.response.models.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private int height;
    private int width;
    private ArrayList<IEntity> entities;
    private String gameMode;
    private String id;
    private String dungeonName;
    private CharacterEntity player;
    private Goals goals;

    public Dungeon(int height, int width, JsonArray entities, JsonObject goalConditions, String gameMode, String id, String dungeonName) {
        this.height = height;
        this.width = width;
        this.gameMode = gameMode;
        this.id = id;
        this.dungeonName = dungeonName;
        this.entities = new ArrayList<>();
        for (JsonElement entityInfo : entities) {
            JsonObject entityObj = entityInfo.getAsJsonObject();
            String type = entityObj.get("type").getAsString();
            switch (type) {
                case "wall":
                    this.entities.add(new WallEntity(entityObj.get("x").getAsInt(), entityObj.get("y").getAsInt(), 0, type));
                    break;
                case "exit":
                    this.entities.add(new ExitEntity(entityObj.get("x").getAsInt(), entityObj.get("y").getAsInt(), 0, type));
                    break;
                case "door":
                    this.entities.add(new DoorEntity(entityObj.get("x").getAsInt(), entityObj.get("y").getAsInt(), 0, type));
                    break;
                case "portal":
                    this.entities.add(new PortalEntity(entityObj.get("x").getAsInt(), entityObj.get("y").getAsInt(), 0, type));
                    break;
                case "switch":
                    this.entities.add(new SwitchEntity(entityObj.get("x").getAsInt(), entityObj.get("y").getAsInt(), 0, type));
                    break;
                case "boulder":
                    this.entities.add(new BoulderEntity(entityObj.get("x").getAsInt(), entityObj.get("y").getAsInt(), 1, type));
                    break;
                case "player":
                    this.player = new CharacterEntity(entityObj.get("x").getAsInt(), entityObj.get("y").getAsInt(), type);
                    break;
            }
        }
        this.goals = new Goals(goalConditions);
    }

    public Dungeon(int height, int width, ArrayList<IEntity> entities, String gameMode, CharacterEntity player) {
        this.height = height;
        this.width = width;
        this.entities = entities;
        this.gameMode = gameMode;
        this.player = player;
    }

    public DungeonResponse getInfo() {
        List<EntityResponse> entitiesInfo = new ArrayList<>();
        for (IEntity entity : entities) {
            entitiesInfo.add(entity.getInfo());
        }
        entitiesInfo.add(player.getInfo());
        return new DungeonResponse(id, dungeonName, entitiesInfo, new ArrayList<>(), new ArrayList<>(), "");
    }

    public void tick(Direction direction) {
        Position target = player.getPosition().translateBy(direction);
        for (IEntity e : entities) {
            if (e.getPosition().equals(target) && !e.passable()) {
                return;
            }
        }
        player.move(direction);
    }
}
