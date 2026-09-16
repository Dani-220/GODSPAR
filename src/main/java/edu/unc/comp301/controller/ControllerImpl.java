package edu.unc.comp301.controller;


import edu.unc.comp301.model.Model;

public class ControllerImpl implements Controller {
  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  public void moveUp() {
    model.moveUp();
  }

  public void moveLeft() {
    model.moveLeft();
  }

  public void moveRight() {
    model.moveRight();
  }

  public void startGame() {
    model.startGame();
  }

  @Override
  public void specialAttack() {
    model.specialAttack();
  }

  @Override
  public void attack() {
    model.attack();
  }
}
