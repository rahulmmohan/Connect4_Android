package info.overrideandroid.connect4.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import info.overrideandroid.connect4.R;
import info.overrideandroid.connect4.controller.GameMenuController;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.rules.GameRules.*;
/**
 * Created by Rahul on 31/05/17.
 */

public class MenuView extends RelativeLayout {
    public MenuView(Context context) {
        super(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Listener for menu events
     * @param gameMenuController game menu controller instance
     */
    public void setListeners(GameMenuController gameMenuController) {

        findViewById(R.id.play).setOnClickListener(gameMenuController);
        ((RadioGroup) findViewById(R.id.play_with)).setOnCheckedChangeListener(gameMenuController);
        ((RadioGroup) findViewById(R.id.player1_disc)).setOnCheckedChangeListener(gameMenuController);
        ((RadioGroup) findViewById(R.id.first_turn)).setOnCheckedChangeListener(gameMenuController);
        ((SeekBar) findViewById(R.id.difficulty)).setOnSeekBarChangeListener(gameMenuController);

    }

    /**
     * change opponent
     * @param opponent game rule - first turn value
     */
    public void setPlayWith(int opponent){
        if(opponent == Opponent.AI){
            ((RadioGroup) findViewById(R.id.play_with)).check(R.id.play_with_ai);
            findViewById(R.id.level).setVisibility(VISIBLE);
            ((RadioButton) findViewById(R.id.first_turn_player2)).setText(getContext().getString(R.string.opponent_ai));

        }else {
            ((RadioGroup) findViewById(R.id.play_with)).check(R.id.play_with_friend);
            findViewById(R.id.level).setVisibility(INVISIBLE);
            ((RadioButton) findViewById(R.id.first_turn_player2)).setText(getContext().getString(R.string.opponent_player));

        }
    }

    /**
     * change first turn
     * @param firstTurn game rule - first turn value
     */
    private void setFirstTurn(int firstTurn){
        ((RadioGroup) findViewById(R.id.first_turn)).check(firstTurn == FirstTurn.PLAYER1 ? R.id.first_turn_player1 : R.id.first_turn_player2);
    }

    /**
     * change disc selection
     * @param PLayer1Disc game rule - Player1 disc value
     */
    private void setPLayer1Disc(int PLayer1Disc){
        ((RadioGroup) findViewById(R.id.player1_disc)).check(PLayer1Disc == Disc.RED ? R.id.disc_red : R.id.disc_yellow);
    }

    /**
     * change difficulty ui
     * @param difficulty game rule - difficulty value
     */
    public void setDifficulty(int difficulty){
        ((SeekBar) findViewById(R.id.difficulty)).setProgress(difficulty);
    }

    /**
     * set menu with default rules
     * @param defaultGameRules default game rule
     */
    public void setupMenu(@NonNull GameRules defaultGameRules) {
        setPlayWith(defaultGameRules.getRule(GameRules.OPPONENT));
        setFirstTurn(defaultGameRules.getRule(GameRules.FIRST_TURN));
        setPLayer1Disc(defaultGameRules.getRule(GameRules.DISC));
        setDifficulty(defaultGameRules.getRule(GameRules.LEVEL));

    }
}
