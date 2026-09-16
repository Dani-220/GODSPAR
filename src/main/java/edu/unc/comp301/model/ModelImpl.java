package edu.unc.comp301.model;

import edu.unc.comp301.model.board.BoardImpl;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.Piece;
import edu.unc.comp301.pieces.CollisionEvent;
import edu.unc.comp301.pieces.requirements.Player;
import edu.unc.comp301.view.View;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

public class ModelImpl implements Model{
    private final Object pauseLock = new Object();
    private final List<View> views;
    private View view;
    private final BoardImpl boardImpl;
    private volatile STATUS status;
    private volatile int score= 0;
    private int highScore= 0;
    private volatile int enemyScore;
    private volatile int roundScore= 0;
    private int level;
    private volatile CollisionEvent result = new CollisionEvent(0, CollisionEvent.Result.CONTINUE);
    private volatile boolean paused = false;
    private boolean playerAtacking = false;
    private Thread gravityThread;
    private Thread gameBehaviorThread;



    public ModelImpl(int width, int height) {
      boardImpl = new BoardImpl(width, height);
      boardImpl.init(5);
      this.status = STATUS.GAME_START;
      this.views = new ArrayList<>();
      this.score = 0;
    }
    public ModelImpl(BoardImpl board) {
      this.boardImpl = board;
      this.level = 1;
      this.status = STATUS.GAME_START;
      this.views = new ArrayList<>();
      this.score = 0;
      boardImpl.setLevel(1);
    }

    @Override
    public int getWidth() {
        return boardImpl.getWidth();
    }

    @Override
    public int getHeight() {
        return boardImpl.getHeight();
    }



    @Override
    public int getCurScore() {
       return score;
    }
    //Also not done yet
    @Override
    public int getHighScore() {
        return highScore;
    }

    //Not done yet
    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Piece get(Posn p) {
        return (Piece) boardImpl.get(p);
    }




    @Override
    public STATUS getStatus() {
        return this.status;
    }

    @Override
    public void startGame() {
        if (gravityThread != null && gravityThread.isAlive()) {
            gravityThread.interrupt();
        }
        if (gameBehaviorThread != null && gameBehaviorThread.isAlive()) {
            gameBehaviorThread.interrupt();
        }
      this.status = STATUS.IN_PROGRESS;
        if (this.getBoard().getEnemy() != null) {
            boardImpl.setPieceNull(boardImpl.getEnemy());
        }
      score = 0;
      roundScore= 0;
      enemyScore = 0;
      this.level = 1;
      boardImpl.setLevel(1);
      moveRight();
      moveLeft();
      updateViews();
      //Player gravity
      gravityThread  =
        new Thread(
            () -> {
                while (!Thread.currentThread().isInterrupted() && getStatus() == STATUS.IN_PROGRESS) {
                try {
                  Thread.sleep(250);
                  result = boardImpl.bellowCollision(boardImpl.getPlayer());
                  boardImpl.gravity(boardImpl.getPlayer());
                  checkPlayerMovement();
                  Platform.runLater(this::updateViews);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
              }
            });
      //Handles all the behavior in game such as enemy movement and deciding when to spawn things in
      gameBehaviorThread  =
        new Thread(
            () -> {
                while (!Thread.currentThread().isInterrupted() && getStatus() == STATUS.IN_PROGRESS) {
                  double randomSpawn = Math.random();
                  synchronized (pauseLock) {
                      while (paused) {
                          try {
                              pauseLock.wait();
                          } catch (InterruptedException e) {
                              Thread.currentThread().interrupt();
                              return;
                          }
                      }
                  }
                try {
                  Thread.sleep(250);
                  boardImpl.gravity(boardImpl.getEnemy());
                  if (boardImpl.getEnemy().getHealth() == 0) {
                    boardImpl.spawnOrb();
                    pauseGame();
                  } else {
                    boardImpl.updateEnemyPos(boardImpl.getEnemy());
                  }if (boardImpl.getPlayer().getHealth() == 0){
                        enemyScore ++;
                        nextLevel(false);
                    }
                    if (randomSpawn < .05){
                        boardImpl.spawnStone();
                    }
                    if (randomSpawn > .98){
                        boardImpl.spawnPoison();
                    }
                  Platform.runLater(this::updateViews);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                }
            });
      gravityThread .start();
      gameBehaviorThread .start();
      updateViews();
    }

    @Override
    public void endGame(STATUS status) {
        //Makes sure to kill threads insuring no bugs
      this.status = status;
        if (gravityThread != null && gravityThread.isAlive()) {
            gravityThread.interrupt();
            gravityThread = null;
        }
        if (gameBehaviorThread != null && gameBehaviorThread.isAlive()) {
            gameBehaviorThread.interrupt();
            gameBehaviorThread = null;}

      if (score>highScore){
          highScore = score;
      }
      updateViews();
    }

    @Override
    public void moveUp() {
        if (!boardImpl.canFall(boardImpl.getPlayer()) && boardImpl.canJump(boardImpl.getPlayer()) && status == STATUS.IN_PROGRESS) {
            result = boardImpl.moveHero(-2,0);
            checkPlayerMovement();
        }
        updateViews();
    }

    @Override
    public void moveLeft() {
        if(status == STATUS.IN_PROGRESS){
            result = boardImpl.moveHero(0,-1);
            checkPlayerMovement();
            updateViews();
        }
    }

    @Override
    public void moveRight() {
        if(status == STATUS.IN_PROGRESS){
            result =boardImpl.moveHero(0,1);
            checkPlayerMovement();
            updateViews();
        }
    }

    @Override
    public void attack(){
        playerAtacking = !playerAtacking;
        boardImpl.playerAttack();
        updateViews();
    }

    @Override
    public void specialAttack (){
        boardImpl.specialAttack();
        updateViews();
    }

    @Override
    public void nextLevel(boolean playerWon) {
        if (roundScore == 2){
            level++;
            if (level > 3){
                endGame(STATUS.END_GAME_WINNER);
            }
            boardImpl.setLevel(level);
            roundScore = 0;
        } else if (enemyScore == 2){
            endGame(STATUS.END_GAME_LOOSER);
        } else {
            nextRound(playerWon);
        }
        Platform.runLater(this::updateViews);
    }

    public void nextRound (boolean playerWon){
        boardImpl.nextRound(playerWon);
    }


    @Override
    public void addView(Observer view) {
      views.add((View)view);
    }

    private void updateViews() {
      for(View view : views) {
        view.update();
      }
    }

    @Override
    public BoardImpl getBoard() {
        return this.boardImpl;
    }

    public void pauseGame() {
        synchronized (pauseLock) {
            paused = true;
        }
    }

    public void resumeGame() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
    public void togglePause() {
        if (paused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }
    @Override
    public int getEnemyScore (){
        return enemyScore;
    }

    private void checkPlayerMovement () {
        //Handles all player interactions with specific board pieces serves as our version of the turn based movement
        score += result.getPoints();
        roundScore += result.getPoints();
        playerAtacking = false;
        if (result.getResults() == CollisionEvent.Result.DEBUFF) {
            boardImpl.poisonPlayer();
        }
        if (result.getResults() == CollisionEvent.Result.APPLY_BUFF) {
            boardImpl.applyBuff();
            if(boardImpl.getPlayer().getMegaEnergyBar()>=100){
                boardImpl.MegaPlayer();
            }
        }
        if (result.getResults() == CollisionEvent.Result.NEXT_ROUND ) {
            if (roundScore == 2) {
                boardImpl.spawnDoor();
                updateViews();
                return;
            }
            resumeGame();
            nextLevel(true);
        } if (result.getResults() == CollisionEvent.Result.NEXT_LEVEL ) {
            boardImpl.deSpawnDoor();
            updateViews();
            resumeGame();
            nextLevel(true);
        }
    }



    // These are used in view to make sure the correct sprite is used
    @Override
    public boolean isPlayerFacingRight() {
        return boardImpl.getPlayerOrientation() == Player.Orientation.RIGHT;
    }
    @Override
    public boolean isPlayerAtacking() {return playerAtacking;}
}


