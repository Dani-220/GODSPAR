package edu.unc.comp301.view;




import edu.unc.comp301.pieces.*;
import edu.unc.comp301.pieces.Enemies.Groudon;
import edu.unc.comp301.pieces.Enemies.Machamp;
import edu.unc.comp301.pieces.Enemies.SirFetched;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.Observer;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


import static java.lang.Thread.sleep;

public class View implements FXComponent, Observer {
  private final Controller pc;
  private final Model model;
  private final Stage stage;
  int scale = 105;

  public View(Controller pc, Model model, Stage stage) {
    this.pc = pc;
    this.model = model;
    this.stage = stage;
  }

  @Override
  public Parent render() {
    return GameView();
  }

  public Parent GameView() {
    StackPane root = new StackPane();
    BorderPane gameScreen = new BorderPane();
    gameScreen.setPadding(new Insets(20));
    GridPane grid = new GridPane();
    HBox stats = new HBox();
    stats.setPadding(new Insets(10));
    stats.setSpacing(20);
    Piece[][] board = model.getBoard().getBoard();

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    double width = primaryScreenBounds.getWidth();
    double height = primaryScreenBounds.getHeight();
    ImageView bgView;
    if (model.getLevel() == 1){
       bgView = new ImageView("Background.png");
    }
    else if (model.getLevel() == 2){
      bgView = new ImageView("Background2.png");
    } else {
      bgView = new ImageView("Background3.png");
    }
    bgView.setFitWidth(width);
    bgView.setFitHeight(height);
    root.getChildren().add(bgView);

    Label score = new Label("Score: " + model.getCurScore());
    score.getStyleClass().add("score");

    Label enemyScore = new Label("Enemy Score: " + model.getEnemyScore());
    enemyScore.getStyleClass().add("score");

    ProgressBar healthBar = new ProgressBar(1.0);
    double currentHealth = model.getBoard().getPlayer().getHealth();
    healthBar.setProgress(currentHealth / 100);
    healthBar.getStyleClass().add("healthBar");

    ProgressBar megaEnergyBar = new ProgressBar(0);
    double currentEnergy = model.getBoard().getPlayer().getMegaEnergyBar();
    megaEnergyBar.setProgress(currentEnergy / 100);
    megaEnergyBar.getStyleClass().add("megaBar");

    ProgressBar enemyHealthBar = new ProgressBar(1.0);
    double enemyCurrentHealth = model.getBoard().getEnemy().getHealth();
    enemyHealthBar.setProgress(enemyCurrentHealth / 100);
    enemyHealthBar.getStyleClass().add("healthBar");

    Label instructions = new Label("          E: Attack, WAD: UP, LEFT, RIGHT");
    instructions.getStyleClass().add("instructions");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    stats.getChildren().addAll(score, healthBar, megaEnergyBar, instructions, spacer, enemyScore, enemyHealthBar);

    gameScreen.setTop(stats);

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        StackPane cell = new StackPane();

        APiece piece = (APiece) board[i][j];
        if (piece instanceof PlayerImpl) {
          Rectangle player = new Rectangle(scale, scale);
          if (model.isPlayerFacingRight()) {
            if (model.isPlayerAtacking()) {
              player.getStyleClass().add("playerRightAttacking");
            } else {
              player.getStyleClass().add("player");
            }
          } else {
            if (model.isPlayerAtacking()) {
              player.getStyleClass().add("playerLeftAttacking");
            } else {
              player.getStyleClass().add("playerLeft");
            }
          }
          cell.getChildren().add(player);
        } else if (piece instanceof Platform) {
          Rectangle platform = new Rectangle(scale, scale);
          platform.getStyleClass().add("platform");
          cell.getChildren().add(platform);
        } else if (piece instanceof Door) {
          Rectangle door = new Rectangle(scale, scale);
          door.getStyleClass().add("exitDoor");
          cell.getChildren().add(door);
        } else if (piece instanceof Machamp) {
          Rectangle machamp = new Rectangle(scale, scale);
          machamp.getStyleClass().add("machamp");
          cell.getChildren().add(machamp);
        } else if (piece instanceof Groudon) {
          Rectangle Groudon = new Rectangle(scale, scale);
          Groudon.getStyleClass().add("groudon");
          cell.getChildren().add(Groudon);
        } else if (piece instanceof SirFetched) {
          Rectangle SirFetched = new Rectangle(scale, scale);
          SirFetched.getStyleClass().add("sirfetched");
          cell.getChildren().add(SirFetched);
        } else if (piece instanceof SpiritOrb) {
          Rectangle SpiritOrb = new Rectangle(scale, scale);
          SpiritOrb.getStyleClass().add("spiritOrb");
          cell.getChildren().add(SpiritOrb);
        } else if (piece instanceof Poison) {
          Rectangle Poison = new Rectangle(scale, scale);
          Poison.getStyleClass().add("poison");
          cell.getChildren().add(Poison);
        } else if (piece instanceof MegaStone) {
          Rectangle MegaStone = new Rectangle(scale, scale);
          MegaStone.getStyleClass().add("mega_stone");
          cell.getChildren().add(MegaStone);
        } else if (piece instanceof PoisonedPlayerWrap) {
          Rectangle player = new Rectangle(scale, scale);
          if (model.isPlayerFacingRight()) {
            if (model.isPlayerAtacking()) {
              player.getStyleClass().add("poisonedPlayerAttacking");
            } else {
              player.getStyleClass().add("poisonPlayer");
            }
          } else {
            if (model.isPlayerAtacking()) {
              player.getStyleClass().add("poisonedPlayerAttackingLeft");
            } else {
              player.getStyleClass().add("poisonedPlayerLeft");
            }
          }
          cell.getChildren().add(player);
        } else if (piece instanceof MegaPlayerWrap) {
          Rectangle player = new Rectangle(scale, scale);
          if (model.isPlayerFacingRight()) {
            if (model.isPlayerAtacking()) {
              player.getStyleClass().add("megaPlayerAttackingRight");
            } else {
              player.getStyleClass().add("megaPlayer");
            }
          } else {
            if (model.isPlayerAtacking()) {
              player.getStyleClass().add("megaPlayerAttackingLeft");
            } else {
              player.getStyleClass().add("megaPlayerLeft");
            }
          }
          cell.getChildren().add(player);
        }else {
          Rectangle empty = new Rectangle(scale, scale);
          empty.setVisible(false);
          cell.getChildren().add(empty);
        }
        grid.add(cell, j, i);
      }
    }

    grid.getStyleClass().add("gameGrid");
    gameScreen.setCenter(grid);

    //    StackPane.setAlignment(score, Pos.TOP_LEFT);
    root.getChildren().add(gameScreen);
    return root;
  }

  public Parent TitleScreenView() {
    ImageView TitleImage = new ImageView("TitleScreen.png");
    TitleImage.setFitWidth(model.getBoard().getWidth() * scale);
    TitleImage.setFitHeight(model.getBoard().getHeight() * scale);
    Label title = new Label("                                         ");
    title.getStyleClass().add("title");
    Label names = new Label("By. Daniel Acevedo Vega And Darwin Lemus");
    names.getStyleClass().add("score");
    Button start = new Button("             ");
    start.getStyleClass().add("start");
    Label highscore = new Label("Highscore: " + model.getHighScore());
    highscore.getStyleClass().add("highscore");
    Label lastScore = new Label("LastScore: " + model.getCurScore());
    lastScore.getStyleClass().add("lastscore");
    start.setOnAction(
        e -> {
          //      model.startGame();
          stage.getScene().setRoot(render());
          startRound();
        });
    VBox menu = new VBox(20, title,names, start, highscore, lastScore);
    menu.setAlignment(Pos.CENTER);

    StackPane lesgo = new StackPane(TitleImage, menu);
    return lesgo;
  }

  public Parent EndGameView() {
    //    ImageView TitleImage = new ImageView("TitleScreen.png");
    Pane gameScreen = (Pane) stage.getScene().getRoot();

    Pane GameOverScreen = new Pane();
    GameOverScreen.getStyleClass().add("gameOverScreen");
    Label gameOver = new Label("                                      ");
    Label highscore = new Label("Highscore: " + model.getHighScore());
    highscore.getStyleClass().add("highscore");
    Label lastScore = new Label("LastScore: " + model.getCurScore());
    lastScore.getStyleClass().add("lastscore");
    gameOver.getStyleClass().add("gameOver");
    Button TryAgain = new Button("              ");
    TryAgain.getStyleClass().add("tryAgain");
    Button close = new Button("              ");
    close.getStyleClass().add("close");

    TryAgain.setOnAction(
            e -> {
              //      model.startGame();
              startRound();
            });
    close.setOnAction(
            e -> {
              //      model.startGame();
              stage.close();
            });


    VBox endMenu = new VBox();
    endMenu.setSpacing(25);
    endMenu.setAlignment(Pos.CENTER);
    endMenu.getChildren().addAll(gameOver,highscore,lastScore, close, TryAgain);

    StackPane End = new StackPane();
    End.getChildren().add(gameScreen);
    End.getChildren().add(GameOverScreen);
    End.getChildren().add(endMenu);

    return End;
  }
  public Parent winnerView() {
    //    ImageView TitleImage = new ImageView("TitleScreen.png");
    Pane gameScreen = (Pane) stage.getScene().getRoot();

    Pane GameOverScreen = new Pane();
    GameOverScreen.getStyleClass().add("winnerScreen");
    Label gameOver = new Label("                  ");
    Label highscore = new Label("Highscore: " + model.getHighScore());
    highscore.getStyleClass().add("highscore");
    Label lastScore = new Label("LastScore: " + model.getCurScore());
    lastScore.getStyleClass().add("lastscore");
    gameOver.getStyleClass().add("You_Win");
    Button TryAgain = new Button("              ");
    TryAgain.getStyleClass().add("tryAgain");
    TryAgain.setOnAction(
            e -> {
              //      model.startGame();
              startRound();
            });
//    Button close = new Button("              ");
//    close.getStyleClass().add("close");

    VBox endMenu = new VBox();
    endMenu.setSpacing(25);
    endMenu.setAlignment(Pos.CENTER);
    endMenu.getChildren().addAll(gameOver,highscore,lastScore, TryAgain);

    StackPane End = new StackPane();
    End.getChildren().add(gameScreen);
    End.getChildren().add(GameOverScreen);
    End.getChildren().add(endMenu);

    return End;
  }

  public void startRound() {
    Pane gameScreen = (Pane) render();
    Label Count = new Label();
    Count.getStyleClass().add("count");
    StackPane root = new StackPane();
    root.getChildren().add(gameScreen);
    root.getChildren().add(Count);

    stage.getScene().setRoot(root);
    Timeline timeline =
        new Timeline(
            new KeyFrame(Duration.seconds(0), e -> Count.setText("3")),
            new KeyFrame(Duration.seconds(1), e -> Count.setText("2")),
            new KeyFrame(Duration.seconds(2), e -> Count.setText("1")),
            new KeyFrame(
                Duration.seconds(3),
                e -> {
                  Count.setText("FIGHT!");
                  Count.getStyleClass().add("fight");
                }));

    timeline.setOnFinished(
        e -> {
          root.getChildren().remove(Count);
          model.startGame();
        });

    // Start the animation
    timeline.play();
  }


  @Override
  public void update() {
    if (model.getStatus() == Model.STATUS.GAME_START) {
      stage.getScene().setRoot(TitleScreenView());
    } else if (model.getStatus() == Model.STATUS.IN_PROGRESS) {
      stage.getScene().setRoot(render());
    } else if (model.getStatus() == Model.STATUS.END_GAME_LOOSER) {
      stage.getScene().setRoot(EndGameView());
    }else if(model.getStatus() == Model.STATUS.END_GAME_WINNER){
      stage.getScene().setRoot(winnerView());
    }
  }
}




