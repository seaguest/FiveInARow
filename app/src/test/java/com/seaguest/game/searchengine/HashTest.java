package com.seaguest.game.searchengine;

import com.seaguest.game.transition.TransitionTable;

import java.util.Arrays;

/**
 * Created by tyhk1987 on 2015/8/26.
 */
public class HashTest {

    //@Test
    public void test1() {

        int[][] board1 = {
                {0, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 2, 0, 0},
                {0, 0, 0, 2, 2, 0, 1},
                {0, 0, 0, 0, 2, 0, 1},
                {0, 2, 2, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0},
        };

        int hash = java.util.Arrays.deepHashCode(board1);
        int[][] counter = Evaluator.getCounter(board1);
        TransitionTable.getInstance().saveBoard(board1);

        System.out.println(hash);
        printCounter(counter);


        int[][] board2 = {
                {0, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 2, 0, 0},
                {0, 0, 0, 2, 2, 0, 1},
                {0, 0, 0, 0, 2, 0, 1},
                {0, 2, 2, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0},
        };

        int[][] counter2 = TransitionTable.getInstance().getCounter(board2);

        System.out.println(counter2);


        printCounter(counter2);

        int[][] res = new int[2][10];
        System.out.println(Arrays.toString(res[0]));

        int[] test1 = new int[6];
        test1[0]=1;
        test1[1]=2;
        test1[2]=3;
        res[0] = test1;
        System.out.println(Arrays.toString(test1));
        System.out.println(Arrays.toString(res[0]));


    }


    public static void printCounter(int[][] counter) {
        for (int i = 0; i < 2; i++) {
            System.out.println("-----------------Player:" + i + "-----------------");
            for (int j = 0; j < 7; j++) {
                System.out.println("-Pattern:" + j + " - count: " + counter[i][j]);
            }
        }
    }

}
