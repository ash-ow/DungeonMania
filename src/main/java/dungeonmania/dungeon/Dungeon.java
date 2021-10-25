package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.*;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.*;
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
            List<IEntity> entitiesInPosition = this.entitiesControl.entitiesFromPosition(new Position(xAxis, yAxis));
            Integer layer = entitiesInPosition.size();

            switch (type) {
                case "wall":
                    this.entitiesControl.addEntities(new WallEntity(xAxis, yAxis, layer, type));
                    break;
                case "exit":
                    this.entitiesControl.addEntities(new ExitEntity(xAxis, yAxis, layer, type));
                    break;
                case "door":
                    this.entitiesControl.addEntities(new DoorEntity(xAxis, yAxis, layer, type));
                    break;
                case "portal":
                    this.entitiesControl.addEntities(new PortalEntity(xAxis, yAxis, layer, type));
                    break;
                case "switch":
                    this.entitiesControl.addEntities(new SwitchEntity(xAxis, yAxis, layer, type));
                    break;
                case "boulder":
                    this.entitiesControl.addEntities(new BoulderEntity(xAxis, yAxis, layer, type));
                    break;
                case "player":
                    this.player = new CharacterEntity(xAxis, yAxis, type);
                    break;
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
        return new DungeonResponse(id, dungeonName, entitiesInfo, new ArrayList<>(), new ArrayList<>(), getGoals());
    }

    public void tick(Direction direction) {
        Position target = player.getPosition().translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.entitiesFromPosition(target);
        IInteractingEntity boulder = (IInteractingEntity) EntitiesControl.entitiesContainsType(targetEntities, BoulderEntity.class);

        if (boulder != null) {
            if (boulder.interactWithPlayer(entitiesControl, direction)) {
                player.move(direction);
                return;
            }
        }

        if ((targetEntities.size() == 0) || !EntitiesControl.entitiesUnpassable(targetEntities)) {
            player.move(direction);
        }
    }

    public String getGoals() {
        return goals.checkGoals(this);
    }

    public CharacterEntity getPlayer() {
        return this.player;
    }

    public List<IEntity> getEntities() {
        return this.entitiesControl.getEntities();
    }
}
 