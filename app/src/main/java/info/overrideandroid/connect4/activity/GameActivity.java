package info.overrideandroid.connect4.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import info.overrideandroid.connect4.R;
import info.overrideandroid.connect4.board.BoardController;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.board.BoardView;

public class GameActivity extends AppCompatActivity {

    BoardController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView boardView = (BoardView)findViewById(R.id.gameView);
        gameController = new BoardController(this, boardView,getGameRules());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
    }

    private GameRules getGameRules() {
        GameRules gameRules = new GameRules();
        gameRules.setRule(GameRules.FIRST_TURN, GameRules.FirstTurn.PLAYER1);
        gameRules.setRule(GameRules.LEVEL, GameRules.Level.EASY);
        gameRules.setRule(GameRules.OPPONENT, GameRules.Opponent.PLAYER);
        gameRules.setRule(GameRules.DISC, GameRules.Disc.YELLOW);
        gameRules.setRule(GameRules.DISC2, GameRules.Disc.RED);
        return  gameRules;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
