package com.example.baybay;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static String toPlay = "Woodland";

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        toPlay = preferences.getString("TO_PLAY_KEY", "Woodland");

        if ("Woodland".equals(toPlay)) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music_woodland_fantacy);
        } else if ("Curious".equals(toPlay)) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music_curious_critters);
        }
        mediaPlayer.setLooping(true);
    }


    public static String getToPlay() {
        return toPlay;
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
