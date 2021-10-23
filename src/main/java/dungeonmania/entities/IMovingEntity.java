package dungeonmania.entities;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface IMovingEntity extends IEntity {
    /**
     * Sets entity position. This field is readonly because we
     *  only want to initialise it in the constructor
     *  and change it by moving
     * @param position - the new position we move to
     */
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
