package dungeonmania.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Position;

public class DungeonGenerator {
    private final Integer width = 49;
    private final Integer height = 49;
    private Position start;
    private Position end;
    private String gameMode;
    private boolean[][] maze;
    private Random rand = new Random();

    private final Boolean wall = false;
    private final Boolean empty = true;

    public DungeonGenerator(Position start, Position end, String gameMode) {
        this.start = start;
        this.end = end;
        this.gameMode = gameMode;
        this.maze = new boolean[width][height];
    }

    public Dungeon GenerateDungeon() {
        RandomizedPrims();

        ArrayList<IEntity> entities = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!maze[x][y]) {
                    entities.add(new WallEntity(x, y));
                }
            }
        }

        CharacterEntity player = new CharacterEntity(start.getX(), start.getY());
        entities.add(new ExitEntity(end.getX(), end.getY()));

        String jsonGoals = "{\"goal\": \"exit\"}";
        JsonObject jsonObject = new Gson().fromJson(jsonGoals, JsonObject.class);

        return new Dungeon(entities, gameMode, player, jsonObject);
    }

    public void RandomizedPrims() {
        maze[start.getX()][start.getY()] = empty;

        List<Position> options = new ArrayList<>();
        getAdjPostionsWithCondition(options, start, wall);

        while(options.size() > 0) {
            Position next = removeRandomFromPositions(options);

            List<Position> neighbours = new ArrayList<>();
            getAdjPostionsWithCondition(neighbours, next, empty);

            if (neighbours.size() > 0) {
                Position neighbour = neighbours.get(rand.nextInt(neighbours.size()));
                Position between = getInBetween(next, neighbour);
                maze[next.getX()][next.getY()] = empty;
                maze[between.getX()][between.getY()] = empty;
                maze[neighbour.getX()][neighbour.getY()] = empty;
            }

            getAdjPostionsWithCondition(options, next, wall);
        }

        if (maze[end.getX()][end.getY()] == wall) {
            maze[end.getX()][end.getY()] = empty;

            List<Position> neighbours = new ArrayList<>();
            List<Position> PossibleNeighbours = end.getCardinallyAdjacentPositionsWithRange(1);
            boolean allWalls = true;
            for(Position position : PossibleNeighbours) {
                if (checkBoundary(position)){
                    neighbours.add(position);
                    if (maze[position.getX()][position.getY()] == empty) {
                        allWalls = false;
                    }
                }
            }
            if (allWalls && (neighbours.size() != 0)) {
                Position neighbour = neighbours.get(rand.nextInt(neighbours.size()));
                maze[neighbour.getX()][neighbour.getY()] = empty;
            }
        }

    }

    private void getAdjPostionsWithCondition(List<Position> list, Position currentPosition, Boolean condition) {
        List<Position> adjPositions = currentPosition.getCardinallyAdjacentPositionsWithRange(2);
        for(Position position : adjPositions) {
            if (checkBoundary(position) && (maze[position.getX()][position.getY()] == condition)) {
                list.add(position);
            }
        }
    }

    private boolean checkBoundary(Position position) {
        if (position.getX() >= (width - 1)  || position.getX() <= 0) {
            return false;
        } else if (position.getY() >= (height - 1) || position.getY() <= 0) {
            return false;
        }

        return true;
    }

    private Position removeRandomFromPositions(List<Position> positions) {
        Integer randomIndex = rand.nextInt(positions.size());
        Position removedPosition = positions.get(randomIndex);
        positions.remove(removedPosition);
        return removedPosition;
    }

    private Position getInBetween(Position p1, Position p2) {
        int x = (p1.getX() + p2.getX()) / 2;
        int y = (p1.getY() + p2.getY()) / 2;

        return new Position(x, y);
    }
}
