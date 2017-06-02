package info.overrideandroid.connect4.rules;

/**
 * Created by rahul.m on 01-06-2017.
 */

public class Slot {

    private final int i;
    private final int j;
    public int player = Player.NONE;
    public Slot(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
