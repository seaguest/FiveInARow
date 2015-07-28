package com.seaguest.game.resource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import com.seaguest.game.R;

/**
 * Created by tyhk1987 on 2015/7/24.
 */
public class ResourceManager {

    public Bitmap blackBitmap, whiteBitmap;
    public MediaPlayer mediaPlayerWin;
    public MediaPlayer mediaPlayerLost;
    public MediaPlayer mediaPlayerDraw;
    public MediaPlayer mediaPlayerPush;
    public MediaPlayer mediaPlayerRemove;


    public ResourceManager(Context context, Resources res) {
        blackBitmap = BitmapFactory.decodeResource(res, R.mipmap.black);
        whiteBitmap = BitmapFactory.decodeResource(res, R.mipmap.white);

        mediaPlayerWin = MediaPlayer.create(context, R.raw.win);
        mediaPlayerLost = MediaPlayer.create(context, R.raw.lost);
        mediaPlayerDraw = MediaPlayer.create(context, R.raw.draw);
        mediaPlayerPush = MediaPlayer.create(context, R.raw.put);
        mediaPlayerRemove = MediaPlayer.create(context, R.raw.remove);

    }

}
