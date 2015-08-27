package com.seaguest.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.seaguest.game.chessboard.ChessBoardView;

public class BoardGame extends Activity implements View.OnClickListener{

    // private CanvasView chessboard;
    private ChessBoardView chessboard;

    private ImageButton start;
    private ImageButton setting;
    private PopupWindow popupMenu;
    private PopupWindow settingpopupMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //	chessboard = (CanvasView) findViewById(R.id.signature_canvas);
        chessboard = (ChessBoardView) findViewById(R.id.chessboard);

        start = (ImageButton) findViewById(R.id.startButton);
        setting = (ImageButton) findViewById(R.id.settingButton);
        start.setOnClickListener(this);
        setting.setOnClickListener(this);

    }

    public void newGame() {
        chessboard.resetChessBoard();
    }


    public void undo() {
        chessboard.undoLastStep();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startButton:
                initPopupMenu();
                break;
            case R.id.settingButton:
                openSetting();
//                initsetPopupMenu();
                break;
            case R.id.newgame:
                this.newGame();
                popupMenu.dismiss();
//                initsetPopupMenu();
                break;
            case R.id.undo:
                this.undo();
                popupMenu.dismiss();
//                initsetPopupMenu();
                break;

        }


    }


    private void openSetting(){
        // TODO Auto-generated method stub
        //popupWindowSetting.showAtLocation(findViewById(R.id.chessboard), Gravity.CENTER, 0, 0);
        Intent intent = new Intent(BoardGame.this, MainSettingActivity.class);
        intent.putExtra("level", chessboard.setting.depth);
        intent.putExtra("size", chessboard.chessBoardSize);
        intent.putExtra("allowundo", chessboard.setting.allowundo);
        startActivityForResult(intent, 0);
    }

    private void initPopupMenu(){
        if(popupMenu==null){
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.popup_menu, null);

            ((Button)v.findViewById(R.id.newgame)).setOnClickListener(this);
            ((Button)v.findViewById(R.id.undo)).setOnClickListener(this);

            popupMenu = new PopupWindow(v, getApplicationContext().getResources().getDisplayMetrics().widthPixels/3,
                    getApplicationContext().getResources().getDisplayMetrics().heightPixels/4 , true);
        }
        popupMenu.setBackgroundDrawable(new BitmapDrawable());
        popupMenu.setFocusable(true);
        popupMenu.setOutsideTouchable(true);
        popupMenu.update();

        popupMenu.showAsDropDown(start);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            int level = data.getIntExtra("level", 1);
            int size = data.getIntExtra("size", 1);
            boolean alowUndo = data.getBooleanExtra("allowundo", false);

            chessboard.setting.allowundo = alowUndo;
            chessboard.setting.depth = level;

            // if size changed, then reset game
            if(size != chessboard.chessBoardSize){
                chessboard.chessBoardSize = size;
                chessboard.resetChessBoard();
            }

            chessboard.setSearchEngineMaxDepth();
//            System.out.println("-------------------------- level:" + level + " allownundo " + alowUndo);

        }
    }

}