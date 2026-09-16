package edu.unc.comp301.pieces;

import edu.unc.comp301.pieces.requirements.Exit;

public class Door extends APiece implements Exit {
    public Door(String name) {
        super(name, "src/main/resources/ExitDoor.png");
    }

    @Override
    public void exit(CollisionEvent event) {
        new CollisionEvent(0, CollisionEvent.Result.NEXT_LEVEL);
    }
}
