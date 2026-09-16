Game by: Darwin Lemus and Daniel Acevedo Vega

Player character - This is the character that the player controls.
Greninja (main Character): Raised beneath perpetual storm-clouds, he spends each four-year cycle honing hit-and-run tactics—darting strikes, evaporating into vapor, and re-forming behind foes—driven by a single goal: win the gods’ tournament and claim the divine blessing. The player can move up and to the sides, and can punch. When the player is next to the enemy the punches will do damage to them. 
![Greninja](src/main/resources/Greninja.png)  

Enemy - Easy - Enemies should be visually distinct from each other and should vary in either movement and/or collision consequences.

Machamp: Undisputed heavyweight champion of the underworld for the past decade, he enters this tournament in pursuit of a new challenge and to reaffirm his supremacy. Bot will chase the player, when deemed to be in close enough range bot will launch an attack and if landed, health will be deducted from the players health. This bot does the least damage out of the three.
![Machamp](src/main/resources/Machamp.png)

Enemy - Medium

Sir fetched: As the royal guard of the Duck King, he was selected by his liege to enter the tournament and showcase the strength of their kingdom. Bot will chase a player and when deemed to be close enough bot attacks. The attack will deduct health from the player and this bot does more damage than Machamp but less than Groudon.
![Sir'Fetched](src/main/resources/Sir'Fetched.png)

Enemy - Hard

Groudon: Born with a cursed demonic form and cast aside by the world, he now seeks to unleash his fury in vengeance against anyone who opposes him. Bot will chase the player, when deemed to be in close enough range bot will launch an attack and if landed, health will be deducted from the players health. This bot does the most damge out of the three enemy classes.
![Groudon](src/main/resources/Groudon.png)

Obstacle - Takes up a space on the grid and cannot be interacted with (e.g., boulder, wall).
Platform that characters can jump through from the bottom and stand on as they fight
![Platform](src/main/resources/Platform.png)

Positive Buff 1 - Mega energy: Mystical stone that is able to increase the user's ability to channel their untapped potential.  A collectible item that when the player collides with will charge up the player mega energy bar, and when the bar is charged fully player will receive an attack buff and then the player sprite will be changed with its mega evolution form. you need to collect 3 stones in order to fill up the bar, and the transformation last for 5 seconds. The attack boost is 1.25 times that of the original damage.  

![Mega Energy](src/main/resources/MegaStone.png)  ![MegaGraninja](src/main/resources/MegaGraninja.png)

Negative Buff - Poison potion: A bottle said to be filled with pure hatred and anger from the regret of loss souls. Damages players health and changes to the players sprite into a poisoned version of the character.

![Poison](src/main/resources/Poison.png) ![PoisonedGraninja](src/main/resources/PoisenedGreninja.png)
Treasure - A collectible item that increases the score: This will spawn after every round when the enemy is defeated, when collected the players score goes up.
Spirit Orb
![SpiritOrb](src/main/resources/SpiritOrb.png)

Exit - An asset that ends the level when reached (e.g., door, castle): Will spawn in the middle of the stage once the enemy is defeated, the fights are best of 3 rounds.
Exit door
![ExitDoor](src/main/resources/ExitDoor.png)

(BONUS) Dealer’s choice - New NPC, portals, key to the exit, etc. If you propose a bonus but do not implement it, you won’t be penalized, but neither will you get credit for half implementations. Extra credit will be awarded based on the complexity of the final result.
We pose an optional gamemode where two players can play against each other on the same machine one player using arrow keys and the other using wasd
