package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
            Integer xAxis = entityObj.get("x").getAsInt();
            Integer yAxis = entityObj.get("y").getAsInt();
            Integer layer = 0;
            if (entityFromPosition(new Position(xAxis, yAxis)) != null) {
                layer = 1;
            }
            switch (type) {
                case "wall":
                    this.entities.add(new WallEntity(xAxis, yAxis, layer, type));
                    break;
                case "exit":
                    this.entities.add(new ExitEntity(xAxis, yAxis, layer, type));
                    break;
                case "door":
                    this.entities.add(new DoorEntity(xAxis, yAxis, layer, type));
                    break;
                case "portal":
                    this.entities.add(new PortalEntity(xAxis, yAxis, layer, type));
                    break;
                case "switch":
                    this.entities.add(new SwitchEntity(xAxis, yAxis, layer, type));
                    break;
                case "boulder":
                    this.entities.add(new BoulderEntity(xAxis, yAxis, layer, type));
                    break;
                case "player":
                    this.player = new CharacterEntity(xAxis, yAxis, type);
                    break;
            }
        }
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
        IEntity targetEntity = entityFromPosition(target);
        
        if ((targetEntity != null) && targetEntity.getClass().toString().contains("Boulder")) {
            Position boulderTarget = targetEntity.getPosition().translateBy(direction);
            if ((entityFromPosition(boulderTarget) == null) || entityFromPosition(boulderTarget).passable()) {
                BoulderEntity boulder = (BoulderEntity) targetEntity;
                boulder.setPosition(boulderTarget);
            }
        }

        targetEntity = entityFromPosition(target);

        if ((targetEntity == null) || targetEntity.passable()) {
            player.move(direction);
        }
    }

    private IEntity entityFromPosition(Position position) {
        for (IEntity e : entities) {
            if (e.getPosition().equals(position)) {
                return e;
            }
        }
        return null;
    }
}
 