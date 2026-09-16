package edu.unc.comp301.pieces;

import edu.unc.comp301.pieces.requirements.Buff;
import edu.unc.comp301.pieces.requirements.Player;

public class MegaStone extends APiece implements Buff {
    public MegaStone() {
        super("MegaStone", "src/main/resources/MegaStone.png");
    }


    public void applyBuff(Player player) {
        player.addMegaEnery(34);
    }

}
