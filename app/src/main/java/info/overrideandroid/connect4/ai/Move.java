package info.overrideandroid.connect4.ai;

/**
 * Created by Rahul on 03/06/17.
 */

class Move {

    private final int column;
    private final int score;

    /**
     * @param column column the move is in
     * @param score  the score of the move
     */
    public Move(int column, int score) {
        this.column = column;
        this.score = score;
    }

    /**
     * Get the column the move is in
     *
     * @return the column the move is in
     */
    public int getColumn() {
        return column;
    }

    /**
     * Get the score of the move
     *
     * @return the score of the move
     */
    public int getScore() {
        return score;
    }
}
