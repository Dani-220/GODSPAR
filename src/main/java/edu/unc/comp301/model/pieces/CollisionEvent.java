package edu.unc.comp301.model.pieces;

public class CollisionEvent {
  private final int points;
  private final Result res;

  public CollisionEvent(int points, Result res) {
    this.points = points;
    this.res = res;
  }

  public int getPoints() {
    return points;
  }

  public Result getResults() {
    return res;
  }

  public enum Result {
    CONTINUE,
    GAME_OVER,
    NEXT_LEVEL
  }
}
