package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.collectableEntities.KeyEntity;

public class Inventory {
    List<ICollectable> items;

    public Inventory() {
        this(new ArrayList<ICollectable>());
    }

    public Inventory(List<ICollectable> inventory) {
        this.items = inventory;
    }
    
	public List<ICollectable> getItems() {
		return this.items;
	}
    
    public void addItem(ICollectable item) {
        if(item.getType().equals(EntityTypes.KEY)){
            KeyEntity key = (KeyEntity) item;
            if (!checkKeyState()){
                key.setkeyPickedUp(true);
                setHasKey(true);
            }
            else{
                return ;
            }    
        }
        this.items.add(item);
    }

// region Filters
    /**
     * Finds an item in the inventory based on its id
     * @param itemID Item identifier
     */
    public IEntity getInventoryItemById(String itemID) {
        return this.items.stream().filter(item -> item.getId().equals(itemID)).findFirst().orElse(null);
    }

    public <T extends ICollectable> List<T> getItemsFromInventoryOfType(Class<T> cls) {
        return this.items.stream().filter(cls::isInstance).map(cls::cast).collect(Collectors.toList());
    }

    public <T extends ICollectable> T getFirstItemOfType(Class<T> cls) {
        return this.items.stream().filter(cls::isInstance).map(cls::cast).findFirst().orElse(null);
    }

    public boolean containsItemOfType(EntityTypes type) {
        return this.items.stream().map(IEntity::getType).collect(Collectors.toList()).contains(type);
    }

// endregion


// region Building

    /**
     * Finds the number of a certain component in the inventory
     * @param component component which is being searched for
     * @return the number of components
     */
    public int numberOfComponentItemsInInventory(EntityTypes component) {
        return this.items
            .stream()
            .filter(ent -> ent.getType() == component)
            .collect(Collectors.toList())
            .size();
    }

    /**
     * Removes build materials based on their type, and the amount of materials which need to be removed
     * @param type of entity to be removed
     * @param amount of material that needs to be removed for each type
     */
    public void removeBuildMaterials(EntityTypes type, int amount) {
        int removed = 0;
        List<ICollectable> toRemove = new ArrayList<>();
        while(removed < amount) {
            for(ICollectable material : this.items) {
                if (material.getType().equals(type)){
                    toRemove.add(material);
                    removed++;
                }
            }
        }
        for (ICollectable material : toRemove) {
            this.items.remove(material);
        }
    }
// endregion

/**
     * Checks if the Key has been picked up in the KeyEntity class
     * keyPickedUp resets to false after key is used  
     */
    public boolean checkKeyState(){
        for(ICollectable k: items){
            if(k.getType().equals(EntityTypes.KEY)){
                KeyEntity key = (KeyEntity) k;
                if (key.keyPickedUp()){
                    return true;
                }
            }
        }
        return false;
    }
}
