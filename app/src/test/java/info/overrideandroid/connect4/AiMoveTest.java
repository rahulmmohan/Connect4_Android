package info.overrideandroid.connect4;

import org.junit.Assert;
import org.junit.Test;

import info.overrideandroid.connect4.ai.AiBoardMove;
import info.overrideandroid.connect4.ai.AiPlayer;
import info.overrideandroid.connect4.board.BoardLogic;

/**
 * Created by rahul.m on 05-06-2017.
 */

public class AiMoveTest {
    private int[][] grid = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {2,0,0,0,0,0,0,},
            {1,0,2,0,0,0,0,},
            {1,1,2,1,0,0,0,},
            {1,2,2,2,1,0,0,},
    };
    private int[][] gridForHorizontalCheck = {
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
    };
    @Test
    public void testAiMove(){
        BoardLogic boardLogic =new BoardLogic(gridForHorizontalCheck);
        AiBoardMove aiBoardMov =new AiBoardMove(gridForHorizontalCheck,boardLogic,null);
        AiPlayer aiPlayer = new AiPlayer(aiBoardMov);
        Assert.assertEquals(0,aiPlayer.getColumn());
    }
}
