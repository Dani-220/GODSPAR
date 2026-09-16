package edu.unc.comp301.model.board;


import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.Enemies.EnemyImpl;
import edu.unc.comp301.pieces.Piece;

/** Defines the public operations on the game board. */
public interface Board {

  void init(int obstacles);


  int getWidth();

  int getHeight();

  Piece get(edu.unc.comp301.model.board.Posn posn);

  CollisionEvent moveHero(int drow, int dcol);


  void spawnDoor();

//  void spawnEnemy (EnemyImpl enemy);
  void spawnPoison();

  void spawnEnemy(EnemyImpl.difficulty difficulty);
}
