package info.overrideandroid.connect4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import info.overrideandroid.connect4.R;
import info.overrideandroid.connect4.activity.GameMenuController;
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

    public void setListeners(GameMenuController gameMenuController) {

        findViewById(R.id.play).setOnClickListener(gameMenuController);
        ((RadioGroup) findViewById(R.id.play_with)).setOnCheckedChangeListener(gameMenuController);
        ((RadioGroup) findViewById(R.id.player1_disc)).setOnCheckedChangeListener(gameMenuController);
        ((RadioGroup) findViewById(R.id.first_turn)).setOnCheckedChangeListener(gameMenuController);
        ((SeekBar) findViewById(R.id.difficulty)).setOnSeekBarChangeListener(gameMenuController);

    }

    public void setPlayWith(int rule){
        if(rule == Opponent.AI){
            ((RadioGroup) findViewById(R.id.play_with)).check(R.id.play_with_ai);
            findViewById(R.id.level).setVisibility(VISIBLE);
            ((RadioButton) findViewById(R.id.first_turn_player2)).setText(getContext().getString(R.string.opponent_ai));

        }else {
            ((RadioGroup) findViewById(R.id.play_with)).check(R.id.play_with_friend);
            findViewById(R.id.level).setVisibility(INVISIBLE);
            ((RadioButton) findViewById(R.id.first_turn_player2)).setText(getContext().getString(R.string.opponent_player));

        }
    }
    public void setFirstTurn(int rule){
        if(rule == FirstTurn.PLAYER1){
            ((RadioGroup) findViewById(R.id.first_turn)).check(R.id.first_turn_player1);
        }else {
            ((RadioGroup) findViewById(R.id.first_turn)).check(R.id.first_turn_player2);
        }
    }
    public void setPLayer1Disc(int rule){
        if(rule == Disc.RED){
            ((RadioGroup) findViewById(R.id.player1_disc)).check(R.id.disc_red);
        }else {
            ((RadioGroup) findViewById(R.id.player1_disc)).check(R.id.disc_yellow);
        }
    }
    public void setDificulty(int rule){
        ((SeekBar) findViewById(R.id.difficulty)).setProgress(rule);
    }

    public void setupMenu(GameRules defaultGameRules) {
        setPlayWith(defaultGameRules.getRule(GameRules.OPPONENT));
        setFirstTurn(defaultGameRules.getRule(GameRules.FIRST_TURN));
        setPLayer1Disc(defaultGameRules.getRule(GameRules.DISC));
        setDificulty(defaultGameRules.getRule(GameRules.LEVEL));

    }
}
