package dungeonmania.dungeon;

public enum GameModeType {
    STANDARD("standard"),
    PEACEFUL("peaceful"),
    HARD("hard")
    ;

    private final String type;
    private GameModeType(String type) {
        this.type = type;
    }

    public static GameModeType getGameModeType(String type) {
        for (GameModeType gameModeType : GameModeType.values()) { 
            if (type.toLowerCase().equals(gameModeType.toString())) {
                return gameModeType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
