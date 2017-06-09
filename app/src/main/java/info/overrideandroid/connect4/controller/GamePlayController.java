package info.overrideandroid.connect4.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import info.overrideandroid.connect4.BuildConfig;
import info.overrideandroid.connect4.activity.GamePlayActivity;
import info.overrideandroid.connect4.ai.AiPlayer;
import info.overrideandroid.connect4.board.BoardLogic;
import info.overrideandroid.connect4.board.BoardLogic.Outcome;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.rules.Player;
import info.overrideandroid.connect4.utils.Constants;
import info.overrideandroid.connect4.view.BoardView;

/**
 * Created by Rahul on 30/05/17.
 */

public class GamePlayController implements View.OnClickListener {

    private static final String TAG = GamePlayController.class.getName();
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
    private final BoardLogic logic = new BoardLogic(grid, free);

    /**
     * Instance of Ai player
     */
    @Nullable
    private AiPlayer aiPlayer;

    /**
     * current status
     */
    @NonNull
    private Outcome outcome = Outcome.NOTHING;

    /**
     * if the game is finished
     */
    private boolean finished = true;

    /**
     * player turn
     */
    private int playerTurn;

    private final Context mContext;

    private final BoardView mBoardView;

    /**
     * Game rules
     */
    @NonNull
    private final GameRules gameRules;

    private boolean aiTurn;

    public GamePlayController(Context context, BoardView boardView, @NonNull GameRules gameRules) {
        this.mContext = context;
        this.gameRules = gameRules;
        this.mBoardView = boardView;
        initialize();
        if (mBoardView != null) {
            mBoardView.initialize(this, gameRules);
        }
    }

    /**
     * initialize game board with default values and player turn
     */
    private void initialize() {
        playerTurn = gameRules.getRule(GameRules.FIRST_TURN);

        // unfinished the game
        finished = false;
        outcome = Outcome.NOTHING;
        // null the grid and free counter for every column
        for (int j = 0; j < COLS; ++j) {
            for (int i = 0; i < ROWS; ++i) {
                grid[i][j] = 0;
            }
            free[j] = ROWS;
        }

        // create AI if needed
        if (gameRules.getRule(GameRules.OPPONENT) == GameRules.Opponent.AI) {
            aiPlayer = new AiPlayer(logic);
            switch (gameRules.getRule(GameRules.LEVEL)) {
                case GameRules.Level.EASY:
                    aiPlayer.setDifficulty(4);
                    break;
                case GameRules.Level.NORMAL:
                    aiPlayer.setDifficulty(7);
                    break;
                case GameRules.Level.HARD:
                    aiPlayer.setDifficulty(10);
                    break;
                default:
                    aiPlayer = null;
                    break;
            }
        } else {
            aiPlayer = null;
        }

        // if it is a computer turn, go ahead with it
        if (playerTurn == GameRules.FirstTurn.PLAYER2 && aiPlayer != null) aiTurn();
    }

    /**
     * ai turn goes here
     */
    private void aiTurn() {

        if (finished) return;
        new AiTask().execute();
    }


    /**
     * drop disc into a column
     *
     * @param column
     */
    private void selectColumn(int column) {
        if (free[column] == 0) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "full column or game is finished");
            }
            return;
        }

        logic.placeMove(column, playerTurn);

        // put disc
        mBoardView.dropDisc(free[column], column, playerTurn);

        // switch player
        playerTurn = playerTurn == Player.PLAYER1
                ? Player.PLAYER2 : Player.PLAYER1;

        // check if someone has won
        checkForWin();
        //   board.displayBoard();
        aiTurn = false;
        if (BuildConfig.DEBUG) {
            logic.displayBoard();
            Log.e(TAG, "Turn: " + playerTurn);
        }
        // AI move if needed
        if (playerTurn == Player.PLAYER2 && aiPlayer != null) aiTurn();
    }

    /**
     * execute board logic for win check and update ui
     */
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

    /**
     * restart game by resetting values and UI
     */
    public void restartGame() {
        initialize();
        mBoardView.resetBoard();
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Game restarted");
        }
    }


    @Override
    public void onClick(@NonNull View view) {
        if (finished || aiTurn) return;
        int col = mBoardView.colAtX(view.getX());
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Selected column: " + col);
        }
        selectColumn(col);
    }

    /**
     * run ai movement in background thread
     */
    class AiTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            aiTurn = true;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                Thread.currentThread();
                Thread.sleep(Constants.AI_DELAY);
            } catch (InterruptedException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
            return aiPlayer.getColumn();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            selectColumn(integer);
        }
    }
}
