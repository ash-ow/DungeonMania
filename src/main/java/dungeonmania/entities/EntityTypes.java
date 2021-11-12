package dungeonmania.entities;

public enum EntityTypes {

    // Static Entities
    WALL("wall"),
    EXIT("exit"),
    BOULDER("boulder"),
    SWITCH("switch"),
    DOOR("door"),
    UNLOCKED_DOOR("unlocked_door"),
    PORTAL("portal"),
    ZOMBIE_TOAST_SPAWNER("zombie_toast_spawner"),
    TIME_TRAVEL_PORTAL("time_travelling_portal"),
    SWAMP("swamp"),
    LIGHT_BULB_ON("light_bulb_on"),
    LIGHT_BULB_OFF("light_bulb_off"),
    WIRE("wire"),

    // Moving Entities
    SPIDER("spider"),
    ZOMBIE_TOAST("zombie_toast"),
    MERCENARY("mercenary"),
    ASSASSIN("assassin"),
    
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
    TIME_TURNER("time_turner"), 
    SUN_STONE("sun_stone"),    
    
    // IBuildableEntity
    SHIELD("shield"),
    BOW("bow"),
    SCEPTRE("sceptre"),
    MIDNIGHT_ARMOUR("midnight_armour"),
    
    // Player
    PLAYER("player"),
    OLDER_PLAYER("older_player")
    
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
