
package com.seaguest.game.chessboard;

public class Chessman extends Point {
    public final static int BLACK = 1;
    public final static int WHITE = 2;
    public final static int NOSTONE = 0;

    public int color;

    public Chessman(int x, int y, int chessColor) {
        super(x, y);
        color = chessColor;
    }

    @Override
    public boolean equals(Object o) {
        boolean equals = false;
        if(o instanceof Chessman){
            Chessman other = (Chessman)o;
            //equals = (other.x == this.x )&&(other.x == this.x )&&(other.color == this.color );
            equals = (other.x == this.x )&&(other.y == this.y );
        }
        return equals;

    }

    @Override
    public int hashCode() {
        return (x*x + y*y);
    }
}