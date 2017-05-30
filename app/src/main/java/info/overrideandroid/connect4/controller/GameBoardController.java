package info.overrideandroid.connect4.controller;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.view.GameView;

/**
 * Created by Rahul on 30/05/17.
 */

public class GameBoardController implements View.OnTouchListener {

    /** number of columns */
    public static final int COLS = 7;

    /** number of rows */
    public static final int ROWS = 6;

    /** grid, contains 0 for empty cell or player ID */
    int grid[][] = new int[COLS][ROWS];

    /** free cells in every column */
    int free[] = new int[COLS];

    private Context mContext;
    private GameView mGameView;

    /** Game rules */
    private GameRules gameRules;

    public GameBoardController(Context context,GameView gameView,GameRules gameRules) {
        this.mContext = context;
        this.gameRules = gameRules;
        this.mGameView = gameView;
        initialize();
        if (mGameView != null) {
            mGameView.initialize(gameRules);
            mGameView.setOnTouchListener(this);
        }
    }
    private void initialize(){
        // null the grid and free counter for every column
        for(int i = 0; i < COLS; ++i) {
            for(int j = 0; j < ROWS; ++j) {
                grid[i][j] = 0;
            }
            free[i] = ROWS;
        }
    }
    public void startGame() {
        mGameView.setPlayer1(gameRules);
        mGameView.setPlayer2(gameRules);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP: {
                int col = mGameView.colAtX(event.getX());
                free[col]--;
                if (col != -1)
                    mGameView.dropDisc(col,free[col]);
            }
        }
        return true;
    }

}
