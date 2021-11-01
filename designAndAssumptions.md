# Design

# Overall Structure
We relied heavily on interfaces to define the functionality for a specific type of entity.

|Interface| Role |
|--|--|
| IBlocker  | Blocks movement across it, and defines an unblock function for unblocking it |
| ITicker  | Have some default behaviour we want them to do every tick without the player interacting with them   |
| IMovingEntity  | Can change the position using the move(direction) method |
| IAutoMovingEntity | Depend not on the tick direction, but on their environment. Extends IMovingEntity |
| IInteractingEntity  | For all the entities which can be interacted with by the frontend. |
| IBattlingEntity | Gives an entity a health, damage, and battling functions. Extends IInteractingEntity |
| IContactingEntity  | All the entities that interact with character |
  
# Strategy Pattern: Move Behaviour
--------------
We utilised the strategy pattern in moving behaviour, such as:

- IMovingBehaviour
- FollowPlayer
- RandomMove
- RunAway

The strategy pattern was used to abstract away logic for movements for auto-moving entities
In doing so it is easy to switch between different movement states and align all auto-moving entities with the same state
e.g. when player is invincible we set their movement to RunAway;
Allows for the switching in and out of move behaviour: in the future this can easily be used to improve general game design
and user experience.

# Composite Pattern: Goals

--------------
We utilised the composite pattern in goals, such as:

- IGoal
- ANDGoal
- ORGoal
- BoulderGoal
- CollectingGoal
- DestroyGoal

In this case IGoal is the component interface; ANDGoal and ORGoal are Composite objects and the individual goals are leaf objects
All of these return a boolean for the completion of a goal and are used uniformly.
This was a solution to evaluating complex goal conditions from the game because Composite objects can be composed of other composite objects etc.


# State Pattern: Spider

--------------
We utilised the state pattern to solve solutions regarding spider movement

- SpiderState
- SpiderClockwise
- SpiderAntiClockwise

The state pattern is used in SpiderEntity to determine the direction of the spider movement (Clockwise or Anti-Clockwise). After trying to implement this without using the state pattern, it became clear that trying to store different movement pattern is very difficult. Especially when reaching edge cases. SpiderState is an interface that each movement direction implements. SpiderEntity (Context) then only needs to check what state it is in and move accordingly.

# Assumptions

## Difficulty

- Difficulty effects the number of ticks that generate zombies and how often mercenaries are generated
    - by the same amount - divide by (20/15) if hard, (15/20) if easy
    - spiders generate randomly
- Difficulty also effects the amount of damage a character does and how much health you start with dividing by the same multiplier as generating
  
## Game Assumptions

- player moves first and interacts with anything on its current square
- then all moving entities move and interact with the player if it is on their current square
- this accounts for two entities converging at the same time so they dont just crossover and still interact
- player checks if target position is blocked if it cannot unblock it e.g. (move the boulder) move and interaction will not occur
    - cannot kill a spider thats on a wall
- each level is surrounded by walls

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

- Is an IBlocker, implements IInteractingEntity
- Cannot be passed or unblocked by the player, like a wall
- The player is able to click on it to destroy it if in range

### Moving Entities

**character:**

- each map can only have one character
- implements IBattlingEntity, IMovingEntity
- contains an inventory- list of collectable items
  
**spider:**

- implements IBattlingEntity, IAutoMovingEntity
- The spider also has a State pattern which determines whether its movement behaviour should be clockwise or anticlockwise
- AutoMoving changes the state if it is blocked between SpiderClockwise and SpiderAnticlockwise
- spiders spawn within the largest x,y on the map
- maximum of 4 spiders on the map
- spiders have a health of 35

**zombie toast:**

- We needed some way to get a random direction which was still seedable for unit tests
- so we created a getRandomDirection method in the Direction class that accepts a Random object
- This uses the seeded random number to generate a random direction
- It then moves in much the same way as the player
- zombie toast can drop potential armour when they are killed but do not gain the effects from this armour while equipped
- zombie toast has a health of 50

**mercenary:**

- Each tick, a hostile mercenary moves horizontally or vertically one step in the direction of the character
- If the mercenary cannot move towards the character it will not move
- A bribed mercenary will teleport to the players' most recent position, which emulates following them
- To assist the player in battle, the mercenary is added to the player's `teamMates` list
- When the player does battle, the mercenary will attack the enemy from a range before the enemy can attack the player, as the spec details
- mercenary has a health of 70

### Collectable Entities

**Potions (health, invis, invince):**

- The health potion should work regardless of player state i.e. both health potion and either an invisibility potion OR invincibility potion can be used together. 
- The invisibility potion takes precedence over the invincibility potion i.e. if used together only the effects of the invisibility will take place 
- Can only be used and picked up by the 'Player' character class, all other moving entities are unable to interact with potions. 
- Both the invisibility and invincibility potion have a time limit of 10 steps (dungeon ticks).  
- While a character is invisible, they can interact with collectable entities whilst still avoiding battle.

**bomb:**

- At first it is not armed where it is a normal collectable
- The player can pass through it and add it to its inventory by walking through
- Once it is used, it is placed in the players current position, setting isArmed = true
- The bomb is an ITicker, checking all the adjacent switches if they are active for its time to explode when it is armed
- It is also an IBlocker, blocking entities when it is armed
- Bomb will explode all adjacent non-player entities
- They are armed when they are placed, becoming unpassable
- Bombs explode cardinally adjacent elements and itself (blast radius 1)

**one ring:**

- There was no reason to make a separate IRareCollectableEntities interface just for this single implementation

**invisibility potions:**

- if invisible doesnt initate any battles with other entities
- potions last for 10 ticks

**invincible potions:**

- if invincible immediately wins all battles with other entities
- potions last for 10 ticks
- all entities run away for 10 ticks and are trapped within the game (e.g. spiders cannot climb over walls when running)

**weapons:** 

- character attacks an additional time for every weapon it has

**shielders:**

- both armour and shield halves incoming damage
- player can equip shield and armour at the same time



