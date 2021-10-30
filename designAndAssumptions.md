# Design

# Strategy Pattern
We relied heavily on interfaces with strategy patterns to define the functionality for a specific type of entity.

|Interface| Role |
|--|--|
| IBlocker  | Blocks movement across it, and defines an unblock function for unblocking it |
| ITicker  | Have some default behaviour we want them to do every tick without the player interacting with them   |
| IMovingEntity  | Can change the position using the move(direction) method |
| IInteractingEntity  | For all the entities which must interact with the player when they share a space. |
| IBattlingEntity | Gives an entity a health, damage, and battling functions. Extends IInteractingEntity |
  
# Overall Structure

--------------
We heavily utilised the strategy pattern here, such as:

- IBlocker
- ITicker
- IContactingEntity
- IMovingEntity
- ICollectableEntity

  

# Composite Pattern: Goals

--------------

  

# State Pattern: Spider

--------------




# Assumptions

### Static Entities
  

**boulder:**


- The boulder is an IBlocker which is always blocking
-  Also an IMovingEntity, able to move itself if there is nothing impeding it
- The player trying to move into the boulder's space triggers that movement 

**switch:**

- The switch ticks every round to check if there is a boulder on the same position as it.
-  If there is, it becomes active
- if there isn't, it becomes inactive

**doors and keys:**

- In our implementation, you can hold multiple keys at once
- Doors are IBlockers
- To unblock, it sorts through all the keys in the player's inventory to find the correct one for the door in the player position
- thus there is no need to implement dropping/re-collecting functionality, which is simpler and enhances user experience

**portal:**

- The portal is an IInteractingEntity
- When the player interacts with it, their position is set to that of the corresponding portal

**zombie toast spawner:**

  

## Moving Entities

  

## Collectable Entities

  

*Bomb*

- Bomb will explode all adjacent non-player entities

- They are armed when they are placed, becoming unpassable

  
  


