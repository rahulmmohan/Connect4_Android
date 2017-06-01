package info.overrideandroid.connect4.ai;

import info.overrideandroid.connect4.rules.Player;
import info.overrideandroid.connect4.rules.Slot;

/**
 * Quite basic AI. Tries to add a new chip to the longest combination,
 * overlooks if it's not possible to finish this combination though.
 * Overlooks gaped combinations too. Overlook upper diagonals too.
 * Created by Rahul on 31/05/17.
 */

public class EasyAiLogic extends AiLogic {

    /** number of columns */
    protected final int cols;

    /** number of rows */
    protected final int rows;

    /*
    * Indices offsets
    */
    protected static final int X_L = -1;
    protected static final int X_R = 1;
    protected static final int Y_T = -1;
    protected static final int Y_B = 1;

    public EasyAiLogic(Slot[][] grid) {
        super(grid);
        cols = grid.length;
        rows = grid[0].length;
    }

    @Override
    public int run() {
        int col = -1;
        int com = -1;

        for(int i = 0; i < grid.length; ++i) {
            if(grid[i][0].player != 0) continue;
            int j = findRow(i);
            int a = analyse(i, j, 0); // analyse every possible column
            if(a > com) {
                com = a;
                col = i;
            }
        }
        if(com < 1) return super.run();
        return col;
    }

    /**
     * Find empty row in a column
     * @param col
     * @return
     */
    protected int findRow(int col) {
        int row = 0;
        while(row < rows - 1 && grid[col][row + Y_B].player == 0) row++;
        return row;
    }

    /**
     * Analyses the column, returns the number which represents the length
     * of the longest combination that will be created if the chip is placed
     * in this particular column.
     * @param col
     * @param row
     * @param com
     * @return
     */
    protected int analyse(int col, int row, int com) {
        return Math.max(com, countCombinations(col, row, Player.PLAYER2));
    }

    /**
     * Returns the longest combination that will be created if the chip is
     * placed in this particular column.
     * @param col
     * @param row
     * @param p
     * @return
     */
    protected int countCombinations(int col, int row, int p) {
        int com = 0;
        com = Math.max(com, combination(col+X_L,    row,        X_L, 0,   p));
        com = Math.max(com, combination(col+X_L,    row+Y_B,    X_L, Y_B, p));
        com = Math.max(com, combination(col,        row+Y_B,    0,   Y_B, p));
        com = Math.max(com, combination(col+X_R,    row+Y_B,    X_R, Y_B, p));
        com = Math.max(com, combination(col+X_R,    row,        X_R, 0,   p));
        return com;
    }

    /**
     * Calculates the combination length
     * @param x column
     * @param y row
     * @param i offset on X
     * @param j offset on Y
     * @return combination length.
     */
    protected int combination(int x, int y, int i, int j, int p) {
        if(x < 0 || x >= cols) return 0;
        if(y >= rows || y < 0) return 0;
        if(grid[x][y].player == p) {
            return combination(x + i, y + j, i, j, p) + 1;
        }
        return 0;
    }
}
