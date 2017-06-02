package info.overrideandroid.connect4.ai;

import android.util.Log;

import info.overrideandroid.connect4.board.BoardLogic;
import info.overrideandroid.connect4.rules.Slot;

/**
 * Created by Rahul on 01/06/17.
 */

public class Board {
        Slot[][] board;
        BoardLogic boardLogic;

        public Board(Slot[][] board,BoardLogic logic) {
            boardLogic =logic;
            this.board = board;
        }
    public boolean isLegalMove(int column) {
            return board[0][column].player == 0;
        }

        //Placing a Move on the board
        public boolean placeMove(int column, int player) {
            if (!isLegalMove(column)) {
                return false;
            }
            for (int i = 5; i >= 0; --i) {
                if (board[i][column].player == 0) {
                    board[i][column].player = player;
                    return true;
                }
            }
            return false;
        }

        public void undoMove(int column) {
            for (int i = 0; i <= 5; ++i) {
                if (board[i][column].player != 0) {
                    board[i][column].player = 0;
                    break;
                }
            }
        }
    }
