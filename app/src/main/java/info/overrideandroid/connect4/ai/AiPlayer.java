package info.overrideandroid.connect4.ai;

import android.support.annotation.NonNull;
import android.util.Log;

import info.overrideandroid.connect4.BuildConfig;
import info.overrideandroid.connect4.board.BoardLogic;
import info.overrideandroid.connect4.controller.GamePlayController;
import info.overrideandroid.connect4.rules.Player;

/**
 * Created by Rahul on 03/06/17.
 */

public class AiPlayer {
    private static final String TAG = GamePlayController.class.getName();

    private final BoardLogic mBoardLogic;

    private int mMaxDepth;

    public AiPlayer(BoardLogic boardLogic) {
        this.mBoardLogic = boardLogic;
    }

    /**
     *  set difficulty of playing with ai
     * @param depth maximum depth ai will search for best move
     */
    public void setDifficulty(int depth) {
        this.mMaxDepth = depth;
    }

    /**
     * run ai move
     * @return column to put AI disc
     */
    public int getColumn() {
        return chooseMove(Player.PLAYER2, Player.PLAYER1, -10000, 10000, mMaxDepth).getColumn();
    }

    /**
     * Recursive minmax method
     *
     * @param player   player taking their move at this level of the tree
     * @param opponent player NOT taking their move at this level of the tree
     * @param alpha    the best score this AIPlayer object can achieve
     * @param beta     the best score the other player can achieve
     * @param depth    the current depth in the tree, 0 is a leaf node
     * @return best move
     */
    @NonNull
    private Move chooseMove(int player, int opponent,
                            int alpha, int beta, int depth) {
        Move best = new Move(-1, player == Player.PLAYER2 ? alpha : beta);
        // go from left to right until you find a non-full column
        for (int i = 0; i < mBoardLogic.numCols; i++) {
            if (mBoardLogic.columnHeight(i) > 0) {
                // add a counter to that column, then check for win-condition
                mBoardLogic.placeMove(i, player);
                // score this move and all its children
                int score = 0;
                if (mBoardLogic.checkMatch(i, mBoardLogic.columnHeight(i))) {
                    // this move is a winning move for the player
                    score = player == Player.PLAYER2 ? 1 : -1;
                } else if (depth != 1) {
                    // this move wasn't a win or a draw, so go to the next move
                    score = chooseMove(opponent, player, alpha, beta,
                            depth - 1).getScore();
                }
                mBoardLogic.undoMove(i);
                // if this move beats this player's best move so far, record it
                if (player == Player.PLAYER2 && score > best.getScore()) {
                    best = new Move(i, score);
                    alpha = score;
                } else if (player == Player.PLAYER1 && score < best.getScore()) {
                    best = new Move(i, score);
                    beta = score;
                }
                // don't continue with this branch, we've already found better
                if (alpha >= beta) {
                    return best;
                }
            }
        }
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "best move "+best.getColumn());
        }
        return best;
    }

}
