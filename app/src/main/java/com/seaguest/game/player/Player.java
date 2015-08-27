package com.seaguest.game.player;

import com.seaguest.game.chessboard.ChessBoard;
import com.seaguest.game.chessboard.Chessman;
import com.seaguest.game.chessboard.Configuration;

public class Player implements IPlayer {
    public boolean isX = false;
    public int chessmanColor;
    public int winState = 2;

    public ChessBoard chessboard;
    public Configuration setting;

    public Player(int color, ChessBoard chessboard) {
        chessmanColor = color;
        this.chessboard = chessboard;
        setting = new Configuration();
    }

    @Override
    public boolean isX() {
        return isX;
    }

    @Override
    public void moveChessman(Chessman chessman) {
        chessboard.placeChessman(chessman);
    }

    @Override
    public int getWinstate() {
        return this.winState;
    }

    @Override
    public int getChessmanColor() {
        return chessmanColor;
    }

    @Override
    public void setWinState(int winstate) {
        this.winState = winstate;
    }


    public void quit(){}

    public void askForDraw(){}

    public void giveup(){}

    public void undo(){
        chessboard.undoLastStep(this);
    }




}
