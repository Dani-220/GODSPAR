package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.controller.ControllerImpl;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.ModelImpl;
import edu.unc.comp301.model.board.BoardImpl;
import edu.unc.comp301.pieces.Piece;
import edu.unc.comp301.pieces.Platform;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class AppLauncher extends Application {
  private final Set<KeyCode> pressedKeys = new HashSet<>();
  @Override
  public void start(Stage stage) {
    Piece[][] level = {
            {null, null, null, null, null, null,null,null,null, null, null, null, null, null,null,null},
            {null, null, null, null, null, null,null,null,null, null, null, null, null, null,null,null},
            {null, null, null, null, null, null,null,null,null, null, null, null, null, null,null,null},
            {null, null, null, null, null, null,null,null,null, null, null, null, null, null,null,null},
            {null, null, null, null, null, null,null,null,null, null, null, null, null, null,null,null},
            {null, null, null, null, null, null,null,null,null, null, null, null, null, null,null,null},
            {null, new Platform("Platform"), null, null, new Platform("Platform"), null,null,new Platform("Platform"),null, null, new Platform("Platform"), null, null, new Platform("Platform"),null,null},
            {null, null, null, null, null, null,null,null,null, null, null, null, null, null,null,null},
            {new Platform("Floor"), new Platform("Floor"), new Platform("Floor"),
            new Platform("Floor"), new Platform("Floor"), new Platform("Floor"),
            new Platform("Floor"),new Platform("Floor"),new Platform("Floor"),
            new Platform("Floor"), new Platform("Floor"), new Platform("Floor"),
            new Platform("Floor"), new Platform("Floor"),new Platform("Floor"),new Platform("Floor")},

    };
    BoardImpl level1 = new BoardImpl(level);

    //MVC Architecture
    Model model = new ModelImpl(level1);
    Controller controller = new ControllerImpl(model);
    View view = new View(controller,model,stage);
    model.addView(view);


    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    double width = primaryScreenBounds.getWidth();
    double height = primaryScreenBounds.getHeight();

    Scene scene = new Scene(view.TitleScreenView(), width, height);
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);

      scene.setOnKeyPressed(event -> {
        pressedKeys.add(event.getCode());

        if (pressedKeys.contains(KeyCode.W)) {
            if(model.getStatus() == Model.STATUS.IN_PROGRESS){
              controller.moveUp();
            }pressedKeys.remove(KeyCode.W);

          } else if (pressedKeys.contains(KeyCode.A)) {
            if(model.getStatus() == Model.STATUS.IN_PROGRESS){
              controller.moveLeft();
            }pressedKeys.remove(KeyCode.A);
          } else if(pressedKeys.contains(KeyCode.D)){
            if(model.getStatus() == Model.STATUS.IN_PROGRESS){
              controller.moveRight();
              }pressedKeys.remove(KeyCode.D);
          } else if(pressedKeys.contains(KeyCode.E)){
            if(model.getStatus() == Model.STATUS.IN_PROGRESS){
              controller.attack();
             } pressedKeys.remove(KeyCode.E);
          } else if (pressedKeys.contains(KeyCode.F)){
            if(model.getStatus() == Model.STATUS.IN_PROGRESS){
              controller.specialAttack();
              }pressedKeys.remove(KeyCode.F);
          }
      });
    stage.setFullScreen(true);
    stage.show();
  }
}
