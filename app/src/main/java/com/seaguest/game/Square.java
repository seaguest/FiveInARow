package com.seaguest.game;

import android.graphics.Point;

/**
 * Created by tyhk1987 on 2015/7/18.
 */
public class Square {
    // the left top origin point.
    public Point start;

    public int width;

    public Square(Point start, int width) {
        this.start = start;
        this.width = width;
    }
}
