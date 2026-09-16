package edu.unc.comp301.pieces;


import edu.unc.comp301.model.board.Posn;

public abstract class APiece implements Piece {
  private final String name;
  private String resourcePath;
  protected Posn position;

  public APiece(String name, String resourcePath) {
    this.name = name;
    this.resourcePath = resourcePath;
  }

  @Override
  public String getResourcePath() {
    return resourcePath;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public Posn getPosn() {
    return position;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  @Override
  public void setPosn(Posn posn) {
    this.position = posn;
  }

  public int getCol(){return this.getPosn().getCol();}
  public int getRow(){return this.getPosn().getRow();}
}
