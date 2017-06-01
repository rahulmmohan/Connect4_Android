package info.overrideandroid.connect4.ai;

import info.overrideandroid.connect4.rules.Player;
import info.overrideandroid.connect4.rules.Slot;

/**
 * Slightly modified easy AI, does the same thing for the opponent to
 * determine if it is useful to block his combination.
 * Created by Rahul on 31/05/17.
 */

public class NormalAiLogic extends EasyAiLogic {

    public NormalAiLogic(Slot[][] grid) {
        super(grid);
    }

    @Override
    public int run() {
        return super.run();
    }

    @Override
    protected int analyse(int col, int row, int com) {
        com = countCombinations(col, row, Player.PLAYER1);
        return super.analyse(col, row, com);
    }
}
