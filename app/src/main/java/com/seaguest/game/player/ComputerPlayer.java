package com.seaguest.game.player;

import com.seaguest.game.chessboard.Configuration;
import com.seaguest.game.searchengine.SearchEngine;

public class ComputerPlayer implements IPlayer {
    public boolean isX = true;

    public int chessmanColor;

    public int winState = 2;

    public SearchEngine searchEngine;
    Configuration setting;

    public ComputerPlayer(int color, int maxDepth) {
        this.chessmanColor = color;
        this.setting = setting;
        searchEngine = new SearchEngine(this, maxDepth);
    }

    @Override
    public boolean isX() {
        return isX;
    }

    @Override
    public boolean play() {
        return false;
    }

    @Override
    public boolean hasIWon() {
        return this.winState == IPlayer.WIN;
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

}
