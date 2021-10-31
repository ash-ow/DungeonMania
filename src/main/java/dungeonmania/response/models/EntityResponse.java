package dungeonmania.response.models;

import dungeonmania.entities.EntityTypes;
import dungeonmania.util.Position;

public final class EntityResponse {
    private final String id;
    private final String type;
    private final Position position;
    private final boolean isInteractable;

    public EntityResponse(String id, EntityTypes type, Position position, boolean isInteractable) {
        this.id = id;
        this.type = type.toString();
        this.position = position;
        this.isInteractable = isInteractable;
    }

    public EntityResponse(String id, String type, Position position, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public final String getId() {
        return id;
    }

    public final String getType() {
        return type;
    }

    public final Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {return true;}
        if (!(o instanceof EntityResponse)) {return false;}
        EntityResponse c = (EntityResponse)o;
        return this.getId().equals(c.getId()) &&
            this.getType().equals(c.getType()) &&
            this.getPosition().equals(c.getPosition()) &&
            this.isInteractable() == c.isInteractable();
    }

    @Override
    public String toString() {
        var formatString = "EntityResponseInfo\n" +
            "---------------------------------\n" +
            "Id             | %s\n" +
            "Type           | %s\n" +
            "Position       | %s\n" +
            "isInteractable | %s";
        return String.format(formatString,
            this.getId(),
            this.getType(),
            this.getPosition(),
            this.isInteractable()
        );
    }
}
