package edu.unc.comp301.pieces;


import edu.unc.comp301.model.board.Posn;

public interface Piece {
  String getName();

  Posn getPosn();

  void setPosn(Posn posn);

  String getResourcePath();
}
