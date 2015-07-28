package com.seaguest.game.searchengine;

import java.util.Arrays;

public class HistoryHeuristic {
    public int[][] historyTable;

    public HistoryHeuristic(int size) {
        historyTable = new int[size][];
        for (int i = 0; i < historyTable.length; i++) {
            historyTable[i] = new int[size];
        }
    }

    public void resetHistoryHeristic() {
        for (int i = 0; i < historyTable.length; i++) {
            for (int j = 0; j < historyTable.length; j++) {
                historyTable[i][j] = 0;
            }
        }
    }

    public int getHistoryHeristicScore(MoveAndScore move) {
        return historyTable[move.position.x][move.position.y];
    }

    public void saveHistoryScore(MoveAndScore move, int depth) {
        historyTable[move.position.x][move.position.y]
                += 2 << depth;
    }

    public void sortStoneMove(MoveAndScore[] moves, boolean direction) {
        Arrays.sort(moves);
        MoveAndScore tm;
        if (direction) {
            for (int i = 0, j = moves.length - 1; i < (moves.length) / 2; i++, j--) {
                tm = moves[i];
                moves[i] = moves[j];
                moves[j] = tm;
            }
        }
    }
}
