package info.overrideandroid.connect4.ai;

import info.overrideandroid.connect4.board.BoardLogic;

/**
 * Created by Rahul on 03/06/17.
 */

public class AiPlayer {

    AiBoardMove board;
    public AiPlayer(AiBoardMove b) {
        this.board = b;
    }

    public int getColumn() {
        return chooseMove(1,2,board,-10000,10000,2).getColumn();
    }

    /**
     * Recursive minimax method
     * @param player player taking their move at this level of the tree
     * @param opponent player NOT taking their move at this level of the tree
     * @param board the current board at this level of the tree
     * @param alpha the best score this AIPlayer object can achieve
     * @param beta the best score the other player can achieve
     * @param depth the current depth in the tree, 0 is a leaf node
     * @return
     */
    public Move chooseMove(int player, int opponent, AiBoardMove board,
                           int alpha, int beta, int depth) {
        Move best = new Move(-1, player == 1 ? alpha : beta);
        // go from left to right until you find a non-full column
        for (int i = 0; i < 7; i++) {
            if (board.columnHeight(i)>0) {
                // add a counter to that column, then check for win-condition
                board.placeMove(i,player);
                // score this move and all its children
                int score = 0;
                if (board.checkMatch(i, board.columnHeight(i))) {
                    // this move is a winning move for the player
                    score = player==1 ? 1 : -1;
                } else if (depth != 1) {
                    // this move wasn't a win or a draw, so go to the next move
                    score = chooseMove(opponent, player, board, alpha, beta,
                            depth+1).getScore();
                }
//                // -- debug output
//                if (Config.DEBUG) {
//                    debug(player, board, depth, alpha, beta, score);
//                }score
                board.undoMove(i);
                // if this move beats this player's best move so far, record it
                if (player==1 && score > best.getScore()) {
                    best = new Move(i, score);
                    alpha = score;
                } else if (player==2 && score < best.getScore()) {
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
