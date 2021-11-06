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

    // Moving Entities
    SPIDER("spider"),    
    ZOMBIE_TOAST("zombie_toast"),    
    MERCENARY("mercenary"),
    
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
    ARMOUR("armour"),    
    ONE_RING("one_ring"),    

    // IBuildableEntity
    SHIELD("shield"),    
    BOW("bow"),    

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
