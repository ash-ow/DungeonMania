package dungeonmania.dungeon;

public enum GameModeType {
    STANDARD("Standard"),
    PEACEFUL("Peaceful"),
    HARD("Hard")
    ;

    private final String type;
    private GameModeType(String type) {
        this.type = type;
    }

    public static GameModeType getGameModeType(String type) {
        for (GameModeType gameModeType : GameModeType.values()) { 
            if (type.equals(gameModeType.toString())) {
                return gameModeType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.type;
    }

    public boolean equals(GameModeType type) {
        if (type == this) {
            return true;
        }

        return type.toString().equals(this.toString());
    }
}
