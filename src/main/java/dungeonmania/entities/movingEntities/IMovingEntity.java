package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface IMovingEntity extends IEntity {
    void setPosition(Position position);
    void setLastMovedDirection(Direction direction);
    Direction getLastMovedDirection();

    /**
     * Moves the entity by the direction offset (e.g. Direction.UP)
     * @param direction
     */
    public default void move(Direction direction, EntitiesControl entities) {
        this.setPosition(
            this.getPosition().translateBy(direction)
        );
        this.setLastMovedDirection(direction);
    }

    public default void move(Direction direction) {
        this.setPosition(
            this.getPosition().translateBy(direction)
        );
        this.setLastMovedDirection(direction);
    }


    public default void move(Direction direction, EntitiesControl entities, CharacterEntity player) {
        this.setPosition(
            this.getPosition().translateBy(direction)
        );
        this.setLastMovedDirection(direction);
    }

    public default void move(Direction direction, int layer) {
        this.setPosition(
            this.getPosition().translateBy(direction).asLayer(layer)
        );
        this.setLastMovedDirection(direction);
    }
}
