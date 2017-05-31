package info.overrideandroid.connect4.board;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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

    private Context mContext;
    private BoardView mBoardView;

    /**
     * Game rules
     */
    private GameRules gameRules;

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

        // null the grid and free counter for every column
        for (int i = 0; i < COLS; ++i) {
            for (int j = 0; j < ROWS; ++j) {
                grid[i][j] = 0;
            }
            free[i] = ROWS;
        }
    }

    public void startGame() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP: {
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

        // put token
        mBoardView.dropDisc(column, free[column], playerTurn);

        // set who put the token
        grid[column][free[column]] = playerTurn;

        // switch player
        playerTurn = playerTurn == Player.PLAYER1
                ? Player.PLAYER2 : Player.PLAYER1;

        // check if someone has won
        checkForWin();

        // AI move if needed
        //if(playerTurn == Player.PLAYER2 && ai != null) aiTurn();
    }

    private void checkForWin() {
        outcome = logic.checkWin();

        if (outcome != Outcome.NOTHING) {
            finished = true;
            mBoardView.showWinStatus(outcome);
        }
        else {
            mBoardView.togglePlayer(playerTurn);
        }
    }

    public void exitGame() {
    }

    public void restartGame() {
        initialize();
        mBoardView.resetBoard();

    }
}
