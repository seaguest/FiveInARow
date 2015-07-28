package com.seaguest.game.player;

import android.content.Intent;

import com.seaguest.game.chessboard.ChessBoard;

public class Player implements IPlayer{
	public ChessBoard chessboard;
	public int myChessmanColor;

	public int winState = 2;

	public Player(ChessBoard c, int color) {
		chessboard = c;
		myChessmanColor = color;
	}

	@Override
	public boolean isX() {
		return false;
	}

	@Override
	public boolean play() {
		return true;
	}
	
 	@Override
	public boolean sendMessage(Intent message){return false;}

 	@Override
	/*
	 * return 胜1 还未胜-1 和0
	 */
	public int canIWin() {
		int totalChessmanInTheory = chessboard.chessBoardSize*chessboard.chessBoardSize;

		if (chessboard.onBoardChessman.size()
				== totalChessmanInTheory) {
			return 0;
		}
		//return chessboard.isWin(myChessmanColor);
		return 1;
	}
}
