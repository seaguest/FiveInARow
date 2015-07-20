package com.seaguest.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class BoardGame extends Activity {

    // private CanvasView customCanvas;
    private BoardView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //	customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        customCanvas = (BoardView) findViewById(R.id.signature_canvas);
    }

    public void newGame(View v) {
        customCanvas.newGame();
    }

}