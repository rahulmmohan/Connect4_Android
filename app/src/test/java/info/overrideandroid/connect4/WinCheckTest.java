package info.overrideandroid.connect4;

import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import info.overrideandroid.connect4.board.BoardLogic;

/**
 * Created by rahul.m on 05-06-2017.
 */

public class WinCheckTest {
    @NonNull
    private int[][] gridForHorizontalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {1,1,1,1,0,0,0,},
    };
    @NonNull
    private int[][] gridForVerticalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
    };
    @NonNull
    private int[][] gridForAcendingDiagonalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,1,0,0,0,},
            {0,0,1,2,0,0,0,},
            {0,1,2,2,0,0,0,},
            {1,2,2,2,0,0,0,},
    };
    private final int[][] gridFoDescendingDiagonalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {1,0,0,0,0,0,0,},
            {2,1,0,0,0,0,0,},
            {2,2,1,0,0,0,0,},
            {2,2,2,1,0,0,0,},
    };
    @Test
    public void testWInnerCheck(){
        BoardLogic boardLogic = new BoardLogic(gridForVerticalCheck,null);
        Assert.assertSame("Winner check failed",BoardLogic.Outcome.PLAYER1_WINS,boardLogic.checkWin());
    }
}
