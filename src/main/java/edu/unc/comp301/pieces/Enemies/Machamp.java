package edu.unc.comp301.pieces.Enemies;

import edu.unc.comp301.pieces.CollisionEvent;

public class Machamp extends EnemyImpl {
    protected Machamp() {
        super("MaChamp","src/main/resources/Machamp.png",5);
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
