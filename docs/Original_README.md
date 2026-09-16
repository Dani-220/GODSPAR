# Godspar
Authors: Daniel Acevedo Vega, Darwin Lemus  

---

## Overview

Briefly describe your game and its current implementation status for Part 2. Include:
- A 2–3 sentence summary of the game concept.
- What has been implemented so far (e.g., pieces, board logic, collisions).
The game is a mortal combat inspired fighting game. It's a tournament and best of 3 rounds per opponent, with 3 different opponents avaliable. The story is basically these participants fight for the entertainment of goddesses and the winner gains a blessing/power.

Currently, the game includes implementations for the player character, platforms, gravity mechanics, exit door, spirit orbs, 3 enemies, a level system with rounds, as well as all buffs. We have set up a straightforward GUI featuring a title screen, where pressing the start button initiates gameplay. In the main game scene, there's a score counter positioned in the top-left corner, and the player can move left, right, jump, and attack. All different versions of the players all have specially animated attacks as well.
The game also makes special uses of pausing and unpausing threads to provide a smoother user experience, making gameplay feel less choppy these can be found in the modelImpl file. The decorator patterns are seen in the pieces folder with two decorators with each buff the mega wrapper is purley cosmetic as the damage buff is handled by the player attack method in playerImpl. All enemy related objects are found in the enemies folder with the factory method found in EnemyImpl.

---

## Quickstart Guide
- Game can be started by using the java fx start command 
- The player is trying to Defeat all the enemies in a best out of 3-round-based fighting game with each level consisting of a different enemy who the player must defeat to get spirit orbs.  
- The player can move: The player can move to the right, left, and up with the WAD keys. gravity is implemented so that the player fall automatically no command needed.  
- Enemies will stop you by: They will chase the player and attack when in range, aiming to kill the player and claim 2 out of the 3 rounds.  
- This will happen when an enemy is successful: If the enemy wins one round, it will reset and start the next round. If the enemy takes 2 rounds, it is game over.  
- Other game mechanics include: The buff is mega energy that once enough is collected, the player can transform and give themselves a damage buff, and the debuff is poison which will drop the players health  however it cannot kill the player and the health can be regenerated after a certain amount of time, both of the buffs are going to spawn randomly. Gravity is implemented on the player and enemy.

---

## Known Issues / Incomplete Features
— The arcade 2-player mode was never implemented.
