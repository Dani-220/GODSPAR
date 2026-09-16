package edu.unc.comp301.pieces;

public interface MovablePiece extends Piece {
  CollisionEvent collide(Piece other);
}
