# Assumptions
-------
- Bomb will explode all adjacent non-collectable and non-player entities 
    - explodes walls, enemies, switch
    - keeps wood, player


- Potions
    - The health potion should work regardless of player state i.e. both health potion and either an invisibility potion OR invincibility potion can be used together. 
    - The invisibility potion takes precedence over the invincibility potion i.e. if used together only the effects of the invisibility will take place 
    - Can only be used and picked up by the 'Player' character class, all other moving entities are unable to interact with potions. 
    - Both the invisibility and invincibility potion have a time limit of 10 steps (dungeon ticks). 
    - While a character is invisible, they can interact with collectable entities whilst still avoiding battle
    
    
# Design Points:

# Overall Structure

# Composite Pattern: Goals

# State Pattern: Spider
