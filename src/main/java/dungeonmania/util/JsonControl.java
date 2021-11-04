package dungeonmania.util;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;

public class JsonControl {
    Position position;
    EntityTypes type;
    Integer key;
    String color;

    public JsonControl() {
        this.position = null;
        this.type = null;
        this.key = null;
        this.color = null;
    }

    public JsonControl(JsonObject entityObj) {
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        this.position = new Position(xAxis, yAxis);
        this.type = EntityTypes.getEntityType(entityObj.get("type").getAsString());
        this.key = entityObj.get("key").getAsInt();
        this.color = entityObj.get("colour").getAsString();
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

    public static Position JsonToPosition(JsonObject entityObj) {
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        return new Position(xAxis, yAxis);
    }
}
