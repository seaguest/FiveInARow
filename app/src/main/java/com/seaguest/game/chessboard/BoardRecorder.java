package com.seaguest.game.chessboard;

import com.seaguest.game.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyhk1987 on 2015/8/10.
 */
public class BoardRecorder {
    // the list which contains all chessman on board
    public List<Chessman> onBoardChessman;
    public int blackCount = 0;
    public int whiteckCount = 0;

    public BoardRecorder(){
        onBoardChessman = new ArrayList<Chessman>();
        blackCount = 0;
        whiteckCount = 0;
    }

    public void push(Chessman chessman){
        onBoardChessman.add(chessman);
    }

    public Chessman getLastChessman(){
        if((blackCount+whiteckCount-1) >=0){
            return onBoardChessman.get(blackCount+whiteckCount-1);
        }
        return null;
    }

    public Chessman pop(){
        Chessman chessman = onBoardChessman.get(blackCount+whiteckCount-1);
        onBoardChessman.remove(chessman);
        if(chessman.color == IPlayer.BLACK){
            blackCount--;
        }else if(chessman.color == IPlayer.WHITE){
            whiteckCount--;
        }
        return chessman;
    }

    public boolean isPlaceAvailable(Chessman chessman){
        return !onBoardChessman.contains(chessman);
    }


    public boolean empty(){
        return onBoardChessman.size() ==0;
    }

    public  void clear(){
        onBoardChessman.clear();
        blackCount = 0;
        whiteckCount = 0;
    }

}
