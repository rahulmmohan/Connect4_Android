package info.overrideandroid.connect4.rules;

import info.overrideandroid.connect4.R;

/**
 * Created by Rahul on 30/05/17.
 * <p>
 * functionality to save and load different game rules.
 */

public class GameRules {

    public class FirstTurn extends Rule {

        public static final int PLAYER1 = Player.PLAYER1;
        public static final int PLAYER2 = Player.PLAYER2;

        FirstTurn() {
            super(new int[]{PLAYER1, PLAYER2});
        }

    }

    /**
     * Levels when play with AI
     */
    public class Level extends Rule {

        public static final int EASY = R.string.level_0;
        public static final int NORMAL = R.string.level_1;
        public static final int HARD = R.string.level_2;

        Level() {
            super(new int[]{EASY, NORMAL, HARD});
        }
    }

    /**
     * Opponent settings
     */
    public class Opponent extends Rule {
        public static final int PLAYER = R.string.opponent_player;
        public static final int AI = R.string.opponent_ai;


        Opponent() {
            super(new int[]{PLAYER, AI});
        }
    }

    /**
     * Color disc settings
     */
    public class Disc extends Rule {
        public static final int RED = R.drawable.red_disc;
        public static final int YELLOW = R.drawable.yellow_disc;


        Disc() {
            super(new int[]{RED, YELLOW});
        }
    }

    /*
     * All possible rules
     */
    public static final int FIRST_TURN = 0;
    public static final int LEVEL = 1;
    public static final int OPPONENT = 2;
    public static final int DISC = 3;
    public static final int DISC2 = 4; // 2nd player token

    /**
     * rules
     */
    private final Rule[] rules;

    /**
     * Creates Game rules
     */
    public GameRules() {
        rules = new Rule[]{
                new FirstTurn(),
                new Level(),
                new Opponent(),
                new Disc(),
                new Disc()
        };
    }

    /**
     * Returns current rule state
     *
     * @param rule
     * @return
     */
    public int getRule(int rule) {
        return rules[rule].getSelectedId();
    }

    /**
     * Sets new rule state
     *
     * @param rule
     * @param value
     */
    public void setRule(int rule, int value) {
        rules[rule].setId(value);
    }


}
