# Restoration notes

## What was restored

- 45 distinct Java source files placed in directories matching their declared packages.
- 32 original PNG assets with download suffixes removed.
- `main.txt` restored as `src/main/resources/main.css`.
- Original `pom.xml` and `PlayerGuide.md` retained.
- Original overview PDF and README retained in `docs/`.
- One byte-identical duplicate of `Poison.java` omitted.
- IDE-specific settings excluded; a repository `.gitignore` added.
- Both `model/pieces` and `pieces` source trees retained because they declare different packages.

`UPLOAD_MAP.json` records the uploaded Java/image filenames and their restored destinations. The original uploaded files were not modified.

## Verification

All Java source bytes match their uploads. All 32 PNGs were decoded successfully. Package paths, public type filenames, and explicit internal imports were checked. Image references in active CSS rules were checked against the restored resource directory. These are static checks, not a successful compilation or gameplay test.

The environment has OpenJDK 17 runtime only; `javac`, Maven, and JDK 24 are unavailable. Consequently compilation and launch could not be verified. The original Maven build configuration is unchanged.

## Issues visible in the submitted source

These are inspection findings, not reproduced runtime failures:

- `ModelImpl.get()` casts a `pieces.Piece` returned by the board to the unrelated `model.pieces.Piece` interface. Calling it with an ordinary board piece may cause a class-cast exception.
- `BoardImpl.get()` directly indexes the board without bounds checks. Attack methods inspect neighboring cells, so attacking near an edge may access an invalid index.
- Game state and some view updates originate from worker threads. JavaFX threading and round/restart behavior need play-testing.
- Piece resource strings mix bare filenames, `resources/`, and `src/main/resources/` prefixes. `PoisonedPlayerWrap` names `PoisonedGraninja.png`, while the uploaded sprite is `PoisenedGreninja.png`; the view currently uses CSS sprite classes. These paths should be reviewed before relying on `getResourcePath()` for rendering.
- The original player guide describes a proposed two-player mode; the original README confirms it was not implemented.

No gameplay fixes or dependency upgrades were made during restoration. The three assignment instruction documents were not uploaded and are omitted.
