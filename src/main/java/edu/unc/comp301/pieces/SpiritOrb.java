package edu.unc.comp301.pieces;

import edu.unc.comp301.pieces.requirements.Treasure;

public class SpiritOrb extends APiece implements Treasure {
    public SpiritOrb() {
        super("SpiritOrb", "resources/SpiritOrb.png");
        setPosn(null);
    }
    @Override
    public CollisionEvent increaseScore() {
        return new CollisionEvent(1, CollisionEvent.Result.CONTINUE);
    }
}
