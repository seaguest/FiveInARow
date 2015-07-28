package com.seaguest.game.player;

import android.content.Intent;

import com.seaguest.game.chessboard.ChessBoard;
import com.seaguest.game.searchengine.SearchEngine;


public class ComputerPlayer implements IPlayer {

    int color = IPlayer.NONE;

    public ChessBoard chessboard;
    public SearchEngine searchEngine;

    public ComputerPlayer(ChessBoard chessboard, int color) {
        this.chessboard = chessboard;
        this.color = color;
        searchEngine = new SearchEngine(chessboard.getSearchEngineBoard());
    }

    @Override
    public boolean isX() {
        return true;
    }

    @Override
    public boolean play() {
        return false;
    }

    @Override
    public int canIWin() {
        return 0;
    }

    @Override
    public boolean sendMessage(Intent message) {
        return false;
    }
}
