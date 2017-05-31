package info.overrideandroid.connect4.activity;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import info.overrideandroid.connect4.R;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.rules.GameRules.Disc;
import info.overrideandroid.connect4.rules.GameRules.FirstTurn;
import info.overrideandroid.connect4.rules.GameRules.Opponent;
import info.overrideandroid.connect4.view.MenuView;

/**
 * Created by Rahul on 31/05/17.
 */

public class GameMenuController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    private MenuView menuView;
    private MenuControllerListener listener;
    private GameRules gameRules = new GameRules();

    public GameMenuController(MenuControllerListener listener, MenuView menuView) {
        this.menuView = menuView;
        this.listener = listener;
        this.menuView.setupMenu(getDefaultGameRules());
    }


    @Override
    public void onClick(View v) {
        listener.onPlay(gameRules);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.play_with_ai:
                gameRules.setRule(GameRules.OPPONENT, Opponent.AI);
                menuView.setPlayWith(gameRules.getRule(GameRules.OPPONENT));
                menuView.setDificulty(gameRules.getRule(GameRules.LEVEL));
                break;
            case R.id.play_with_friend:
                gameRules.setRule(GameRules.OPPONENT, Opponent.PLAYER);
                menuView.setPlayWith(gameRules.getRule(GameRules.OPPONENT));
                menuView.setDificulty(gameRules.getRule(GameRules.LEVEL));
                break;
            case R.id.disc_red:
                gameRules.setRule(GameRules.DISC, Disc.RED);
                gameRules.setRule(GameRules.DISC2, Disc.YELLOW);
                break;
            case R.id.disc_yellow:
                gameRules.setRule(GameRules.DISC2, Disc.RED);
                gameRules.setRule(GameRules.DISC, Disc.YELLOW);
                break;
            case R.id.first_turn_player1:
                gameRules.setRule(GameRules.FIRST_TURN, FirstTurn.PLAYER1);
                break;
            case R.id.first_turn_player2:
                gameRules.setRule(GameRules.FIRST_TURN, FirstTurn.PLAYER2);
                break;

        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        gameRules.setRule(GameRules.LEVEL, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private GameRules getDefaultGameRules() {
        gameRules.setRule(GameRules.FIRST_TURN, FirstTurn.PLAYER1);
        gameRules.setRule(GameRules.LEVEL, GameRules.Level.NORMAL);
        gameRules.setRule(GameRules.OPPONENT, Opponent.PLAYER);
        gameRules.setRule(GameRules.DISC, GameRules.Disc.RED);
        gameRules.setRule(GameRules.DISC2, GameRules.Disc.YELLOW);
        return gameRules;
    }


    public interface MenuControllerListener {
        /**
         * The method is called by menu controller to inform the
         * menu Activity about to start game
         */
        void onPlay(GameRules gameRules);
    }
}
