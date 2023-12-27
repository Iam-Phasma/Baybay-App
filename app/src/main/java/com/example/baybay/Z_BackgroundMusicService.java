package com.example.baybay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class Z_BackgroundMusicService extends Service {

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        Z_BackgroundMusicService getService() {
            return Z_BackgroundMusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.main_woodlandfantacy);
        mediaPlayer.setLooping(true);
    }

    public void startMusic() {
        if (Z_SoundManager.isBgon[0]) {
            mediaPlayer.start();
        }
    }

    public void stopMusic() {
        mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
