# GODSPAR

A JavaFX fighting game built by **Daniel Acevedo Vega** and **Darwin Lemus** for UNC-Chapel Hill COMP 301.

GODSPAR combines Pokémon-inspired characters with an arcade tournament format. Control Greninja, fight three enemy types, collect spirit orbs, and use temporary power-ups while navigating platforms and gravity.

## Features

- Three opponents: Machamp, SirFetched, and Groudon.
- Round-based combat with a best-of-three tournament design.
- Movement, jumping, gravity, melee attacks, and a special attack.
- Mega-energy collectibles and a temporary poisoned-player state.
- Health and energy bars, score tracking, and title, victory, and game-over screens.

## Technologies and design

**Java · JavaFX · Maven · CSS**

The project separates game state, player input, and rendering through a model-view-controller structure. Observer interfaces connect model updates to the view. Player wrappers demonstrate the decorator pattern, and an enemy factory selects opponents by difficulty.

## Run locally

Install **JDK 24** and **Maven**, then open a terminal in the project folder:

```sh
mvn clean compile
mvn javafx:run
```

The supplied Maven configuration targets Java 24 and uses JavaFX 21.0.3. The game needs a graphical desktop and opens in full-screen mode. Maven downloads the configured dependencies on the first build.

## Controls

| Key | Action |
| --- | --- |
| W | Jump |
| A | Move left |
| D | Move right |
| E | Attack |
| F | Special attack |

Collect spirit orbs after defeating opponents and use the exit door to advance.

## Project structure

| Location | Contents |
| --- | --- |
| `src/main/java/edu/unc/comp301/Main.java` | Application entry point |
| `src/main/java/edu/unc/comp301/controller/` | Input and game actions |
| `src/main/java/edu/unc/comp301/model/` | Game state, board, and observer interfaces |
| `src/main/java/edu/unc/comp301/pieces/` | Characters, enemies, collisions, and player wrappers |
| `src/main/java/edu/unc/comp301/view/` | JavaFX screens and application setup |
| `src/main/resources/` | 32 game images and the CSS stylesheet |
| `PlayerGuide.md` | Original character and gameplay guide |
| `docs/` | Original overview, submission README, and restoration notes |

## Project status

This repository restores the original course submission for portfolio review. Java source files are preserved without gameplay changes. Filenames and package directories have been restored from individually downloaded submission files.

**The restored project has not yet been compiled or play-tested.** The restoration environment only provided a Java 17 runtime, without Maven or a Java compiler. The original project requires JDK 24. Static inspection identified limitations documented in [Restoration notes](docs/RESTORATION.md), including unchecked board-edge access and inconsistent piece types. Local two-player mode was proposed but not implemented.

## Credits

- Daniel Acevedo Vega
- Darwin Lemus

Pokémon characters and names belong to their respective owners. This is a student fan-game project. Original asset attribution and licensing information were not included in the uploaded files.
