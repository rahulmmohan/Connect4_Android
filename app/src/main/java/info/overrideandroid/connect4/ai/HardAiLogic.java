package info.overrideandroid.connect4.ai;

import info.overrideandroid.connect4.rules.Player;

/**
 * Hard AI. Anticipates a move from above, doesn't miss the gaps, starts in
 * the centre. Pretty much unbeatable unless you do two combinations of 3 with
 * a single gap in between.
 * Created by Rahul on 31/05/17.
 */

public class HardAiLogic extends NormalAiLogic {

    public HardAiLogic(int[][] grid) {
        super(grid);
    }

    @Override
    public int run() {
        int col = cols / 2; // start in the centre
        int alpha = 0;

        for (int i = 0; i < grid.length; ++i) {
            if (grid[i][0] != 0) continue;
            int j = findRow(i);
            int beta = analyse(i, j);
            if (beta > alpha) {
                alpha = beta;
                col = i;
            }
        }
        // lets be sure the move is valid
        return grid[col][0] == 0 ? col : super.run();
    }

    private int analyse(int x, int y) {
        int m = count(x, y, -1);

        if (y > 0 && m > count(x, y - 1, Player.PLAYER1)) return m;

        return y == 0 ? m : 0;
    }

    private int count(int x, int y, int p) {
        int com = 0;
        com = Math.max(com, countCom(x, y, X_L, 0, p));
        com = Math.max(com, countCom(x, y, X_L, Y_T, p));
        com = Math.max(com, countCom(x, y, X_L, Y_B, p));
        com = Math.max(com, countCom(x, y, 0, Y_T, p));
        return com;
    }

    private int countCom(int x, int y, int i, int j, int p) {
        int com = 0;

        int i2 = -i;
        int j2 = -j;

        if (p == -1) {
            // count both player combinations possible (from both sides)
            int temp = 0;
            temp += combination(x + i, y + j, i, j, Player.PLAYER1);
            temp += combination(x + i2, y + j2, i2, j2, Player.PLAYER1);
            com = Math.max(temp, com);
            temp += combination(x + i, y + j, i, j, Player.PLAYER2);
            temp += combination(x + i2, y + j2, i2, j2, Player.PLAYER2);
            com = Math.max(temp, com);
        } else { // count one player combinations (from both sides)
            int temp = 0;
            temp += combination(x + i, y + j, i, j, p);
            temp += combination(x + i2, y + j2, i2, j2, p);
            com = Math.max(temp, com);
        }

        return com;
    }


}
