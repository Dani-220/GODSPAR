package edu.unc.comp301.pieces;

import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.pieces.requirements.Buff;
import edu.unc.comp301.pieces.requirements.Player;

public class PoisonedPlayerWrap extends APiece implements  Player {
    private Player p;
    private boolean isPoisoned;

    public PoisonedPlayerWrap(Player p) {
    super(p.getName(),"PoisonedGraninja.png");
        this.p = p;
        isPoisoned = false;
    }





    public void applyBuff() {
        Thread poisenedPlayer = new Thread(() ->{
            isPoisoned = true;
            double previousPlayerHealth = p.getHealth();
            p.setHealth(20);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (p.getHealth() >= 0) {
                p.setHealth(previousPlayerHealth);
                isPoisoned = false;
            }
        });
        poisenedPlayer.start();

    }

    public boolean isPoisoned() {
        return isPoisoned;
    }

    public Player unWrap() {
        return p;
    }


    @Override
    public int getRow() {
        return p.getRow();
    }

    @Override
    public int getCol() {
        return p.getCol();
    }

    @Override
    public CollisionEvent collide(Piece piece) {
        return p.collide(piece);
    }

    @Override
    public void takeDamage(int damage) {
        p.takeDamage(damage);
    }

    @Override
    public int getMegaEnergyBar() {
        return p.getMegaEnergyBar();
    }

    @Override
    public int attack() {
        return p.attack();
    }

    @Override
    public int specialAttack() {
        return p.specialAttack();
    }

    @Override
    public void setDamageMultiplier(int damageMultiplier) {
        p.setDamageMultiplier(damageMultiplier);
    }

    @Override
    public double getDamageMultiplier() {
        return p.getDamageMultiplier();
    }

    @Override
    public void setHealth(double health) {
        p.setHealth(health);
    }

    @Override
    public double getHealth() {
        return p.getHealth();
    }

    @Override
    public void setMegaEnergyBar(int addedEnergyBar) {
        p.setMegaEnergyBar(addedEnergyBar);
    }

    @Override
    public void addMegaEnery(int addedEnergyBar) {
        p.addMegaEnery(addedEnergyBar);
    }

    @Override
    public Orientation getOrientation() {
       return p.getOrientation();
    }

    @Override
    public String getName() {
        return p.getName();
    }

    @Override
    public Posn getPosn() {
        return p.getPosn();
    }

    @Override
    public void setPosn(Posn posn) {
        p.setPosn(posn);
    }

    @Override
    public String getResourcePath() {
        return p.getResourcePath();
    }
}
