package info.overrideandroid.connect4.board;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import info.overrideandroid.connect4.BuildConfig;
import info.overrideandroid.connect4.controller.GamePlayController;
import info.overrideandroid.connect4.rules.Player;

/**
 * Created by Rahul on 30/05/17.
 */

public class BoardLogic {
    private static final String TAG = GamePlayController.class.getName();

    /**
     * Possible outcomes
     */
    public enum Outcome {
        NOTHING, DRAW, PLAYER1_WINS, PLAYER2_WINS
    }

    /**
     * flag to mark mDraw
     */
    private boolean mDraw;

    /**
     * Reference to player win
     */
    private int mCellValue;

    /**
     * Reference to a main mGrid
     */
    @NonNull
    private final int[][] mGrid;

    /**
     * number of columns in the mGrid
     */
    public final int numCols;

    /**
     * number of rows in the mGrid
     */
    private final int numRows;

    /**
     * player win starting index
     */
    private int p, q;

    /**
     * winner direction
     */
    private int WIN_X = 0;
    private int WIN_Y = 0;

    /**
     * reference to mFree cells in every column
     */
    private final int[] mFree;

    /**
     * win counter
     */
    private static final int COUNTERS_IN_MATCH = 4;

    /**
     * Initialise members
     *
     * @param grid reference to board grid
     * @param free reference to column height
     */
    public BoardLogic(@NonNull int[][] grid, int[] free) {
        mGrid = grid;
        numRows = grid.length;
        numCols = grid[0].length;
        this.mFree = free;
    }

    @NonNull
    public Outcome checkWin() {
        mDraw = true;
        mCellValue = 0;
        if (horizontalCheck() || verticalCheck() ||
                ascendingDiagonalCheck() || descendingDiagonalCheck()) {
            return mCellValue == Player.PLAYER1 ? Outcome.PLAYER1_WINS : Outcome.PLAYER2_WINS;
        }
        // nobody won, return mDraw if it is, nothing if it's not
        return mDraw ? Outcome.DRAW : Outcome.NOTHING;
    }


    private boolean horizontalCheck() {
        // horizontalCheck
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols - 3; j++) {
                mCellValue = mGrid[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && mGrid[i][j + 1] == mCellValue && mGrid[i][j + 2] == mCellValue && mGrid[i][j + 3] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "Horizontal check pass");
                    }
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
        for (int j = 0; j < numCols; j++) {
            for (int i = 0; i < numRows - 3; i++) {
                mCellValue = mGrid[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && mGrid[i + 1][j] == mCellValue && mGrid[i + 2][j] == mCellValue && mGrid[i + 3][j] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "Vertical check pass");
                    }
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
        for (int i = 3; i < numRows; i++) {
            for (int j = 0; j < numCols - 3; j++) {
                mCellValue = mGrid[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && mGrid[i - 1][j + 1] == mCellValue && mGrid[i - 2][j + 2] == mCellValue && mGrid[i - 3][j + 3] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "ascendingDiagonal check pass");
                    }
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
        for (int i = 3; i < numRows; i++) {
            for (int j = 3; j < numCols; j++) {
                mCellValue = mGrid[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && mGrid[i - 1][j - 1] == mCellValue && mGrid[i - 2][j - 2] == mCellValue && mGrid[i - 3][j - 3] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "descendingDiagonal check pass");
                    }
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
     * @param cells cell mGrid
     * @return winning move discs
     */
    @NonNull
    public ArrayList<ImageView> getWinDiscs(ImageView[][] cells) {
        ArrayList<ImageView> combination = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            combination.add(cells[p + WIN_Y * i][q + WIN_X * i]);
        }
        return combination;
    }

    /**
     * placing a Move on the mGrid
     */

    public void placeMove(int column, int player) {
        if (mFree[column] > 0) {
            mGrid[mFree[column] - 1][column] = player;
            mFree[column]--;
        }
    }

    /**
     * undo previous move
     *
     * @param column column to undo move
     */
    public void undoMove(int column) {
        if (mFree[column] < numRows) {
            mFree[column]++;
            mGrid[mFree[column] - 1][column] = 0;

        }
    }

    /**
     * Get the height the counters in a specific column
     *
     * @param index index of the column to check
     * @return the height of the column
     */
    public int columnHeight(int index) {
        return mFree[index];
    }


    /**
     * Check if a counter at a specific mGrid position is involved in a match
     *
     * @param column column the counter is in
     * @param row    row the counter is in
     * @return true if the counter is involved in a match, false otherwise
     */
    public boolean checkMatch(int column, int row) {
        int horizontal_matches = 0;
        int vertical_matches = 0;
        int forward_diagonal_matches = 0;
        int backward_diagonal_matches = 0;

        // horizontal matches
        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column + i, row)) {
                horizontal_matches++;
            } else break;
        }

        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column - i, row)) {
                horizontal_matches++;
            } else break;
        }

        // vertical matches
        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column, row + i)) {
                vertical_matches++;
            } else break;
        }

        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column, row - i)) {
                vertical_matches++;
            } else break;
        }

        // backward diagonal matches ( \ )
        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column + i, row - i)) {
                backward_diagonal_matches++;
            } else break;
        }

        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column - i, row + i)) {
                backward_diagonal_matches++;
            } else break;
        }

        // forward diagonal matches ( / )
        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column + i, row + i)) {
                forward_diagonal_matches++;
            } else break;
        }

        for (int i = 1; i < COUNTERS_IN_MATCH; i++) {
            if (matchingCounters(column, row, column - i, row - i)) {
                forward_diagonal_matches++;
            } else break;
        }

        return horizontal_matches >= COUNTERS_IN_MATCH - 1
                || vertical_matches >= COUNTERS_IN_MATCH - 1
                || forward_diagonal_matches >= COUNTERS_IN_MATCH - 1
                || backward_diagonal_matches >= COUNTERS_IN_MATCH - 1;
    }

    /**
     * Check if a counter at a specific position matches a counter at a
     * different specific position
     *
     * @param columnA column the first counter is in
     * @param rowA    row the first counter is in
     * @param columnB column the second counter is in
     * @param rowB    row the second counter is in
     * @return true if there is matching
     */
    private boolean matchingCounters(int columnA, int rowA, int columnB, int rowB) {
        // return false if either set of coordinates falls out of bounds
        if (columnA < 0 || columnA >= numCols
                || rowA < 0 || rowA >= numRows
                || columnB < 0 || columnB >= numCols
                || rowB < 0 || rowB >= numRows) {
            return false;
        }
        return !(mGrid[rowA][columnA] == 0 || mGrid[rowB][columnB] == 0) && mGrid[rowA][columnA] == mGrid[rowB][columnB];
    }

    /**
     * display board status
     */
    public void displayBoard() {
        System.out.println();
        for (int i = 0; i <= 5; ++i) {
            for (int j = 0; j <= 6; ++j) {
                System.out.print(mGrid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
