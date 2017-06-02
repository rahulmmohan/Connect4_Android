package info.overrideandroid.connect4.ai;

import android.util.Log;

import info.overrideandroid.connect4.board.BoardLogic;
import info.overrideandroid.connect4.rules.Player;

/**
 * Created by Rahul on 01/06/17.
 */

public class AiLogicNew {

    boolean firstMove = true;
    private AiBoardMove b;
    private int nextMoveLocation = -1;
    private int maxDepth = 9;

    public AiLogicNew(AiBoardMove b) {
        firstMove = true;
        this.b = b;
    }

    /**
     * set difficulty in maximum depth to analyse
     *
     * @param level
     */
    public void setDifficulty(int level) {
        this.maxDepth = level;
    }

    /**
     * run AI move
     * @return
     */
    public int getAIMove() {
        /**
         * place disc on center column if first move
         */
        if (firstMove) {
            firstMove = false;
            return 3;
        }
        nextMoveLocation = -1;
        minMax(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return nextMoveLocation;
    }

    /**
     *
     * @param depth
     * @param turn
     * @param alpha
     * @param beta
     * @return
     */
    public int minMax(int depth, int turn, int alpha, int beta) {

        if (beta <= alpha) {
            if (turn == 1) return Integer.MAX_VALUE;
            else return Integer.MIN_VALUE;
        }
        BoardLogic.Outcome gameResult = b.boardLogic.checkWin();

        if (gameResult == BoardLogic.Outcome.PLAYER2_WINS) return Integer.MAX_VALUE / 2;
        else if (gameResult == BoardLogic.Outcome.PLAYER1_WINS) return Integer.MIN_VALUE / 2;
        else if (gameResult == BoardLogic.Outcome.DRAW) return 0;

        if (depth == maxDepth) return evaluateBoard(b);

        int maxScore = Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;

        for (int j = 0; j <= 6; ++j) {

            int currentScore = 0;

            if (!b.isLegalMove(j)) continue;

            if (turn == 1) {
                b.placeMove(j, Player.PLAYER2);
                currentScore = minMax(depth + 1, 2, alpha, beta);

                if (depth == 0) {
                    Log.d("Score for location ", j + " = " + currentScore);
                    if (currentScore > maxScore) nextMoveLocation = j;
                    if (currentScore == Integer.MAX_VALUE / 2) {
                        b.undoMove(j);
                        break;
                    }
                }

                maxScore = Math.max(currentScore, maxScore);

                alpha = Math.max(currentScore, alpha);
            } else if (turn == 2) {
                b.placeMove(j, Player.PLAYER1);
                currentScore = minMax(depth + 1, 1, alpha, beta);
                minScore = Math.min(currentScore, minScore);

                beta = Math.min(currentScore, beta);
            }
            b.undoMove(j);
            if (currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
        }
        return turn == 1 ? maxScore : minScore;
    }

    //Evaluate board favorableness for AI
    public int evaluateBoard(AiBoardMove b) {

        int aiScore = 1;
        int score = 0;
        int blanks = 0;
        int k = 0, moreMoves = 0;
        for (int i = 5; i >= 0; --i) {
            for (int j = 0; j <= 6; ++j) {

                if (b.board[i][j] == 0 || b.board[i][j] == Player.PLAYER1) continue;

                if (j <= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.board[i][j + k] == Player.PLAYER2) aiScore++;
                        else if (b.board[i][j + k] == Player.PLAYER1) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }

                    moreMoves = 0;
                    if (blanks > 0)
                        for (int c = 1; c < 4; ++c) {
                            int column = j + c;
                            for (int m = i; m <= 5; m++) {
                                if (b.board[m][column] == 0) moreMoves++;
                                else break;
                            }
                        }

                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (i >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.board[i - k][j] == Player.PLAYER2) aiScore++;
                        else if (b.board[i - k][j] == Player.PLAYER1) {
                            aiScore = 0;
                            break;
                        }
                    }
                    moreMoves = 0;

                    if (aiScore > 0) {
                        int column = j;
                        for (int m = i - k + 1; m <= i - 1; m++) {
                            if (b.board[m][column] == 0) moreMoves++;
                            else break;
                        }
                    }
                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (j >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.board[i][j - k] == Player.PLAYER2) aiScore++;
                        else if (b.board[i][j - k] == Player.PLAYER1) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0)
                        for (int c = 1; c < 4; ++c) {
                            int column = j - c;
                            for (int m = i; m <= 5; m++) {
                                if (b.board[m][column] == 0) moreMoves++;
                                else break;
                            }
                        }

                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (j <= 3 && i >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.board[i - k][j + k] == Player.PLAYER2) aiScore++;
                        else if (b.board[i - k][j + k] == Player.PLAYER1) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0) {
                        for (int c = 1; c < 4; ++c) {
                            int column = j + c, row = i - c;
                            for (int m = row; m <= 5; ++m) {
                                if (b.board[m][column] == 0) moreMoves++;
                                else if (b.board[m][column] == Player.PLAYER2) ;
                                else break;
                            }
                        }
                        if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                        aiScore = 1;
                        blanks = 0;
                    }
                }

                if (i >= 3 && j >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.board[i - k][j - k] == Player.PLAYER2) aiScore++;
                        else if (b.board[i - k][j - k] == Player.PLAYER1) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0) {
                        for (int c = 1; c < 4; ++c) {
                            int column = j - c, row = i - c;
                            for (int m = row; m <= 5; ++m) {
                                if (b.board[m][column] == 0) moreMoves++;
                                else if (b.board[m][column] == Player.PLAYER2) ;
                                else break;
                            }
                        }
                        if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                        aiScore = 1;
                        blanks = 0;
                    }
                }
            }
        }
        return score;
    }

    int calculateScore(int aiScore, int moreMoves) {
        int moveScore = 4 - moreMoves;
        if (aiScore == 0) return 0;
        else if (aiScore == 1) return moveScore;
        else if (aiScore == 2) return 10 * moveScore;
        else if (aiScore == 3) return 100 * moveScore;
        else return 1000;
    }


}