package com.seaguest.tyhk1987.uiv10;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tyhk1987 on 2015/7/22.
 */
public class MyView extends LinearLayout {

    public TextView name;
    public TextView level;
    public ImageButton avatar;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        name = new TextView(context);
        LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(15,50);
        name.setLayoutParams(Params1);   //you take linearlayout and relativelayout.
        name.setText("None --------------");

        name.setVisibility(View.VISIBLE);

        level = new TextView(context);
        level.setText("level 1");
        level.setVisibility(View.VISIBLE);

        avatar = new ImageButton(context);
        avatar.setBackgroundResource(R.mipmap.icon);
    }
}
