package info.overrideandroid.connect4.ai;

import info.overrideandroid.connect4.rules.Slot;

/**
 * Provides a simple logic for AI classes.
 * Created by Rahul on 31/05/17.
 */

public class AiLogic {

    /** reference to the main grid */
    protected final Slot[][] grid;

    /**
     * Create AI
     * @param _grid
     */
    public AiLogic(Slot[][] _grid) {
        grid = _grid;
    }


    /**
     * Runs the Ai logic, returns the column to put the disc in
     * @return
     */
    public int run() {
        // random column
        int col = (int)(Math.random() * grid.length);

        while(grid[col][0].player != 0) {
            // increment it if not possible to put chip there
            col = (col + 1) % grid.length;
        }
        return col;
    }
}
