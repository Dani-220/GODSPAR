package edu.unc.comp301.pieces.Enemies;

import edu.unc.comp301.pieces.CollisionEvent;

public class SirFetched extends EnemyImpl {
    protected SirFetched() {
        super("SirFetched","src/main/resources/Sir'Fetched.png",10);
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
