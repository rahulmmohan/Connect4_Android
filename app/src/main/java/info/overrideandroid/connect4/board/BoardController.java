package info.overrideandroid.connect4.board;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import info.overrideandroid.connect4.activity.GamePlayActivity;
import info.overrideandroid.connect4.ai.AiLogic;
import info.overrideandroid.connect4.ai.EasyAiLogic;
import info.overrideandroid.connect4.ai.HardAiLogic;
import info.overrideandroid.connect4.ai.NormalAiLogic;
import info.overrideandroid.connect4.board.BoardLogic.Outcome;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.rules.Player;
import info.overrideandroid.connect4.utils.Constants;

/**
 * Created by Rahul on 30/05/17.
 */

public class BoardController implements View.OnTouchListener {

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
    int grid[][] = new int[COLS][ROWS];

    /**
     * free cells in every column
     */
    int free[] = new int[COLS];

    /**
     * board logic (winning check)
     */
    private final BoardLogic logic = new BoardLogic(grid);

    /**
     * AI logic
     */
    private  AiLogic aiLogic;

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

    /** main thread handler */
    private final Handler handler = new Handler();

    private Context mContext;
    private BoardView mBoardView;

    /**
     * Game rules
     */
    private GameRules gameRules;
    private boolean aiTurn;

    public BoardController(Context context, BoardView boardView, GameRules gameRules) {
        this.mContext = context;
        this.gameRules = gameRules;
        this.mBoardView = boardView;
        initialize();
        if (mBoardView != null) {
            mBoardView.initialize(gameRules);
            mBoardView.setOnTouchListener(this);
        }
    }

    private void initialize() {
        playerTurn = gameRules.getRule(GameRules.FIRST_TURN);

        // unfinished the game
        finished = false;
        outcome = Outcome.NOTHING;

        // create AI if needed
        if (gameRules.getRule(GameRules.OPPONENT) == GameRules.Opponent.AI) {
            switch (gameRules.getRule(GameRules.LEVEL)) {
                case GameRules.Level.EASY:
                    aiLogic = new EasyAiLogic(grid);
                    break;
                case GameRules.Level.NORMAL:
                    aiLogic = new NormalAiLogic(grid);
                    break;
                case GameRules.Level.HARD:
                    aiLogic = new HardAiLogic(grid);
                    break;
                default:
                    aiLogic = null;
                    break;
            }
        } else aiLogic = null;

        // null the grid and free counter for every column
        for (int i = 0; i < COLS; ++i) {
            for (int j = 0; j < ROWS; ++j) {
                grid[i][j] = 0;
            }
            free[i] = ROWS;
        }

        // if it is a computer turn, go ahead with it
        if(playerTurn == GameRules.FirstTurn.PLAYER2 && aiLogic != null) aiTurn();
    }

    private void aiTurn() {
        if(finished) return;
        aiTurn = true;
        handler.postDelayed(ai, Constants.AI_DELAY);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP: {
                if(finished || aiTurn ) return true;
                int col = mBoardView.colAtX(event.getX());
                selectColumn(col);
            }
        }
        return true;
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
        mBoardView.dropDisc(column, free[column], playerTurn);

        // set who put the disc
        grid[column][free[column]] = playerTurn;

        // switch player
        playerTurn = playerTurn == Player.PLAYER1
                ? Player.PLAYER2 : Player.PLAYER1;

        // check if someone has won
        checkForWin();
        aiTurn = false;
        // AI move if needed
        if(playerTurn == Player.PLAYER2 && aiLogic != null) aiTurn();
    }

    private void checkForWin() {
        outcome = logic.checkWin();

        if (outcome != Outcome.NOTHING) {
            finished = true;
            mBoardView.showWinStatus(outcome);
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
    private Runnable ai = new Runnable(){
        @Override
        public void run() {
            selectColumn(aiLogic.run());
        }
    };

}
