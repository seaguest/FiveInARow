package com.seaguest.game;

import android.graphics.PointF;

/**
 * Created by tyhk1987 on 2015/7/18.
 */
public class BoardPoint extends PointF{
    public PointState state;

    public BoardPoint(float x, float y){
        super(x, y);
        state = PointState.VACANT;
    }

    public PointState getPointSate(){
        return state;
    }

}
