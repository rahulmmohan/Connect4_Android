package info.overrideandroid.connect4;

import org.junit.Assert;
import org.junit.Test;

import info.overrideandroid.connect4.ai.AiPlayer;
import info.overrideandroid.connect4.board.BoardLogic;

/**
 * Created by rahul.m on 05-06-2017.
 */

public class AiMoveTest {
    private final int[] free = {4,5,4,5,5,6,6};

    private final int[][] grid = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {1,0,2,0,0,0,0,},
            {1,1,2,2,2,0,0,},
    };

    @Test
    public void testAiMove(){
        BoardLogic boardLogic =new BoardLogic(grid,free);
        AiPlayer aiPlayer = new AiPlayer(boardLogic);
        Assert.assertEquals(5,aiPlayer.getColumn());
    }
}
