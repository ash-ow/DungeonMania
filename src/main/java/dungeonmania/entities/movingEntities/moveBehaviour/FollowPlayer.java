package dungeonmania.entities.movingEntities.moveBehaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.BooleanControl;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwampEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class FollowPlayer implements IMovingBehaviour{

    /**
     * Takes the current position and finds the fastest path to the player
     * @return next direction to the player
     */
    @Override
    public Direction getBehaviourDirection(EntitiesControl entitiesControl, CharacterEntity player, Position position) {
        if (!playerIsReachable(player, entitiesControl)) {
            return Direction.NONE;
        }
        List<Position> path = shortestPathToPlayer(entitiesControl, player, position);
        return nextDirection(path, player, position);
    }
    
    /**
     * Finds the shortest path to the player
     * @return List of positions on the path to the player
     */
    public List<Position> shortestPathToPlayer(EntitiesControl entitiesControl, CharacterEntity player, Position position){
        Map<Position, List<Position>> mainPathsMap = new HashMap<>();
        mainPathsMap.put(position, new ArrayList<>());
        while(!mainPathsMap.containsKey(player.getPosition())) {
            Map<Position, List<Position>> newPaths = new HashMap<>();
            newPaths.putAll(mainPathsMap);
            for(Position entry: mainPathsMap.keySet()) {
                newPaths.putAll(dijkstra(entry, newPaths, entitiesControl));
            }
            mainPathsMap.putAll(newPaths);
        }
        List<Position> shortestPathToPlayer = mainPathsMap.get(player.getPosition());
        return shortestPathToPlayer;
    }

    /**
     * Expands the search to the next list of cardinally adjacent positions
     */
    public Map<Position, List<Position>> dijkstra(Position currentPosition, Map<Position, List<Position>> pathsMap, EntitiesControl entitiesControl) {
        List<Position> currentPrevPositions = pathsMap.get(currentPosition);
        for (Position newPosition:currentPosition.getCardinallyAdjacentPositions()){
            if(!EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(newPosition))) {
                List<Position> newPrevPositions = new ArrayList<>();
                for(Position prevPosition: currentPrevPositions) {
                    newPrevPositions.add(prevPosition);
                }
                newPrevPositions.add(currentPosition);
                if (!pathsMap.containsKey(newPosition)){
                    pathsMap.put(newPosition, newPrevPositions);
                } else {
                    int newPathLength = newPrevPositions.size() + pathIncreasedBySwampTiles(entitiesControl, newPrevPositions);
                    if (newPathLength < pathsMap.get(newPosition).size()){
                        pathsMap.put(newPosition, newPrevPositions);
                    }
                }
            }
        }
        return pathsMap;
    }

    public Direction nextDirection(List<Position> path, CharacterEntity player, Position position){
        Position next = player.getPosition();
        if (path.size() > 1) {
            next = path.get(1);
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

    public boolean playerIsReachable(CharacterEntity player, EntitiesControl entitiesControl){
        for (Position pos: player.getPosition().getCardinallyAdjacentPositions()) {
            if (!EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(pos))) {
                return true;
            }
        }
        return false;
    }

    public int pathIncreasedBySwampTiles(EntitiesControl entitiesControl, List<Position> newPrevPositions){
        SwampEntity potentialSwampEntity = new SwampEntity();
        int movementFactor = potentialSwampEntity.getMovementFactor();
        int numSwampTiles = 0;
        for (Position pos: newPrevPositions) {
            if(entitiesControl.positionContainsEntityType(pos, SwampEntity.class)) {
                numSwampTiles += 1;
            }
        }
        return numSwampTiles*movementFactor;    
    }
}
