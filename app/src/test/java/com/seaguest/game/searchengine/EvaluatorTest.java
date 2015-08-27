package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Chessman;
import com.seaguest.game.player.ComputerPlayer;
import com.seaguest.game.player.IPlayer;
import com.seaguest.game.transition.TransitionTable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tyhk1987 on 2015/8/26.
 */
public class EvaluatorTest {

    @Test
    public void testxx() {
        System.out.println("------xxxxxxxxxxxxxx----------------");


        int[][] board9 = {
                {0, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 2, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 2, 0, 2, 2, 2, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };


        System.out.println("------xxxxxxxxxxxxxx----------------");


        ComputerPlayer ai = new ComputerPlayer(IPlayer.WHITE, 2);

        // Evaluator evaluator = new Evaluator(board9);
        // evaluator.move(3,2, 1);
        // evaluator.move(6,7, 2);
        // evaluator.move(3,3, 1);
        // evaluator.move(6,8, 2);
        // evaluator.displayBoard();
        // int score = evaluator.evaluate(ai);
        // System.out.println("score:" + score);
        //
        Evaluator evaluator = new Evaluator(board9);
        int[][] initialcounter = Evaluator.getCounter(board9);
        TransitionTable.getInstance().saveBoard(board9);
        System.out.println("initialcounter: \n" + Arrays.deepToString(initialcounter));


        List<Chessman> records = new ArrayList<Chessman>();
        Chessman chess1 = new Chessman(2, 2, 1);
        Chessman chess2 = new Chessman(5, 5, 2);
        Chessman chess3 = new Chessman(0, 1, 1);
        Chessman chess4 = new Chessman(8, 3, 2);
        records.add(chess1);
        records.add(chess2);
        records.add(chess3);
        records.add(chess4);
        evaluator.moveChessmans(records);

        System.out.println("target counter:\n" + Arrays.deepToString(Evaluator.getCounter(evaluator.board)));


        int[][] newcounter = evaluator.getCurrentCounter(records);
        System.out.println("newcounter:\n" + Arrays.deepToString(newcounter));



//	evaluator.move(5, 6, 2);
        // evaluator.move(5,5, 2);
        // evaluator.move(2,8, 1);
        // evaluator.move(7,3, 2);

//        evaluator.displayBoard();
        //      int score = evaluator.evaluate(ai);
//        System.out.println("score:" + score);


    }

}
