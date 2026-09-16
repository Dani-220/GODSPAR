package edu.unc.comp301.pieces.Enemies;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.pieces.*;
import edu.unc.comp301.pieces.requirements.Enemy;
import edu.unc.comp301.pieces.requirements.Obstacle;
import edu.unc.comp301.pieces.requirements.Player;

public abstract class EnemyImpl extends APiece implements MovablePiece, Enemy {
    private double health;
    private Orientation orientation;
    private final int damage;


    protected EnemyImpl(String name, String resourcePath, int damage) {
        super(name,resourcePath);
        this.health = 100;
        this.damage = damage;
        setPosn(null);
    }

    public static EnemyImpl create(difficulty difficulty) {
        if (difficulty == EnemyImpl.difficulty.EASY) {
            return new Machamp();
        } else if (difficulty == EnemyImpl.difficulty.MEDIUM) {
            return new SirFetched();
        } else if (difficulty == EnemyImpl.difficulty.HARD) {
            return new Groudon();
        } else {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public String findPlayer(Player player){
        int pCol = player.getPosn().getCol();
        int pRow = player.getPosn().getRow();
        Posn enemyposn = getPosn();
        int ecol = enemyposn.getCol();
        int erow = enemyposn.getRow();
        int dx = pCol -ecol;
        int dy = pRow - erow;

        double distance = Math.sqrt(dx * dx + dy * dy);
        if(distance < 2){
            player.takeDamage(damage);
            return "Attack";
        }
        if (distance > 1) {
            if(dx >0){
                return "Right";
            } else if(dx < 0){
                return "Left";
            }
            if(dy > 0){
                return "Down";
            } else if(dy < 0){
                return "Up";
            }
        }
        return "";
    }


    @Override
    public CollisionEvent collide(Piece other) {
        return switch (other) {
            case PlayerImpl player -> new CollisionEvent(0, CollisionEvent.Result.ATTACK);
            case Obstacle obstacle -> new CollisionEvent(0, CollisionEvent.Result.DO_NOT_PASS);
            case null, default -> new CollisionEvent(0, CollisionEvent.Result.CONTINUE);
        };
    }



    public void damageHealth(double damage) {
        if (damage > health) {
            this.health = 0;
            return;
        }
        this.health  -= damage;
    }

    public void setOrientation(Posn posn) {
        if (posn.getCol() < this.getPosn().getCol()) {
            this.orientation = Orientation.LEFT;
        }  else if (posn.getCol() > this.getPosn().getCol()) {
            this.orientation = Orientation.RIGHT;
        } else if (posn.getRow() < this.getPosn().getRow()) {
            this.orientation = Orientation.UP;
        }
    }
    public Orientation getOrientation() {return orientation;}
    public double getHealth (){return this.health;}
    public void setHealth(double health) {this.health = health;}

    public enum difficulty {
        EASY, MEDIUM, HARD
    }



}
