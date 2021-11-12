package dungeonmania.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;

public class DungeonEntityJsonObject {
    Position position;
    EntityTypes type;
    Integer key;
    String color;
    public JsonObject entityObj;

    public DungeonEntityJsonObject() {
        this.position = null;
        this.type = null;
        this.key = null;
        this.color = null;
    }

    public DungeonEntityJsonObject(JsonObject entityObj) {
        this.entityObj = entityObj;
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        this.position = new Position(xAxis, yAxis);
        this.type = EntityTypes.getEntityType(entityObj.get("type").getAsString());
        if (entityObj.get("key") != null) {
            this.key = entityObj.get("key").getAsInt();
        }
        if (entityObj.get("colour") != null) {
            this.color = entityObj.get("colour").getAsString();
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

    public Integer getKey() {
        return key;
    }

    public String getColor() {
        return color;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setType(EntityTypes type) {
        this.type = type;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isPlayerJson() {
        return getType().equals(EntityTypes.PLAYER); 
    }
}
