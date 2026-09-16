package edu.unc.comp301.pieces;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.pieces.requirements.*;
import  edu.unc.comp301.pieces.requirements.Player;


public  class PlayerImpl extends APiece implements MovablePiece, Player {
    private volatile double health ;
    private int damageMultiplier = 1;
    private int megaEnergyBar = 0;
    private Orientation orientation = Orientation.LEFT;


    public PlayerImpl(){
        super("Player","resources/Greninja.png");
        super.setPosn(new Posn(0,0));
        this.health = 100;
    }

//    public abstract boolean specialMove();

    @Override
    public CollisionEvent collide(Piece other) {
        return switch (other) {
            case Treasure treasure -> new CollisionEvent(1, CollisionEvent.Result.NEXT_ROUND);
            case Exit exit -> new CollisionEvent(0, CollisionEvent.Result.NEXT_LEVEL);
            case Enemy enemy -> new CollisionEvent(0, CollisionEvent.Result.DO_NOT_PASS);
            case Obstacle obstacle -> new CollisionEvent(0, CollisionEvent.Result.DO_NOT_PASS);
            case MegaStone megaStone -> new CollisionEvent(0, CollisionEvent.Result.APPLY_BUFF);
            case Poison poison -> new CollisionEvent(0, CollisionEvent.Result.DEBUFF);
            case null, default -> new CollisionEvent(0, CollisionEvent.Result.CONTINUE);
        };
    }



    @Override
    public int attack() {
        if (this.megaEnergyBar >= 100) {
            setDamageMultiplier(5);
        } else {
            setDamageMultiplier(1);
        }
        int damage = 10 * this.damageMultiplier;
        return damage;
    }

    @Override
    public  int specialAttack() {
        if (this.megaEnergyBar == 100) {
            this.setDamageMultiplier(2);
        }
        return 15 * this.damageMultiplier;
    }



    @Override
    public void setPosn(Posn posn) {
        if (posn.getCol() < this.getPosn().getCol()) {
            this.orientation = Orientation.LEFT;
        }  else if (posn.getCol() > this.getPosn().getCol()) {
            this.orientation = Orientation.RIGHT;
        } else if (posn.getRow() < this.getPosn().getRow()) {
            this.orientation = Orientation.UP;
        }
        this.position = posn;
    }



    public void addMegaEnery(int addedEnergyBar) {
        this.megaEnergyBar += addedEnergyBar;
    }
    public void setMegaEnergyBar(int megaEnergyBar) {this.megaEnergyBar = megaEnergyBar;}
    public int getMegaEnergyBar() {return this.megaEnergyBar;}
    public double getDamageMultiplier() {
        return damageMultiplier;
    }
    public void setDamageMultiplier(int damageMultiplier) {this.damageMultiplier = damageMultiplier;}
    public void setHealth(double health) {
        this.health = health;
    }
    public void takeDamage(int damage) {
        if (damage > this.health) {
            health = 0;
            return;
        }
        this.health -= damage;
    }
    public double getHealth() {
        return health;
    }
    public Orientation getOrientation() {return orientation;}

    @Override
    public Player unWrap(){
        return this;
    }

}

