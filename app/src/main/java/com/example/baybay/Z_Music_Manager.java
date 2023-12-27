package com.example.baybay;

import android.content.Context;
import android.media.MediaPlayer;

import es.dmoral.toasty.Toasty;

public class Z_Music_Manager {

    private static MediaPlayer ChapterA, ChapterB, ChapterC;


    public static void playChapterA(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            if (context != null) {
                ChapterA = MediaPlayer.create(context, R.raw.history_chapter_a);
                ChapterA.setLooping(true);
                ChapterA.setVolume(1f, 1f);
                ChapterA.start();
                ChapterA.setOnCompletionListener(MediaPlayer::release);
            }
        } else {
            Toasty.info(context, "Turn on sound effects to play narration.", Toasty.LENGTH_SHORT).show();
        }
    }

    public static void stopChapterA() {
        if (ChapterA != null) {
            ChapterA.stop();
            ChapterA.release();
            ChapterA = null;
        }
    }



    public static void playChapterB(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            if (context != null) {
                ChapterB = MediaPlayer.create(context, R.raw.history_chapter_b);
                ChapterB.setLooping(true);
                ChapterB.setVolume(1f, 1f);
                ChapterB.start();
                ChapterB.setOnCompletionListener(MediaPlayer::release);
            }
        } else {
            Toasty.info(context, "Turn on sound effects to play narration.", Toasty.LENGTH_SHORT).show();
        }
    }

    public static void stopChapterB() {
        if (ChapterB != null) {
            ChapterB.stop();
            ChapterB.release();
            ChapterB = null;
        }
    }


    public static void playChapterC(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            if (context != null) {
                ChapterC = MediaPlayer.create(context, R.raw.history_chapter_c);
                ChapterC.setLooping(true);
                ChapterC.setVolume(1f, 1f);
                ChapterC.start();
                ChapterC.setOnCompletionListener(MediaPlayer::release);
            }
        } else {
            Toasty.info(context, "Turn on sound effects to play narration.", Toasty.LENGTH_SHORT).show();
        }
    }

    public static void stopChapterC() {
        if (ChapterC != null) {
            ChapterC.stop();
            ChapterC.release();
            ChapterC = null;
        }
    }
}
