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

import static java.lang.Thread.sleep;

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
     * mGrid, contains 0 for empty cell or player ID
     */
    private final int[][] mGrid = new int[ROWS][COLS];

    /**
     * mFree cells in every column
     */
    private final int[] mFree = new int[COLS];

    /**
     * board mBoardLogic (winning check)
     */
    private final BoardLogic mBoardLogic = new BoardLogic(mGrid, mFree);

    /**
     * Instance of Ai player
     */
    @Nullable
    private AiPlayer mAiPlayer;

    /**
     * current status
     */
    @NonNull
    private Outcome mOutcome = Outcome.NOTHING;

    /**
     * if the game is mFinished
     */
    private boolean mFinished = true;

    /**
     * player turn
     */
    private int mPlayerTurn;

    private final Context mContext;

    private final BoardView mBoardView;

    /**
     * Game rules
     */
    @NonNull
    private final GameRules mGameRules;

    private boolean mAiTurn;

    public GamePlayController(Context context, BoardView boardView, @NonNull GameRules mGameRules) {
        this.mContext = context;
        this.mGameRules = mGameRules;
        this.mBoardView = boardView;
        initialize();
        if (mBoardView != null) {
            mBoardView.initialize(this, mGameRules);
        }
    }

    /**
     * initialize game board with default values and player turn
     */
    private void initialize() {
        mPlayerTurn = mGameRules.getRule(GameRules.FIRST_TURN);

        // unfinished the game
        mFinished = false;
        mOutcome = Outcome.NOTHING;
        // null the mGrid and mFree counter for every column
        for (int j = 0; j < COLS; ++j) {
            for (int i = 0; i < ROWS; ++i) {
                mGrid[i][j] = 0;
            }
            mFree[j] = ROWS;
        }

        // create AI if needed
        if (mGameRules.getRule(GameRules.OPPONENT) == GameRules.Opponent.AI) {
            mAiPlayer = new AiPlayer(mBoardLogic);
            switch (mGameRules.getRule(GameRules.LEVEL)) {
                case GameRules.Level.EASY:
                    mAiPlayer.setDifficulty(4);
                    break;
                case GameRules.Level.NORMAL:
                    mAiPlayer.setDifficulty(7);
                    break;
                case GameRules.Level.HARD:
                    mAiPlayer.setDifficulty(10);
                    break;
                default:
                    mAiPlayer = null;
                    break;
            }
        } else {
            mAiPlayer = null;
        }

        // if it is a computer turn, go ahead with it
        if (mPlayerTurn == GameRules.FirstTurn.PLAYER2 && mAiPlayer != null) aiTurn();
    }

    /**
     * ai turn goes here
     */
    private void aiTurn() {

        if (mFinished) return;
        new AiTask().execute();
    }


    /**
     * drop disc into a column
     *
     * @param column column to drop disc
     */
    private void selectColumn(int column) {
        if (mFree[column] == 0) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "full column or game is mFinished");
            }
            return;
        }

        mBoardLogic.placeMove(column, mPlayerTurn);

        // put disc
        mBoardView.dropDisc(mFree[column], column, mPlayerTurn);

        // switch player
        mPlayerTurn = mPlayerTurn == Player.PLAYER1
                ? Player.PLAYER2 : Player.PLAYER1;

        // check if someone has won
        checkForWin();
        //   board.displayBoard();
        mAiTurn = false;
        if (BuildConfig.DEBUG) {
            mBoardLogic.displayBoard();
            Log.e(TAG, "Turn: " + mPlayerTurn);
        }
        // AI move if needed
        if (mPlayerTurn == Player.PLAYER2 && mAiPlayer != null) aiTurn();
    }

    /**
     * execute board mBoardLogic for win check and update ui
     */
    private void checkForWin() {
        mOutcome = mBoardLogic.checkWin();

        if (mOutcome != Outcome.NOTHING) {
            mFinished = true;
            ArrayList<ImageView> winDiscs = mBoardLogic.getWinDiscs(mBoardView.getCells());
            mBoardView.showWinStatus(mOutcome, winDiscs);

        } else {
            mBoardView.togglePlayer(mPlayerTurn);
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
        if (mFinished || mAiTurn) return;
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
            mAiTurn = true;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                Thread.currentThread();
                sleep(Constants.AI_DELAY);
            } catch (InterruptedException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
            return mAiPlayer.getColumn();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            selectColumn(integer);
        }
    }
}
