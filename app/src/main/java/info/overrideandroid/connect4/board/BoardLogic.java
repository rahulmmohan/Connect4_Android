package info.overrideandroid.connect4.board;

import android.widget.ImageView;

import java.util.ArrayList;

import info.overrideandroid.connect4.rules.Player;
import info.overrideandroid.connect4.rules.Slot;

/**
 * Created by Rahul on 30/05/17.
 */

public class BoardLogic {
    /**
     * Possible outcomes
     */
    public enum Outcome {
        NOTHING, DRAW, PLAYER1_WINS, PLAYER2_WINS;
    }

    /**
     * flag to mark draw
     */
    private boolean draw;

    /**
     * Reference to player win
     */
    private int cellValue;

    /**
     * Reference to a main grid
     */
    private final Slot[][] grid;

    /**
     * number of columns in the grid
     */
    private final int numCols;

    /**
     * number of rows in the grid
     */
    private final int numRows;

    /**
     * player win starting index
     */
    private int p, q;

    /**
     * winner direction
     */
    int WIN_X = 0, WIN_Y = 0;

    /**
     * player win starting index
     */

    /**
     * Initialise members
     *
     * @param _grid
     */
    public BoardLogic(Slot[][] _grid) {
        grid = _grid;
        numCols = _grid.length;
        numRows = _grid[0].length;

    }

    public Outcome checkWin() {
        draw = true;
        cellValue = 0;
        if (horizontalCheck() || verticalCheck() ||
                ascendingDiagonalCheck() || descendingDiagonalCheck()) {
            return cellValue == Player.PLAYER1 ? Outcome.PLAYER1_WINS : Outcome.PLAYER2_WINS;
        }
        // nobody won, return draw if it is, nothing if it's not
        return draw ? Outcome.DRAW : Outcome.NOTHING;
    }


    private boolean horizontalCheck() {
        // horizontalCheck
        for (int j = 0; j < numRows - 3; j++) {
            for (int i = 0; i < numCols; i++) {
                cellValue = grid[i][j].player;
                if (cellValue == 0) draw = false;
                if (cellValue != 0 && grid[i][j + 1].player == cellValue && grid[i][j + 2].player == cellValue && grid[i][j + 3].player == cellValue) {
                    p = i;
                    q = j;
                    WIN_X = 1;
                    WIN_Y = 0;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalCheck() {
        // verticalCheck
        for (int i = 0; i < numCols - 3; i++) {
            for (int j = 0; j < this.numRows; j++) {
                cellValue = grid[i][j].player;
                if (cellValue == 0) draw = false;
                if (cellValue != 0 && grid[i + 1][j].player == cellValue && grid[i + 2][j].player == cellValue && grid[i + 3][j].player == cellValue) {
                    p = i;
                    q = j;
                    WIN_X = 0;
                    WIN_Y = 1;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ascendingDiagonalCheck() {
        // ascendingDiagonalCheck
        for (int i = 3; i < numCols; i++) {
            for (int j = 0; j < numRows - 3; j++) {
                cellValue = grid[i][j].player;
                if (cellValue == 0) draw = false;
                if (cellValue != 0 && grid[i - 1][j + 1].player == cellValue && grid[i - 2][j + 2].player == cellValue && grid[i - 3][j + 3].player == cellValue) {
                    p = i;
                    q = j;
                    WIN_X = 1;
                    WIN_Y = -1;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean descendingDiagonalCheck() {
        // descendingDiagonalCheck
        for (int i = 3; i < numCols; i++) {
            for (int j = 3; j < numRows; j++) {
                cellValue = grid[i][j].player;
                if (cellValue == 0) draw = false;
                if (cellValue != 0 && grid[i - 1][j - 1].player == cellValue && grid[i - 2][j - 2].player == cellValue && grid[i - 3][j - 3].player == cellValue) {
                    p = i;
                    q = j;
                    WIN_X = -1;
                    WIN_Y = -1;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns sprites of a winning combination
     *
     * @param
     * @param cells
     * @return
     */
    public ArrayList<ImageView> getWinDiscs(ImageView[][] cells) {
        ArrayList<ImageView> combination = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            combination.add(cells[p + WIN_X * i][q + WIN_Y * i]);
        }
        return combination;
    }

}
