package dungeonmania.entities;

public enum EntityTypes {

    // Static Entities
    WALL("wall"),
    EXIT("exit"),
    BOULDER("boulder"),
    SWITCH("switch"),
    DOOR("door"),
    PORTAL("portal"),
    ZOMBIE_TOAST_SPAWNER("zombie_toast_spawner"),
    SWAMP("swamp"),

    // Moving Entities
    SPIDER("spider"),    
    ZOMBIE_TOAST("zombie_toast"),    
    MERCENARY("mercenary"),
    ASSASSIN("assassin"),
    HYDRA("hydra"),
    
    // Collectable Entities
    TREASURE("treasure"),    
    KEY("key"),    
    HEALTH_POTION("health_potion"),    
    INVINCIBILITY_POTION("invincibility_potion"),    
    INVISIBILITY_POTION("invisibility_potion"),    
    WOOD("wood"),    
    ARROW("arrow"),    
    BOMB("bomb"),    
    SWORD("sword"),    
    ANDURIL("anduril"),    
    ARMOUR("armour"),    
    ONE_RING("one_ring"),    
    SUN_STONE("sun_stone"),    
    
    // IBuildableEntity
    SHIELD("shield"),    
    BOW("bow"),    
    SCEPTRE("sceptre"),    
    MIDNIGHT_ARMOUR("midnight_armour"),
    
    // Player
    PLAYER("player")
    
    ;

    private final String type;
    private EntityTypes(String type) {
        this.type = type;
    }

    public static EntityTypes getEntityType(String type) {
        for (EntityTypes entityType : EntityTypes.values()) { 
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
