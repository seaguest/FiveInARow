package com.seaguest.game.searchengine;

import com.seaguest.game.chessboard.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyhk1987 on 2015/8/11.
 */
public class LocationScoreArray {
    private static LocationScoreArray instance = null;

    public List<int[][]> locationscores;


    public static LocationScoreArray getInstance() {
        if (instance == null) {
            instance = new LocationScoreArray();
        }

        return instance;
    }

    public LocationScoreArray() {
        init();
    }

    public int getScore(int size, int x, int y){
        return locationscores.get((size-7)/2)[x][y];
    }

    /**
     * init for size: 7,9,11,13,15,17,19
     */
    public void init() {
        locationscores = new ArrayList<int[][]>();
        locationscores.add(createLocationScoreArray(7));
        locationscores.add(createLocationScoreArray(9));
        locationscores.add(createLocationScoreArray(11));
        locationscores.add(createLocationScoreArray(13));
        locationscores.add(createLocationScoreArray(15));
        locationscores.add(createLocationScoreArray(17));
        locationscores.add(createLocationScoreArray(19));
    }

    public int[][] createLocationScoreArray(int size) {
        int[][] array = new int[size][size];
        int maxValue = (size - 1) / 2;
        Point center = new Point(maxValue, maxValue);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = maxValue - Math.max(Math.abs(i - center.x), Math.abs(j - center.y));
                array[i][j] = value;
            }
        }
        return array;
    }


}
