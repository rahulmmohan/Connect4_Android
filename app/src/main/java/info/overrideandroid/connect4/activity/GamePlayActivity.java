package info.overrideandroid.connect4.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import info.overrideandroid.connect4.R;
import info.overrideandroid.connect4.board.BoardController;
import info.overrideandroid.connect4.board.BoardView;
import info.overrideandroid.connect4.rules.GameRules;

public class GamePlayActivity extends AppCompatActivity {

    private BoardController gameController;
    private final GameRules gameRules = new GameRules();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView boardView = (BoardView) findViewById(R.id.gameView);
        gameRules.importFrom(getIntent().getExtras());
        gameController = new BoardController(this, boardView, gameRules);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitPlay();
                break;
            case R.id.restart:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.app_name))
                        .setMessage(R.string.reset_game)
                        .setCancelable(false)
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gameController.restartGame();
                            }
                        }).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exitPlay() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(R.string.back)
                .setCancelable(false)
                .setNegativeButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameController.exitGame();
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        exitPlay();
    }
}
