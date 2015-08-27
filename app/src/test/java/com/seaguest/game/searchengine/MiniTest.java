package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Chessman;
import com.seaguest.game.chessboard.Configuration;
import com.seaguest.game.chessboard.Point;
import com.seaguest.game.player.ComputerPlayer;
import com.seaguest.game.player.IPlayer;
import com.seaguest.game.transition.TransitionTable;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by tyhk1987 on 2015/7/18.
 */

public class MiniTest {

    //@Test
    public void testArrayGenerator() {
    int        size = 11;
        int[][] array = new int[size][size];

        int maxValue = (size-1)/2;
        Point center = new Point(maxValue, maxValue);
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                int value =maxValue- Math.max(Math.abs(i-center.x), Math.abs(j-center.y));
                array[i][j] = value;
            }
        }

        System.out.println(Arrays.deepToString(array));
    }

    @Test
    public void testMinimax1() {
        int[][] board1 = {
                {0, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 2, 0, 0},
                {0, 0, 0, 2, 2, 0, 1},
                {0, 0, 0, 0, 2, 0, 1},
                {0, 2, 2, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0},
        };

        int[][] board9 = {
                { 0, 0, 1, 0, 0, 0, 0, 1, 1},
                { 0, 0, 1, 0, 0, 0, 0, 0, 0},
                { 0, 1, 0, 0, 0, 0, 0, 0, 0},
                { 0, 1, 0, 0, 1, 0, 0, 2, 0},
                { 0, 0, 0, 0, 0, 0, 2, 2, 0},
                { 0, 0, 0, 0, 0, 0, 0, 2, 0},
                { 0, 0, 0, 0, 2, 2, 2, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };



        int[][] board91 = {
                { 1, 1, 0, 2, 1, 1, 1, 2, 2},
                { 2, 2, 2, 2, 1, 2, 2, 1, 2},
                { 2, 2, 2, 1, 1, 1, 1, 2, 1},
                { 2, 1, 1, 1, 2, 1, 1, 1, 1},
                { 2, 2, 2, 2, 1, 2, 1, 2, 1},
                { 1, 2, 2, 2, 2, 1, 2, 2, 2},
                { 1, 2, 2, 2, 2, 1, 1, 1, 1},
                { 1, 2, 2, 1, 1, 2, 2, 2, 1},
                { 0, 1, 1, 1, 2, 1, 1, 1, 1},
        };


        int[][] board11 = {
                {0, 0, 0, 0, 1, 0, 2, 0, 0, 1, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 1, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };


        int[][] board131 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        int[][] board2 = {
                {0, 0, 0, 0, 1, 0, 2, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        int[][] board13 = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };


        long t3 = System.currentTimeMillis();
//        Evaluator.getCounter(board2);
        TransitionTable.getInstance().saveBoard(board9);
        long t4 = System.currentTimeMillis();
        System.out.println("computing counter Time:" + (t4 - t3));


        long t1 = System.currentTimeMillis();
/*
        int[][] rows =         MatrixHelper.getRows(board131);
        System.out.println("rows:" + Arrays.deepToString(rows));
*/

        Configuration setting = new Configuration();
        ComputerPlayer ai = new ComputerPlayer(IPlayer.BLACK, 4);
        ai.searchEngine.setBoard(board9);
        ai.searchEngine.displayBoard();
        ai.searchEngine.searchBestMove();
        long t2 = System.currentTimeMillis();
        System.out.println("Searching Time:" + (t2 - t1));


        System.out.println("----------------------");
        MoveAndScore bestMove = ai.searchEngine.getBestMove();
        Chessman chessmanToMove = new Chessman(bestMove.position.x, bestMove.position.y, IPlayer.BLACK);
        ai.searchEngine.makeMove(chessmanToMove);
        ai.searchEngine.displayBoard();
        System.out.println("bestMove is:" + bestMove.position.x + "-" + bestMove.position.y);


    }

    //@Test
    public void testMinimax3() {

        int[][] board9 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        Configuration setting = new Configuration();
        ComputerPlayer ai = new ComputerPlayer(IPlayer.BLACK, 2);
        ai.searchEngine.setBoard(board9);

        ai.searchEngine.displayBoard();

        ai.searchEngine.searchBestMove();

        System.out.println("----------------------");
        MoveAndScore bestMove = ai.searchEngine.getBestMove();
        Chessman chessmanToMove = new Chessman(bestMove.position.x, bestMove.position.y, IPlayer.BLACK);
        ai.searchEngine.makeMove(chessmanToMove);

        ai.searchEngine.displayBoard();

        System.out.println("bestMove is:" + bestMove.position.x + "-" + bestMove.position.y);
    }


//    @Test
    public void testQuickSort() {
        MoveAndScore[] moves = new MoveAndScore[10];
        moves[0] = new MoveAndScore(10, new Point(-1, -1));
        moves[1] = new MoveAndScore(1, new Point(-1, -1));
        moves[2] = new MoveAndScore(5, new Point(-1, -1));
        moves[3] = new MoveAndScore(4, new Point(-1, -1));
        moves[4] = new MoveAndScore(8, new Point(-1, -1));
        moves[5] = new MoveAndScore(0, null);
        moves[6] = new MoveAndScore(0, null);
        moves[7] = new MoveAndScore(0, null);
        moves[8] = new MoveAndScore(0, null);
        moves[9] = new MoveAndScore(0, null);

        System.out.println("endIndex:" + QuickSort.findEndIndex(moves));


        QuickSort.quickSort(moves, 0, QuickSort.findEndIndex(moves));

        for(MoveAndScore move: moves){
            System.out.println("Score:" + move.score);
        }

    }

//    @Test
    public void testMinimax2() {
        int[][] board9 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,}
        };

        Configuration setting = new Configuration();
        ComputerPlayer ai = new ComputerPlayer(IPlayer.BLACK, 2);
        ai.searchEngine.setBoard(board9);

        ai.searchEngine.displayBoard();

        ai.searchEngine.searchBestMove();

        System.out.println("----------------------");
        MoveAndScore bestMove = ai.searchEngine.getBestMove();
        Chessman chessmanToMove = new Chessman(bestMove.position.x, bestMove.position.y, IPlayer.BLACK);
        ai.searchEngine.makeMove(chessmanToMove);
        ai.searchEngine.displayBoard();

        System.out.println("bestMove is:" + bestMove.position.x + "-" + bestMove.position.y);

    }

}
