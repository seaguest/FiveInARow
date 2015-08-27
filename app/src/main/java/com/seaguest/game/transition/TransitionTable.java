package com.seaguest.game.transition;

import com.seaguest.game.searchengine.Evaluator;

import java.util.HashMap;

/**
 * Created by tyhk1987 on 2015/8/26.
 */
public class TransitionTable {

    public static TransitionTable instance = new TransitionTable();

    private HashMap<Integer, int[][]> transitiontable = new HashMap<Integer, int[][]>();


    public static TransitionTable getInstance() {
        return instance;
    }


    public synchronized void saveBoard(int[][] board) {
        int key = getHashCode(board);
        if (!transitiontable.containsKey(key)) {
            int[][] counter = Evaluator.getCounter(board);
            transitiontable.put(key, counter);
        }
    }

    public synchronized int[][] getCounter(int[][] boad) {
        int key = getHashCode(boad);
        if (transitiontable.containsKey(key)) {
            return transitiontable.get(key);
        }
        return null;
    }

    public synchronized int getHashCode(int[][] board) {
        return java.util.Arrays.deepHashCode(board);
    }
}
