package info.overrideandroid.connect4.ai;

import info.overrideandroid.connect4.board.BoardLogic;
import info.overrideandroid.connect4.rules.Slot;

/**
 * Created by Rahul on 01/06/17.
 * <p>
 * used to plan ai moves,check each move is legal,or undo moves
 */

public class AiBoardMove {
    final int[][] board;
    public final BoardLogic boardLogic;

    public AiBoardMove(int[][] board, BoardLogic logic) {
        boardLogic = logic;
        this.board = board;
    }

    /**
     * check move is leagal
     *
     * @param column
     * @return
     */
    public boolean isLegalMove(int column) {
        return board[0][column] == 0;
    }

    /**
     *  placing a Move on the board
     */

    public void placeMove(int column, int player) {
        if (!isLegalMove(column)) {
            return;
        }
        for (int i = 5; i >= 0; --i) {
            if (board[i][column] == 0) {
                board[i][column] = player;
                return;
            }
        }
    }

    /**
     * uundu previous move
     *
     * @param column
     */
    public void undoMove(int column) {
        for (int i = 0; i <= 5; ++i) {
            if (board[i][column] != 0) {
                board[i][column] = 0;
                break;
            }
        }
    }
}
