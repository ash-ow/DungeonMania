# Design

# Strategy Pattern
We relied heavily on interfaces with strategy patterns to define the functionality for a specific type of entity.

|Interface| Role |
|--|--|
| IBlocker  | Blocks movement across it, and defines an unblock function for unblocking it |
| ITicker  | Have some default behaviour we want them to do every tick without the player interacting with them   |
| IMovingEntity  | Can change the position using the move(direction) method |
| IAutoMovingEntity | Depend not on the tick direction, but on their environment. Extends IMovingEntity |
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

- Is an IBlocker
- Cannot be passed or unblocked by the player, like a wall
- The player is able to click on it
- ????
  

### Moving Entities
  
**spider:**

- implements IBattlingEntity, IAutoMovingEntity
- The spider also has a State pattern which determines whether its movement behaviour should be clockwise or anticlockwise
- AutoMoving changes the state if it is blocked between SpiderClockwise and SpiderAnticlockwise

**zombie toast:**

- We needed some way to get a random direction which was still seedable for unit tests
- so we created a getRandomDirection method in the Direction class that accepts a Random object
- This uses the seeded random number to generate a random direction
- It then moves in much the same way as the player

**mercenary:**

- Each tick, a hostile mercenary moves horizontally or vertically one step in the direction of the character
- A bribed mercenary will teleport to the players' most recent position, which emulates following them
- To assist the player in battle, the mercenary is added to the player's `teamMates` list
- When the player does battle, the mercenary will attack the enemy from a range before the enemy can attack the player, as the spec details

### Collectable Entities

**health_potion:**

- xxx

**invincibility_potion:**

- xxx

**invisibility_potion:**

- xxx

**wood:**

- xxx

**arrow:**

- xxx

**bomb:**

- At first it is not armed where it is a normal collectable
- The player can pass through it and add it to its inventory by walking through
- Once it is used, it is placed in the players current position, setting isArmed = true
- The bomb is an ITicker, checking all the adjacent switches if they are active for its time to explode when it is armed
- It is also an IBlocker, blocking entities when it is armed

**sword:**

- xxx

**armour:**

- xxx

### Generator entities
write up about how we generate mercenearies spiders and zombies

## Collectable Entities

  

*Bomb*

- Bomb will explode all adjacent non-player entities

- They are armed when they are placed, becoming unpassable

  
  


