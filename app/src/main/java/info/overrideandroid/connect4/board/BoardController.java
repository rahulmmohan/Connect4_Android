package info.overrideandroid.connect4.board;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import info.overrideandroid.connect4.activity.GamePlayActivity;
import info.overrideandroid.connect4.ai.AiLogic;
import info.overrideandroid.connect4.ai.AiLogicNew;
import info.overrideandroid.connect4.ai.AiBoardMove;
import info.overrideandroid.connect4.board.BoardLogic.Outcome;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.rules.Player;
import info.overrideandroid.connect4.rules.Slot;
import info.overrideandroid.connect4.utils.Constants;

/**
 * Created by Rahul on 30/05/17.
 */

public class BoardController implements View.OnClickListener {

    private static final String TAG = BoardController.class.getName();
    /**
     * number of columns
     */
    public static final int COLS = 7;

    /**
     * number of rows
     */
    public static final int ROWS = 6;

    /**
     * grid, contains 0 for empty cell or player ID
     */
    private final int[][] grid = new int[ROWS][COLS];

    /**
     * free cells in every column
     */
    private final int[] free = new int[COLS];

    /**
     * board logic (winning check)
     */
    private final BoardLogic logic = new BoardLogic(grid);

    /**
     * AI logic
     */
    private AiLogic aiLogic;

    /**
     * current status
     */
    private Outcome outcome = Outcome.NOTHING;

    /**
     * if the game is finished
     */
    private boolean finished = true;

    /**
     * player turn
     */
    private int playerTurn;

    /**
     * main thread handler
     */
    private final Handler handler = new Handler();

    private final Context mContext;
    private final BoardView mBoardView;

    /**
     * Game rules
     */
    private final GameRules gameRules;
    private boolean aiTurn;
    private AiLogicNew aiLogicNew;
    private AiBoardMove board;

    public BoardController(Context context, BoardView boardView, GameRules gameRules) {
        this.mContext = context;
        this.gameRules = gameRules;
        this.mBoardView = boardView;
        initialize();
        if (mBoardView != null) {
            mBoardView.initialize(this,gameRules);
        }
    }

    private void initialize() {
        playerTurn = gameRules.getRule(GameRules.FIRST_TURN);

        // unfinished the game
        finished = false;
        outcome = Outcome.NOTHING;

        // create AI if needed
        if (gameRules.getRule(GameRules.OPPONENT) == GameRules.Opponent.AI) {
            board = new AiBoardMove(grid, logic);
            aiLogicNew = new AiLogicNew(board);
            switch (gameRules.getRule(GameRules.LEVEL)) {
                case GameRules.Level.EASY:
                    aiLogicNew.setDifficulty(2);
                    break;
                case GameRules.Level.NORMAL:
                    aiLogicNew.setDifficulty(4);
                    break;
                case GameRules.Level.HARD:
                    aiLogicNew.setDifficulty(7);
                    break;
                default:
                    aiLogicNew = null;
                    break;
            }
        } else aiLogicNew = null;

        // null the grid and free counter for every column
        for (int j = 0; j < COLS; ++j) {
            for (int i = 0; i < ROWS; ++i) {
                grid[i][j] = 0;
            }
            free[j] = ROWS;
        }

        // if it is a computer turn, go ahead with it
        if (playerTurn == GameRules.FirstTurn.PLAYER2 && aiLogicNew != null) aiTurn();
    }

    private void aiTurn() {
        if (finished) return;
        aiTurn = true;
        handler.postDelayed(ai, Constants.AI_DELAY);
    }


    /**
     * drop disc into a column
     *
     * @param column
     */
    private void selectColumn(int column) {
        if (free[column] == 0) {
            if (Constants.DEBUG) {
                Log.e(TAG, "full column or game is finished");
            }
            return;
        }

        // decrement free space in this column
        free[column]--;

        // put disc
        mBoardView.dropDisc(free[column], column, playerTurn);

        // set who put the disc
        grid[free[column]][column] = playerTurn;
        // switch player
        playerTurn = playerTurn == Player.PLAYER1
                ? Player.PLAYER2 : Player.PLAYER1;

        // check if someone has won
        checkForWin();
        aiTurn = false;
        // AI move if needed
        if (playerTurn == Player.PLAYER2 && aiLogicNew != null) aiTurn();
    }

    private void checkForWin() {
        outcome = logic.checkWin();

        if (outcome != Outcome.NOTHING) {
            finished = true;
            ArrayList<ImageView> winDiscs = logic.getWinDiscs(mBoardView.getCells());
            mBoardView.showWinStatus(outcome, winDiscs);

        } else {
            mBoardView.togglePlayer(playerTurn);
        }
    }

    public void exitGame() {
        ((GamePlayActivity) mContext).finish();
    }

    public void restartGame() {
        initialize();
        mBoardView.resetBoard();

    }

    /**
     * Runs AI after a delay
     */
    private Runnable ai = new Runnable() {
        @Override
        public void run() {
            selectColumn(aiLogicNew.getAIMove());
        }
    };


    @Override
    public void onClick(View view) {
        if (finished || aiTurn) return;
        int col = mBoardView.colAtX(view.getX());
        selectColumn(col);
    }
}
