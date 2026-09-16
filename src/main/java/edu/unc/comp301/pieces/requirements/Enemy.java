package edu.unc.comp301.pieces.requirements;

import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.Piece;
import edu.unc.comp301.pieces.PlayerImpl;

public interface Enemy {
    enum Orientation {
        UP, DOWN, LEFT, RIGHT
    }
    boolean attack(CollisionEvent event);
    boolean specialMove(CollisionEvent event);

    String findPlayer(Player player);

    CollisionEvent collide(Piece other);

    void damageHealth(double damage);
}
