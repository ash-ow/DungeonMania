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
        for (GameModeType entityType : GameModeType.values()) { 
            if (type.equals(entityType.toString())) {
                return entityType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.type.toLowerCase();
    }
}
