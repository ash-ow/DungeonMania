package dungeonmania.entities.movingEntities;

import dungeonmania.entities.IEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface IMovingEntity extends IEntity {
    void setPosition(Position position);

    /**
     * Moves the entity by the direction offset (e.g. Direction.UP)
     * @param direction
     */
    public default void move(Direction direction) {
        this.setPosition(
            this.getPosition().translateBy(direction)
        );
    }
}
