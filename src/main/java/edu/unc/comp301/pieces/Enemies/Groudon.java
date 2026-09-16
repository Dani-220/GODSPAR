package edu.unc.comp301.pieces.Enemies;

import edu.unc.comp301.pieces.CollisionEvent;

public class Groudon extends EnemyImpl {
    protected Groudon() {
        super("Groudon","src/main/resources/Groudon.png",15);
    }


    @Override
    public boolean attack(CollisionEvent event) {
        return false;
    }

    @Override
    public boolean specialMove(CollisionEvent event) {
        return false;
    }
}
