package info.overrideandroid.connect4.controller;

import android.support.annotation.NonNull;
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
    private final MenuView mMenuView;
    private final MenuControllerListener mListener;
    private final GameRules mGameRules = new GameRules();

    public GameMenuController(MenuControllerListener mListener, MenuView mMenuView) {
        this.mMenuView = mMenuView;
        this.mListener = mListener;
        this.mMenuView.setupMenu(getDefaultGameRules());
    }


    @Override
    public void onClick(View v) {
        mListener.onPlay(mGameRules);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.play_with_ai:
                mGameRules.setRule(GameRules.OPPONENT, Opponent.AI);
                mMenuView.setPlayWith(mGameRules.getRule(GameRules.OPPONENT));
                mMenuView.setDifficulty(mGameRules.getRule(GameRules.LEVEL));
                break;
            case R.id.play_with_friend:
                mGameRules.setRule(GameRules.OPPONENT, Opponent.PLAYER);
                mMenuView.setPlayWith(mGameRules.getRule(GameRules.OPPONENT));
                mMenuView.setDifficulty(mGameRules.getRule(GameRules.LEVEL));
                break;
            case R.id.disc_red:
                mGameRules.setRule(GameRules.DISC, Disc.RED);
                mGameRules.setRule(GameRules.DISC2, Disc.YELLOW);
                break;
            case R.id.disc_yellow:
                mGameRules.setRule(GameRules.DISC2, Disc.RED);
                mGameRules.setRule(GameRules.DISC, Disc.YELLOW);
                break;
            case R.id.first_turn_player1:
                mGameRules.setRule(GameRules.FIRST_TURN, FirstTurn.PLAYER1);
                break;
            case R.id.first_turn_player2:
                mGameRules.setRule(GameRules.FIRST_TURN, FirstTurn.PLAYER2);
                break;

        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mGameRules.setRule(GameRules.LEVEL, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @NonNull
    private GameRules getDefaultGameRules() {
        mGameRules.setRule(GameRules.FIRST_TURN, FirstTurn.PLAYER1);
        mGameRules.setRule(GameRules.LEVEL, GameRules.Level.HARD);
        mGameRules.setRule(GameRules.OPPONENT, Opponent.AI);
        mGameRules.setRule(GameRules.DISC, GameRules.Disc.RED);
        mGameRules.setRule(GameRules.DISC2, GameRules.Disc.YELLOW);
        return mGameRules;
    }


    public interface MenuControllerListener {
        /**
         * The method is called by menu controller to inform the
         * menu Activity about to start game
         */
        void onPlay(GameRules gameRules);
    }
}
