package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private int height;
    private int width;
    private EntitiesControl entitiesControl;
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
        this.entitiesControl = new EntitiesControl();
        for (JsonElement entityInfo : entities) {
            JsonObject entityObj = entityInfo.getAsJsonObject();
            String type = entityObj.get("type").getAsString();
            Integer xAxis = entityObj.get("x").getAsInt();
            Integer yAxis = entityObj.get("y").getAsInt();
            Integer layer = this.entitiesControl.entitiesFromPosition(new Position(xAxis, yAxis)).size();
            if (type.equals("player")) {
                this.player = new CharacterEntity(xAxis, yAxis, layer);
            } else {
                this.entitiesControl.createEntity(xAxis, yAxis, layer, type);
            }
        }
        this.goals = new Goals(goalConditions);
    }

    public Dungeon(int height, int width, ArrayList<IEntity> entities, String gameMode, CharacterEntity player) {
        this.height = height;
        this.width = width;
        this.entitiesControl = new EntitiesControl();
        this.entitiesControl.setEntities(entities);
        this.gameMode = gameMode;
        this.player = player;
    }

    public Dungeon(int height, int width, ArrayList<IEntity> entities, String gameMode, CharacterEntity player, JsonObject goalConditions) {
        this.height = height;
        this.width = width;
        this.entitiesControl = new EntitiesControl();
        this.entitiesControl.setEntities(entities);
        this.gameMode = gameMode;
        this.player = player;
        this.goals = new Goals(goalConditions);
    }

    public DungeonResponse getInfo() {
        List<EntityResponse> entitiesInfo = new ArrayList<>();
        for (IEntity entity : entitiesControl.getEntities()) {
            entitiesInfo.add(entity.getInfo());
        }
        entitiesInfo.add(player.getInfo());
        return new DungeonResponse(id, dungeonName, entitiesInfo, player.getInventoryInfo(), new ArrayList<>(), getGoals());
    }

    public void tick(Direction direction) {
        player.move(direction, entitiesControl);
    }

    public String getGoals() {
        return goals.checkGoals(this);
    }

    public CharacterEntity getPlayer() {
        return this.player;
    }

    public List<IEntity> getEntities(String type) {
        return this.entitiesControl.entitiesOfSameType(type);
    }
}
 