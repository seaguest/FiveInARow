
package com.seaguest.game.chessboard;

public class Chessman extends Point {
    public final static int BLACK = 1;
    public final static int WHITE = 2;
    public final static int NOSTONE = 0;

    int color;

    public Chessman(int x, int y, int chessColor) {
        super(x, y);
        color = chessColor;
    }

}