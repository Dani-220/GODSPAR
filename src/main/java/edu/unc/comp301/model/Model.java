package edu.unc.comp301.model;


import edu.unc.comp301.model.board.BoardImpl;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.Piece;

public interface Model extends Subject {
  int getWidth();

  int getHeight();

  int getCurScore();

  int getHighScore();

  int getLevel();

  Piece get(Posn p);

  // Change status from IN_PROGRESS and END_GAME
  STATUS getStatus();

  void startGame();

  void endGame(STATUS status);

  void moveUp();


  void moveLeft();

  void moveRight();

  void attack();

  void specialAttack();

  void nextLevel(boolean playerWon);

  BoardImpl getBoard();

  int getEnemyScore();

  boolean isPlayerFacingRight();

  boolean isPlayerAtacking();

  enum STATUS {
    END_GAME_WINNER,
    END_GAME_LOOSER,
    IN_PROGRESS,
    GAME_START,
    PAUSED
  }
}
