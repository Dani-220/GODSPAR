package edu.unc.comp301.pieces;

import edu.unc.comp301.pieces.requirements.Obstacle;

public class Platform extends APiece implements Obstacle {

    public Platform(String name) {
        super(name, "resources/Platform.png");
        setPosn(null);
    }

    @Override
    public boolean allowMove() {
        return false;
    }
}
