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
    private int[] heights = new int[7];

    public AiBoardMove(int[][] board, BoardLogic logic, int[] free) {
        boardLogic = logic;
        this.board = board;
        for (int j = 0; j < 7; ++j) {
            heights[j] = 6;
        }

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
//        if (!isLegalMove(column)) {
//            return;
//        }
//        for (int i = 0; i < 6; i++) {
//            if (board[i][column] == 0) {
//                board[heights[column]][column] = player;
//                break;
//            }
//        }
        if (heights[column] >0) {
            board[heights[column]-1][column] = player;
            heights[column]--;
        }
    }

    /**
     * uundu previous move
     *
     * @param column
     */
    public void undoMove(int column) {
        if (heights[column] < 6) {
            heights[column]++;
            board[heights[column]-1][column] = 0;

        }
    }

    /**
     * Get the height the counters in a specific column
     * @param index index of the column to check
     * @return the height of the column
     */
    public int columnHeight(int index) {
        return heights[index];
    }


    /**
     * Check if a counter at a specific board position is involved in a match
     * @param column column the counter is in
     * @param row row the counter is in
     * @return true if the counter is involved in a match, false otherwise
     */
    public boolean checkMatch(int column, int row) {
        int horizontal_matches = 0;
        int vertical_matches = 0;
        int forward_diagonal_matches = 0;
        int backward_diagonal_matches = 0;

        // horizontal matches
        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column+i, row)) {
                horizontal_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column-i, row)) {
                horizontal_matches++;
            } else break;
        }

        // vertical matches
        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column, row+i)) {
                vertical_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column, row-i)) {
                vertical_matches++;
            } else break;
        }

        // backward diagonal matches ( \ )
        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column+i, row-i)) {
                backward_diagonal_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column-i, row+i)) {
                backward_diagonal_matches++;
            } else break;
        }

        // forward diagonal matches ( / )
        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column+i, row+i)) {
                forward_diagonal_matches++;
            } else break;
        }

        for (int i = 1; i < 4; i++) {
            if (matchingCounters(column, row, column-i, row-i)) {
                forward_diagonal_matches++;
            } else break;
        }

        if (horizontal_matches >= 4-1
                || vertical_matches >= 4-1
                || forward_diagonal_matches >= 4-1
                || backward_diagonal_matches >= 4-1) {
            return true;
        }
        return false;
    }

    /**
     * Check if a counter at a specific position matches a counter at a
     * different specific position
     * @param columnA column the first counter is in
     * @param rowA row the first counter is in
     * @param columnB column the second counter is in
     * @param rowB row the second counter is in
     * @return
     */
    private boolean matchingCounters(int columnA, int rowA, int columnB, int rowB){
        // return false if either set of coordinates falls out of bounds
        if (columnA < 0 || columnA >= 7
                || rowA < 0 || rowA >= 6
                || columnB < 0 || columnB >= 7
                || rowB < 0 || rowB >= 6) {
            return false;
        }
        if (board[rowA][columnA]== 0 || board[rowB][columnB] == 0) {
            return false;
        }
        if (board[rowA][columnA]!=board[rowB][columnB]) {
            return false;
        }
        return true;
    }

    public void displayBoard(){
        System.out.println();
        for(int i=0;i<=5;++i){
            for(int j=0;j<=6;++j){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
