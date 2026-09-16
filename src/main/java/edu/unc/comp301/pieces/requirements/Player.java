package edu.unc.comp301.pieces.requirements;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.pieces.*;

public interface Player extends Piece, MovablePiece {
    int getRow();

    int getCol();

    CollisionEvent collide(Piece piece);

    void takeDamage(int damage);

    int getMegaEnergyBar();

    Player unWrap();

    enum Orientation {
        UP, DOWN, LEFT, RIGHT
    }
    int attack ();
    int specialAttack();
    void setDamageMultiplier(int damageMultiplier);
    double getDamageMultiplier();
    void setHealth(double health);
    double getHealth();
    void setMegaEnergyBar(int addedEnergyBar);
    void addMegaEnery(int addedEnergyBar);
    Orientation getOrientation();


}
