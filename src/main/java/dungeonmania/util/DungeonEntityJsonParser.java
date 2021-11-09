package dungeonmania.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;

public class DungeonEntityJsonParser {
    Position position;
    EntityTypes type;
    JsonElement info;

    public DungeonEntityJsonParser() {
        this.position = null;
        this.type = null;
        this.info = null;
    }

    public DungeonEntityJsonParser(JsonObject entityObj) {
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        this.position = new Position(xAxis, yAxis);
        this.type = EntityTypes.getEntityType(entityObj.get("type").getAsString());
        if (entityObj.get("key") != null) {
            this.info = entityObj.get("key");
        }
        if (entityObj.get("colour") != null) {
            this.info = entityObj.get("colour");
        }
    }

    public Integer getX() {
        return getPosition().getX();
    }

    public Integer getY() {
        return getPosition().getY();
    }

    public Position getPosition() {
        return position;
    }

    public EntityTypes getType() {
        return type;
    }

    public JsonElement getInfo() {
        return info;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setType(EntityTypes type) {
        this.type = type;
    }

    public void setInfo(JsonElement info) {
        this.info = info;
    }

    public boolean isPlayerJson() {
        return getType().equals(EntityTypes.PLAYER); 
    }
}
