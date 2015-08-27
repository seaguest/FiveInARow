package com.seaguest.game.player;

import com.seaguest.game.chessboard.Chessman;

/**
 * Player Interface
 */
public interface IPlayer {
    public final static int WIN = 1;
    public final static int LOST = -1;
    public final static int DRAW = 0;

    public static final int X = 11;
    public static final int O = 22;

    public static final int NONE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public int getChessmanColor();

    public boolean isX();

    public void moveChessman(Chessman chessman);

    public void setWinState(int winstate);

    public int getWinstate();

}