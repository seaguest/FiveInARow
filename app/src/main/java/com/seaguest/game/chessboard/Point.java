package com.seaguest.game.chessboard;

// for coordiante
public class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        boolean equals = false;
        if (o instanceof Point) {
            Point anotherPoint = (Point) o;
            equals = (this.x == anotherPoint.x && this.y == anotherPoint.y);
        }
        return equals;
    }

}