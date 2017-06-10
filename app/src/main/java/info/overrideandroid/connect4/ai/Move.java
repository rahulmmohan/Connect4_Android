package info.overrideandroid.connect4.ai;

/**
 * Created by Rahul on 03/06/17.
 */

class Move {

    private final int mColumn;
    private final int mScore;

    /**
     * @param column mColumn the move is in
     * @param score  the mScore of the move
     */
    public Move(int column, int score) {
        this.mColumn = column;
        this.mScore = score;
    }

    /**
     * Get the mColumn the move is in
     *
     * @return the mColumn the move is in
     */
    public int getColumn() {
        return mColumn;
    }

    /**
     * Get the mScore of the move
     *
     * @return the mScore of the move
     */
    public int getScore() {
        return mScore;
    }
}
