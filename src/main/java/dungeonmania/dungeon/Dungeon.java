package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private int height;
    private int width;
    public EntitiesControl entitiesControl;
    private String gameMode;
    private String id;
    private String dungeonName;
    private CharacterEntity player;
    private Goals goals;

    /**
     * Main Dungeon Constructor if goalConditions exist
     * @param type
     * @return
     */
    public Dungeon(JsonArray entities, JsonObject goalConditions, String gameMode, String id, String dungeonName) {
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
                this.entitiesControl.createEntity(entityObj);
            }
        }
        if (goalConditions != null) {
            this.goals = new Goals(goalConditions);
        }
    }

    public Dungeon(ArrayList<IEntity> entities, String gameMode, CharacterEntity player) {
        this.entitiesControl = new EntitiesControl();
        this.entitiesControl.setEntities(entities);
        this.gameMode = gameMode;
        this.player = player;
    }

    public Dungeon(ArrayList<IEntity> entities, String gameMode, CharacterEntity player, JsonObject goalConditions) {
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
        entitiesControl.moveAllMovingEntities(direction, player);
        entitiesControl.tick();
        entitiesControl.generateEnemyEntities();
    }

    public String getGoals() {
        if (goals == null) {
            return "";
        }
        return goals.checkGoals(this);
    }

    public CharacterEntity getPlayer() {
        return this.player;
    }

    public List<IEntity> getEntities(String type) {
        return this.entitiesControl.getAllEntitiesOfType(type);
    }
}
 