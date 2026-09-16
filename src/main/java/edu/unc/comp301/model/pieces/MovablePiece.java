package edu.unc.comp301.model.pieces;

public interface MovablePiece extends Piece {
  CollisionEvent collide(Piece other);
}
