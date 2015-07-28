package com.seaguest.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.seaguest.game.chessboard.ChessBoard;

public class BoardGame extends Activity {

    // private CanvasView chessboard;
    private ChessBoard chessboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //	chessboard = (CanvasView) findViewById(R.id.signature_canvas);
        chessboard = (ChessBoard) findViewById(R.id.chessboard);
    }

    public void newGame(View v) {
        chessboard.resetChessBoard();
    }

}