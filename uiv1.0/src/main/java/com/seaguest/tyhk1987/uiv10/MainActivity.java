package com.seaguest.tyhk1987.uiv10;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    // private CanvasView chessboard;
    private ImageButton start;
    private ImageButton setting;
    private PopupWindow popupMenu;
    private PopupWindow settingpopupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (ImageButton) findViewById(R.id.startButton);
        setting = (ImageButton) findViewById(R.id.settingButton);
        start.setOnClickListener(this);
        setting.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        }


    }


    private void initPopupMenu(){
        if(popupMenu==null){
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.popup_menu, null);

            ((Button)v.findViewById(R.id.newgame)).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    popupMenu.dismiss();
                }
            });

            popupMenu = new PopupWindow(v, getApplicationContext().getResources().getDisplayMetrics().widthPixels/3,
                    getApplicationContext().getResources().getDisplayMetrics().heightPixels/4 , true);
        }
        popupMenu.setBackgroundDrawable(new BitmapDrawable());
        popupMenu.setFocusable(true);
        popupMenu.setOutsideTouchable(true);
        popupMenu.update();

        popupMenu.showAsDropDown(start);
    }



    private void openSetting(){
        // TODO Auto-generated method stub
        //popupWindowSetting.showAtLocation(findViewById(R.id.chessboard), Gravity.CENTER, 0, 0);
        Intent intent = new Intent(MainActivity.this, MainSettingActivity.class);
        intent.putExtra("SETTING_DISPLAY_WAY_SIZE",
                new String[]{"Test"});

        startActivityForResult(intent, 0);

//        startActivity(intent);

    }

    private void initsetPopupMenu(){
        if(settingpopupMenu==null){
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.setting, null);

            ((Button)v.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    settingpopupMenu.dismiss();
                }
            });

//            settingpopupMenu = new PopupWindow(v, getApplicationContext().getResources().getDisplayMetrics().widthPixels/3,                    getApplicationContext().getResources().getDisplayMetrics().heightPixels/4 , true);
            settingpopupMenu = new PopupWindow(v, 300,
                    300 , true);
        }
//        settingpopupMenu.setClippingEnabled(true);
        settingpopupMenu.setBackgroundDrawable(new BitmapDrawable());
        settingpopupMenu.setFocusable(true);
        settingpopupMenu.setOutsideTouchable(true);
        settingpopupMenu.update(300, 300, -1, -1);

        settingpopupMenu.showAsDropDown(setting, Gravity.NO_GRAVITY, 500, 900);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            int level = data.getIntExtra("level", 1);
            boolean alowUndo = data.getBooleanExtra("allowundo", false);

            System.out.println("000000000000000000000 level:" + level + " allownundo " + alowUndo);

        }
    }


}
