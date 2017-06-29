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
import info.overrideandroid.connect4.rules.GameRules.Disc;
import info.overrideandroid.connect4.rules.GameRules.FirstTurn;
import info.overrideandroid.connect4.rules.GameRules.Opponent;

/**
 * Created by Rahul on 31/05/17.
 */

public class GameMenuView extends RelativeLayout {
    public GameMenuView(Context context) {
        super(context);
    }

    public GameMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Listener for menu events
     * @param gameMenuController game menu controller instance
     */
    public void setListeners(GameMenuController gameMenuController) {

        findViewById(R.id.play).setOnClickListener(gameMenuController);
        findViewById(R.id.achievements).setOnClickListener(gameMenuController);
        findViewById(R.id.settings).setOnClickListener(gameMenuController);
        findViewById(R.id.help).setOnClickListener(gameMenuController);
        findViewById(R.id.rate).setOnClickListener(gameMenuController);
        findViewById(R.id.share).setOnClickListener(gameMenuController);

    }


}
