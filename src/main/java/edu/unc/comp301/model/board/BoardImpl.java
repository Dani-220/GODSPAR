package edu.unc.comp301.model.board;

import edu.unc.comp301.pieces.*;
import edu.unc.comp301.pieces.Enemies.EnemyImpl;
import edu.unc.comp301.pieces.Enemies.Machamp;

import java.util.Objects;
import java.util.Random;
import edu.unc.comp301.pieces.requirements.Player;

public class BoardImpl implements Board {
  private final GameState gameStatus = GameState.RUNNING;
  private final Piece[][] board;
  private final Door door = new Door("Door");
  private volatile Player player;
  private final SpiritOrb spiritOrb = new SpiritOrb();
  private EnemyImpl enemy;
  private final Poison poison = new Poison();
  protected volatile boolean poisoned = false;
  private MegaStone megaStone;
  private boolean enemyAttacking= false;

  public BoardImpl(int width, int height) {
    this.board = new Piece[width][height];
    this.player = new PlayerImpl();
    player.setPosn(new Posn(0,0));
//    this.spawnPoison();
  }

  public BoardImpl(Piece[][] board) {
    this.board = board;
    player = new PlayerImpl();
    player.setPosn(new Posn(7,14));
    board [7][14] = player;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Piece[] pieces : this.board) {
      for (int j = 0; j < this.board[0].length; j++) {
        if (pieces[j] == null) {
          sb.append("0");
        } else if (pieces[j] == player) {
          sb.append("2");
        } else if (Objects.equals(pieces[j].getName(), "Platform")) {
          sb.append("1");
        } else if (Objects.equals(pieces[j].getName(), "Floor")) {
          sb.append("1");
        } else if (pieces[j] == enemy) {
          sb.append("3");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public void init(int obstacles) {
    int width = getWidth();
    int height = getHeight();
    if (obstacles > width * (height-1)) {
      throw new IllegalArgumentException();
    }
    int row;
    int col;
    clearBoard();
    player.setPosn(new Posn(0, 0));
    board[0][0] = player;
    this.spawnEnemy(EnemyImpl.create(EnemyImpl.difficulty.EASY));
    for (int i = 0; i < width; i++) {
      board[height - 1][i] = new Platform("Floor");
    }

    for (int i = 0; i < obstacles; i++) {
      row = height - 3 + (int) (Math.random() * 3);
      col = (int) (Math.floor(Math.random() * width));
      if (board[row][col] == null) {
        board[row][col] = new Platform("Platform");
      } else {
        i--;
      }
    }
  }

  public void clearBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        board[i][j] = null;
      }
    }
  }



  //Status checkers and gravity

  public void gravity(MovablePiece piece) {
    Posn posn = new  Posn(piece.getPosn().getRow() + 1, piece.getPosn().getCol());
    if (canFall(piece)) {
      board[piece.getPosn().getRow()][piece.getPosn().getCol()] = null;
      piece.setPosn(posn);
      board[piece.getPosn().getRow()][piece.getPosn().getCol()] = piece;
    }
  }

  public boolean canFall(MovablePiece piece) {
    return bellowCollision(piece).getResults() != CollisionEvent.Result.DO_NOT_PASS;
  }
  public CollisionEvent bellowCollision(MovablePiece piece) {
    int bellow = piece.getPosn().getRow() + 1;
    Piece bellowPiece = this.get(new Posn(bellow, piece.getPosn().getCol()));
    return piece.collide(bellowPiece);
  }

  public boolean canJump(MovablePiece piece) {
    int above = piece.getPosn().getRow() - 1;
    if (above < 0) {
      return false;
    }
    Piece abovePiece = this.get(new Posn(above, piece.getPosn().getCol()));
    CollisionEvent collisionEvent = piece.collide(abovePiece);
    return collisionEvent.getResults() != CollisionEvent.Result.DO_NOT_PASS || abovePiece instanceof Platform;
  }

  public boolean canMoveLeft(MovablePiece piece) {
    int left = piece.getPosn().getCol() - 1;
    if (left < 0) {
      return false;
    }
    Piece leftPiece = this.get(new Posn(piece.getPosn().getRow(), left));
    CollisionEvent collisionEvent = piece.collide(leftPiece);
    return collisionEvent.getResults() != CollisionEvent.Result.DO_NOT_PASS;
  }

  public boolean canMoveRight(MovablePiece piece) {
    int right = piece.getPosn().getCol() + 1;
    if (right >= getWidth()) {
      return false;
    }
    Piece rightPiece = this.get(new Posn(piece.getPosn().getRow(), right));
    CollisionEvent collisionEvent = piece.collide(rightPiece);
    return collisionEvent.getResults() != CollisionEvent.Result.DO_NOT_PASS;
  }


  //Enemy Logic

  public synchronized void updateEnemyPos(EnemyImpl enemy) {
    String result = enemy.findPlayer(player);
    if (Objects.equals(result, "Up")) {
      enemyAttacking = false;
      if (this.canJump(enemy)) {
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = null;
        enemy.setPosn(new Posn(enemy.getRow() - 2, enemy.getCol()));
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = enemy;
      }
    } else if (Objects.equals(result, "Right")) {
      enemyAttacking = false;
      if (this.canMoveRight(enemy)){
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = null;
        enemy.setPosn(new Posn(enemy.getRow(), enemy.getCol() +1));
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = enemy;
      } else if(this.canJump(enemy)){
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = null;
        enemy.setPosn(new Posn(enemy.getRow() - 2, enemy.getCol() +1));
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = enemy;
      }
    } else if (Objects.equals(result, "Left")) {
      enemyAttacking = false;
      if (this.canMoveLeft(enemy)){
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = null;
        enemy.setPosn(new Posn(enemy.getRow(), enemy.getCol() -1));
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = enemy;
      }else if(this.canJump(enemy)){
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = null;
        enemy.setPosn(new Posn(enemy.getRow() - 2, enemy.getCol() -1));
        board[enemy.getPosn().getRow()][enemy.getPosn().getCol()] = enemy;
      }
    }else if (Objects.equals(result, "Attack")){
      enemyAttacking = true;
    }
  }


  //Player Buff and poison

  public void applyBuff(){
    megaStone.applyBuff(player);
  }

  public void MegaPlayer(){
    Player p = this.player;
    MegaPlayerWrap megaPlayer = new MegaPlayerWrap(p);
    Thread MegaPlayerThread = new Thread(() ->{
      this.player = megaPlayer;
      double previousPlayerHealth = p.getHealth();
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      if (megaPlayer.getHealth() >= 0) {
        megaPlayer.setHealth(previousPlayerHealth);
        this.player = megaPlayer.unWrap();
      }
    });
    MegaPlayerThread.start();
  }

  public void poisonPlayer(){
    Player p = this.player;
    PoisonedPlayerWrap poisonedPlayer = new PoisonedPlayerWrap(p);
    Thread poisonedPlayerThread = new Thread(() ->{
      this.player = poisonedPlayer;
      double previousPlayerHealth = p.getHealth();
      poisonedPlayer.setHealth(20);
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      if (poisonedPlayer.getHealth() >= 0) {
        poisonedPlayer.setHealth(previousPlayerHealth);
        this.player = poisonedPlayer.unWrap();
      }
    });
    poisonedPlayerThread.start();
  }



  //Player attack and move methods

  @Override
  public CollisionEvent moveHero(int drow, int dcol) {
    if (player.getCol() + dcol >= getWidth() || player.getCol() + dcol < 0) {
      return new CollisionEvent(0, CollisionEvent.Result.DO_NOT_PASS);
    }
    Piece piece = get(new Posn(player.getRow() + drow, player.getCol() + dcol));
    if (player.collide(piece).getResults() != CollisionEvent.Result.DO_NOT_PASS) {
      board[player.getRow()][player.getCol()] = null;
      player.setPosn(new Posn(player.getRow() + drow, player.getCol() + dcol));
      board[player.getRow()][player.getCol()] = player;
    }
    return player.collide(piece);
  }

  public void playerAttack (){
    if (get(new Posn(player.getRow(), player.getCol() +1 )) instanceof EnemyImpl){
        this.enemy.damageHealth(player.attack());
    }
    if (get(new Posn(player.getRow(),player.getCol()-1)) instanceof EnemyImpl){
        this.enemy.damageHealth(player.attack());
    }
    if (get(new Posn(player.getRow() - 1, player.getCol())) instanceof EnemyImpl) {
        this.enemy.damageHealth(player.attack());
    }
    if (player.getMegaEnergyBar() >= 100) {
      player.setMegaEnergyBar(0);
    }
    
  }

  public void specialAttack (){
    if (get(new Posn(player.getRow(), player.getCol() +1 )) instanceof EnemyImpl){
      this.enemy.damageHealth(player.specialAttack());
    }
    if (get(new Posn(player.getRow(),player.getCol()-1)) instanceof EnemyImpl){
      this.enemy.damageHealth(player.specialAttack());
    }
    if (get(new Posn(player.getRow() - 1, player.getCol())) instanceof EnemyImpl) {
      this.enemy.damageHealth(player.specialAttack());
    }
    if (player.getMegaEnergyBar() >= 100) {
      player.setMegaEnergyBar(0);
    }
  }



  //Level and round control

  public synchronized void setLevel (int level) {
    this.player = player.unWrap();
    player.setHealth(100);
    if (level == 1) {
      spawnEnemy(EnemyImpl.difficulty.EASY);
    } else if (level == 2) {
      spawnEnemy(EnemyImpl.difficulty.MEDIUM);
    } else if (level == 3) {
      spawnEnemy(EnemyImpl.difficulty.HARD);
    }
  }

  public void nextRound(boolean playerWon){
    this.player = player.unWrap();
    if (!playerWon) {
      board[enemy.getRow()][enemy.getCol()] = null;
    }
    spawnEnemy(getEnemy());
    player.setHealth(100);
    board[player.getRow()][player.getCol()] = null;
    player.setPosn(new Posn(7,14));
    board [7][14] = player;
  }



  //Spawn Methods

  public void spawnOrb(){
    spiritOrb.setPosn(new Posn(enemy.getRow(), enemy.getCol()));
    board[spiritOrb.getRow()][spiritOrb.getCol()] = spiritOrb;
  }

  public void deSpawnDoor(){
    player.setPosn(new Posn(7,14));
    board [7][14] = player;
    board[door.getRow()][door.getCol()] = null;
    door.setPosn(null);
  }

  public void spawnStone(){
     this.megaStone = new MegaStone();
    megaStone.setPosn(new Posn(5, (int) Math.floor(Math.random() * getWidth())));
    board[megaStone.getRow()][megaStone.getCol()] = megaStone;
  }
  @Override
  public synchronized void spawnEnemy(EnemyImpl.difficulty difficulty) {
    this.enemy = EnemyImpl.create(difficulty);
    this.enemy.setHealth(100);
    board[getHeight()-2][1] = enemy;
    enemy.setPosn(new Posn(getHeight()-2, 1));
  }

  public synchronized void spawnEnemy(EnemyImpl enemy) {
    this.enemy = enemy;
    this.enemy.setHealth(100);
    board[getHeight()-2][1] = enemy;
    enemy.setPosn(new Posn(getHeight()-2, 1));
  }

  @Override
  public void spawnDoor() {
    this.door.setPosn(RandomSpawnGen());
    board[door.getRow()][door.getCol()] = door;
  }

  private Posn RandomSpawnGen() {
    int rnd = new Random().nextInt(getWidth());
    Posn posn = new Posn(getHeight()-2, rnd);
    while (get(posn) != null) {
      rnd = new Random().nextInt(getWidth());
      posn = new Posn(getHeight()-2, rnd);
    }
    return posn;
  }

  public void spawnPoison() {
    this.poison.setPosn(RandomSpawnGen());
    board[poison.getRow()][poison.getCol()] = poison;
  }



  //Getter Methods

  public Player getPlayer() {
    return player;
  }

  public int getWidth() {
    return board[0].length;
  }

  public int getHeight() {
    return board.length;
  }

  public Piece[][] getBoard() {
    return board;
  }

  public Player.Orientation getPlayerOrientation (){return player.getOrientation();}

  public Piece get(Posn posn) {
    return board[posn.getRow()][posn.getCol()];
  }

  public EnemyImpl getEnemy() {
    return this.enemy;
  }

  public void setPieceNull(APiece piece) {
    board[piece.getRow()][piece.getCol()] = null;
  }








  enum GameState {
    RUNNING,
    GAME_OVER,
    NEXT_LEVEL
  }
}
