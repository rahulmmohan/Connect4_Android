package info.overrideandroid.connect4.ai;

import android.support.annotation.NonNull;

import info.overrideandroid.connect4.board.BoardLogic;
import info.overrideandroid.connect4.rules.Player;

/**
 * Created by Rahul on 03/06/17.
 */

public class AiPlayer {

    private final BoardLogic boardLogic;
    private int maxDepth;

    public AiPlayer(BoardLogic boardLogic) {
        this.boardLogic = boardLogic;
    }

    public void setDifficulty(int depth) {
        this.maxDepth = depth;
    }

    public int getColumn() {
        return chooseMove(Player.PLAYER2, Player.PLAYER1, -10000, 10000, maxDepth).getColumn();
    }

    /**
     * Recursive minimax method
     *
     * @param player   player taking their move at this level of the tree
     * @param opponent player NOT taking their move at this level of the tree
     * @param alpha    the best score this AIPlayer object can achieve
     * @param beta     the best score the other player can achieve
     * @param depth    the current depth in the tree, 0 is a leaf node
     * @return
     */
    @NonNull
    private Move chooseMove(int player, int opponent,
                            int alpha, int beta, int depth) {
        Move best = new Move(-1, player == Player.PLAYER2 ? alpha : beta);
        // go from left to right until you find a non-full column
        for (int i = 0; i < 7; i++) {
            if (boardLogic.columnHeight(i) > 0) {
                // add a counter to that column, then check for win-condition
                boardLogic.placeMove(i, player);
                // score this move and all its children
                int score = 0;
                if (boardLogic.checkMatch(i, boardLogic.columnHeight(i))) {
                    // this move is a winning move for the player
                    score = player == Player.PLAYER2 ? 1 : -1;
                } else if (depth != 1) {
                    // this move wasn't a win or a draw, so go to the next move
                    score = chooseMove(opponent, player, alpha, beta,
                            depth - 1).getScore();
                }
                boardLogic.undoMove(i);
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
        return best;
    }

}
