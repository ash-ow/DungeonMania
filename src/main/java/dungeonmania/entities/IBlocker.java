package dungeonmania.entities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;

public interface IBlocker {
    /**
     * @return true if the blocker is still preventing the IMovingEntity from entering its position
     */
    public boolean isBlocking();
    void setIsBlocking(boolean isBlocking);

    /**
     * Will move the entity if the IBlocker is not blocking
     * @param ent
     * @param direction
     */
    default boolean tryMove(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        if (this.tryUnblockIfNeeded(ent, direction, entitiesControl)) {
            ent.move(direction);
            return true;
        }
        return false;
    }

    /**
     * Sets isBlocking = false if successfully unblocks
     * @param ent 
     * @return true if IBlocker is already unblocked or if successfully unblocked
     */
    default boolean tryUnblockIfNeeded(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        boolean successfullyUnblocked = false;
        if (this.isBlocking()) {
            if (this.unblockCore(ent, direction, entitiesControl)) {
                this.setIsBlocking(false);
                successfullyUnblocked = true;
            }
        }
        return successfullyUnblocked;
    }

    /**
     * Core function for unblocking this IBlocker with ent.
     * @param ent
     * @return true if successfully unblocked
     */
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl);
}
