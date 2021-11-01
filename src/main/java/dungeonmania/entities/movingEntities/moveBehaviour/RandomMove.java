package dungeonmania.entities.movingEntities.moveBehaviour;

import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RandomMove implements IMovingBehaviour{
    Random rand = new Random();

    public RandomMove(int seed) {
        rand = new Random(seed);
    }

    public RandomMove() {
        rand = new Random();
    }

    @Override
    public Direction getBehaviourDirection(EntitiesControl entitiesControl, CharacterEntity player, Position position) {
        Direction direction = Direction.getRandomDirection(new Random(rand.nextInt()));
        // TODO this is the same as the players - could we abstract this to IMovingEntity or something?
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) ) {
            return direction;
        }
        return Direction.NONE;
    }
}
