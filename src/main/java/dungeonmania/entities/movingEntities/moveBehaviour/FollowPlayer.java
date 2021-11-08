package dungeonmania.entities.movingEntities.moveBehaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.BooleanControl;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class FollowPlayer implements IMovingBehaviour{

    /**
     * Takes the current position and finds the faster path to the player
     * @return a list of positions to get to the player
     */
    @Override
    public Direction getBehaviourDirection(EntitiesControl entitiesControl, CharacterEntity player, Position position) {
        // List<Direction> usefulDirections = getUsefuDirections(player, position);
        // return moveToUsefulUnblocked(usefulDirections, entitiesControl, position);
        if (!playerIsReachable(player, entitiesControl)) {
            return Direction.NONE;
        }
        
        List<Position> shortestPath = new ArrayList<>();
        Map<Position, List<Position>> pathsMap = new HashMap<>();
        List<Position> previousPositions = new ArrayList<>();
        pathsMap.put(position, previousPositions);
        while(!pathsMap.containsKey(player.getPosition())) {
            Map<Position, List<Position>> newPathsMap = new HashMap<>();
            newPathsMap.putAll(pathsMap);
            for(Map.Entry<Position, List<Position>> entry: pathsMap.entrySet()) {
                newPathsMap.putAll(dijkstra(entry.getKey(), newPathsMap, entitiesControl));
            }
            pathsMap.putAll(newPathsMap);
        }

        shortestPath = pathsMap.get(player.getPosition());
        // Not sure about this next line, I did it in order to fix a bug where the next position was the player's position
        Position next = player.getPosition();
        if (shortestPath.size() > 1) {
            next = shortestPath.get(1);
        }
        if(next.equals(position.translateBy(Direction.UP))) {
            return Direction.UP;
        }
        else if(next.equals(position.translateBy(Direction.LEFT))) {
            return Direction.LEFT;
        }
        else if(next.equals(position.translateBy(Direction.RIGHT))) {
            return Direction.RIGHT;
        }
        else if(next.equals(position.translateBy(Direction.DOWN))) {
            return Direction.DOWN;
        }
        else {
            return Direction.NONE;
        }
    }
    
    /**
     * Expands the search to the next list of cardinally adjacent positions
     */
    public Map<Position, List<Position>> dijkstra(Position position, Map<Position, List<Position>> pathsMap, EntitiesControl entitiesControl) {
        List<Position> currentPrevPositions = pathsMap.get(position);
        for (Position pos:position.getCardinallyAdjacentPositions()){
            if(!EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(pos))) {
                List<Position> newPrevPositions = new ArrayList<>();
                for(Position prevPosition: currentPrevPositions) {
                    newPrevPositions.add(prevPosition);
                }
                newPrevPositions.add(position);
                if (!pathsMap.containsKey(pos)){
                    pathsMap.put(pos, newPrevPositions);
                }
                else if (newPrevPositions.size() < pathsMap.get(pos).size()){
                    pathsMap.put(pos, newPrevPositions);
                }
            }
        }
        return pathsMap;
    }

    public boolean playerIsReachable(CharacterEntity player, EntitiesControl entitiesControl){
        Position upFromPlayer = player.getPosition().translateBy(Direction.UP);
        Position downFromPlayer = player.getPosition().translateBy(Direction.DOWN);
        Position leftFromPlayer = player.getPosition().translateBy(Direction.LEFT);
        Position rightFromPlayer = player.getPosition().translateBy(Direction.RIGHT);

        if(EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(upFromPlayer)) &&
           EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(downFromPlayer)) &&
           EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(leftFromPlayer)) &&
           EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(rightFromPlayer))      
        ) {
           return false; 
        }
        return true;
    }
}
