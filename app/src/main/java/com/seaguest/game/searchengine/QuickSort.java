package com.seaguest.game.searchengine;

/**
 * Created by tyhk1987 on 2015/7/25.
 */
public class QuickSort {

    public static int partition(MoveAndScore[] moves, int left, int right) {

        int i = left, j = right;
        MoveAndScore tmp;
        int pivot = moves[(left + right) / 2].score;

        while (i <= j) {
            while (moves[i].score > pivot) i++;
            while (moves[j].score < pivot) j--;
            if (i <= j) {
                tmp = moves[i];
                moves[i] = moves[j];
                moves[j] = tmp;
                i++;
                j--;
            }
        }
        return i;
    }


    public static void quickSort(MoveAndScore[] moves, int left, int right) {
        int index = partition(moves, left, right);
        if (left < index - 1)
            quickSort(moves, left, index - 1);
        if (index < right)
            quickSort(moves, index, right);
    }


    public static int findEndIndex(MoveAndScore[] moves) {
        int endIndex = moves.length - 1;

        while (moves[endIndex].position == null) endIndex--;

        return endIndex;
    }
}
