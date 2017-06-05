package info.overrideandroid.connect4;

import org.junit.Assert;
import org.junit.Test;

import info.overrideandroid.connect4.board.BoardLogic;

/**
 * Created by rahul.m on 05-06-2017.
 */

public class WinCheckTest {
    private int[][] gridForHorizontalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {1,1,1,1,0,0,0,},
    };
    private int[][] gridForVerticalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
    };
    private int[][] gridForAcendingDiagonalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,1,0,0,0,},
            {0,0,1,2,0,0,0,},
            {0,1,2,2,0,0,0,},
            {1,2,2,2,0,0,0,},
    };
    private int[][] gridFoDescendingDiagonalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {2,1,0,0,0,0,0,},
            {2,2,1,0,0,0,0,},
            {2,2,2,1,0,0,0,},
    };
    @Test
    public void testWInnerCheck(){
        BoardLogic boardLogic = new BoardLogic(gridFoDescendingDiagonalCheck);
        Assert.assertSame("Winner check failed",BoardLogic.Outcome.PLAYER2_WINS,boardLogic.checkWin());
    }
}
