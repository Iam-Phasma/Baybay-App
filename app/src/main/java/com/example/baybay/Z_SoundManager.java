package com.example.baybay;


import android.content.Context;
import android.media.MediaPlayer;

public class Z_SoundManager {

    public static boolean[] isSoundFx = {true};
    public static boolean[] isBgon = {true};


    //BUTTON CLICK SOUND
    static MediaPlayer regButtonClickSound;
    public void RegButtonClickSound(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            regButtonClickSound = MediaPlayer.create(context, R.raw.button_click);
            regButtonClickSound.start();
            regButtonClickSound.setOnCompletionListener(MediaPlayer::release);
        }
    }

    //Quiz Voice SFX
    static MediaPlayer starVoice0;
    public void QuizVoice0(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            starVoice0 = MediaPlayer.create(context, R.raw.score0star);
            starVoice0.start();
            starVoice0.setOnCompletionListener(MediaPlayer::release);
        }
    }

    static MediaPlayer starVoice1;
    public void QuizVoice1(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            starVoice1 = MediaPlayer.create(context, R.raw.score1star);
            starVoice1.start();
            starVoice1.setOnCompletionListener(MediaPlayer::release);
        }
    }

    static MediaPlayer starVoice2;
    public void QuizVoice2(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            starVoice2 = MediaPlayer.create(context, R.raw.score2stars);
            starVoice2.start();
            starVoice2.setOnCompletionListener(MediaPlayer::release);
        }
    }

    static MediaPlayer starVoice3;
    public void QuizVoice3(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            starVoice3 = MediaPlayer.create(context, R.raw.score3stars);
            starVoice3.start();
            starVoice3.setOnCompletionListener(MediaPlayer::release);
        }
    }

    static MediaPlayer quizVoicePause;
    public void QuizVoicePause(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            quizVoicePause = MediaPlayer.create(context, R.raw.game_paused);
            quizVoicePause.start();
            quizVoicePause.setOnCompletionListener(MediaPlayer::release);
        }
    }

//    static MediaPlayer quizVoiceResume;
//    public void QuizVoiceResume(Context context) {
//        boolean[] sfxPass = Z_SoundManager.isSoundFx;
//        if (sfxPass.length > 0 && sfxPass[0]) {
//            quizVoiceResume = MediaPlayer.create(context, R.raw.uno_thank_you_resume);
//            quizVoiceResume.start();
//            quizVoiceResume.setOnCompletionListener(MediaPlayer::release);
//        }
//    }



    //GAMES CORRECT SOUND
    static MediaPlayer correctSound;
    public void GameCorrectSound(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            correctSound = MediaPlayer.create(context, R.raw.game_correct);
            correctSound.start();
            correctSound.setOnCompletionListener(MediaPlayer::release);
        }
    }
    //GAMES INCORRECT SOUND
    static MediaPlayer WrongSound;
    public void GameWrongSound(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            WrongSound = MediaPlayer.create(context, R.raw.game_incorrect);
            WrongSound.start();
            WrongSound.setOnCompletionListener(MediaPlayer::release);
        }
    }

    //SKIP CLICK SOUND
    static MediaPlayer buttonSkipSound;
    public void buttonSkipSound(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            buttonSkipSound = MediaPlayer.create(context, R.raw.character_skipped);
            buttonSkipSound.start();
            buttonSkipSound.setOnCompletionListener(MediaPlayer::release);
        }
    }

    static MediaPlayer matchCountdown;
    public void MatchCountdown(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            matchCountdown = MediaPlayer.create(context, R.raw.match_countdown);
            matchCountdown.start();
            matchCountdown.setOnCompletionListener(MediaPlayer::release);
        }
    }


    //MAIN MENU AND MODES
    //static MediaPlayer MainMenu_Modes;
//    public static void PlayMainMenu_ModesBackgroundMusic(Context context) {
//
//        if (Z_SoundManager.isBgon[0]) {
//            // Play background music if it's not playing or has been released
//            if (MainMenu.songMain == null) {
//                // Initialize the MediaPlayer and start the music
//                MainMenu.songMain = MediaPlayer.create(context, R.raw.main_woodlandfantacy);
//                MainMenu.songMain.setLooping(true);
//                MainMenu.songMain.start();
//            } else if (!MainMenu.songMain.isPlaying()) {
//                // If the MediaPlayer exists but is not playing, start it again
//                MainMenu.songMain.start();
//            }
//        } else {
//            // Stop and release the MediaPlayer if isBgon[0] is false
//            if (MainMenu.songMain != null && MainMenu.songMain.isPlaying()) {
//                MainMenu.songMain.stop();
//            }
//            if (MainMenu.songMain != null) {
//                MainMenu.songMain.release();
//                MainMenu.songMain = null;
//            }
//        }
//    }
//    public static void StopMainMenu_ModesBackgroundMusic() {
//        if (MainMenu.songMain != null && MainMenu.songMain.isPlaying()) {
//            //MainMenu.songMain.pause();
//            MainMenu.songMain.stop();
//            MainMenu.songMain.release();
//            MainMenu.songMain = null;
//        }
//    }
//    private static boolean activityMainMenuPaused = false;
//    private static boolean activityModesPaused = false;

//    public static void setActivityMainMenuPaused(boolean paused) {
//        activityMainMenuPaused = paused;
//        if (activityMainMenuPaused && activityModesPaused) {
//            Z_SoundManager.StopMainMenu_ModesBackgroundMusic();
//        }
//    }
//    public static void setActivityMainMenuResumed(Context context) {
//        activityMainMenuPaused = false;
//        Z_SoundManager.PlayMainMenu_ModesBackgroundMusic(context);
//    }
//    public static void setActivityModesPaused(boolean paused) {
//        activityModesPaused = paused;
//        if (activityMainMenuPaused && activityModesPaused) {
//            Z_SoundManager.StopMainMenu_ModesBackgroundMusic();
//        }
//    }
//    public static void setActivityModesResumed(Context context) {
//        activityModesPaused = false;
//        Z_SoundManager.PlayMainMenu_ModesBackgroundMusic(context);
//    }



    //GAMES BACKGROUND MUSIC
    static MediaPlayer Games;
    public void GamesBackgroundMusic(Context context) {
        boolean[] bgPass = Z_SoundManager.isBgon;

        if (bgPass.length > 0 && bgPass[0]) {
            if (Games == null) {
                // Initialize the MediaPlayer with the audio resource
                Games = MediaPlayer.create(context, R.raw.gameplay_music_pleasant_creek);
                Games.setLooping(true);
            }

            if (Games != null && !Games.isPlaying()) {
                // Start the background music if it's not already playing
                Games.start();
            }
        }
    }
    public void StopGamesBackgroundMusic() {
        if (Games != null && Games.isPlaying()) {
            // Stop the background music if it's playing
            Games.stop();
            Games.release();
            Games = null;
        }
    }


//    //MODES AND MORE BACKGROUND MUSIC
//    static MediaPlayer More;
//    public static void PlayMoreMusic(Context context) {
//        boolean[] bgPass = Z_SoundManager.isBgon;
//
//        if (bgPass.length > 0 && bgPass[0]) {
//            if (More == null) {
//                // Initialize the MediaPlayer with the audio resource
//                More = MediaPlayer.create(context, R.raw.modes_trem);
//                More.setLooping(true);
//            }
//
//            if (More != null && !More.isPlaying()) {
//                // Start the background music if it's not already playing
//                More.start();
//            }
//        }
//    }
//    public static void StopMoreMusic() {
//        if (More != null && More.isPlaying()) {
//            // Stop the background music if it's playing
//            More.stop();
//            More.release();
//            More = null;
//        }
//    }



    //CHART AND CHARACTER BACKGROUND MUSIC
//    static MediaPlayer Chart;
//    public static void PlayChartBgMusic(Context context) {
//        boolean[] bgPass = MainMenu.isBgon;
//
//        if (bgPass.length > 0 && bgPass[0]) {
//            if (Chart == null) {
//                // Initialize the MediaPlayer with the audio resource
//                Chart = MediaPlayer.create(context, R.raw.chart_new_morning_new_sun);
//                Chart.setLooping(true);
//            }
//
//            if (Chart != null && !Chart.isPlaying()) {
//                // Start the background music if it's not already playing
//                Chart.start();
//            }
//        }
//    }
//    public static void StopChartBgMusic() {
//        if (Chart != null && Chart.isPlaying()) {
//            // Stop the background music if it's playing
//            Chart.stop();
//            Chart.release();
//            Chart = null;
//        }
//    }
//    private static boolean activityChapterPaused = false;
//    private static boolean activityLetterPaused = false;
//
//    public static void setActivityChapterPaused(boolean paused) {
//        activityChapterPaused = paused;
//        if (activityChapterPaused && activityLetterPaused) {
//            Z_SoundManager.StopChartBgMusic();
//        }
//    }
//    public static void setActivityChapterResumed(Context context) {
//        activityChapterPaused = false;
//        Z_SoundManager.PlayChartBgMusic(context);
//    }
//    public static void setActivityLetterPaused(boolean paused) {
//        activityLetterPaused = paused;
//        if (activityChapterPaused && activityLetterPaused) {
//            Z_SoundManager.StopChartBgMusic();
//        }
//    }
//    public static void setActivityLetterResumed(Context context) {
//        activityLetterPaused = false;
//        Z_SoundManager.PlayChartBgMusic(context);
//    }


    //LESSONS BACKGROUND MUSIC
//    static MediaPlayer Lessons;
//    public static void PlayLessonsBgMusic(Context context) {
//        boolean[] bgPass = Z_SoundManager.isBgon;
//
//        if (bgPass.length > 0 && bgPass[0]) {
//            if (Lessons == null) {
//                // Initialize the MediaPlayer with the audio resource
//                Lessons = MediaPlayer.create(context, R.raw.lessons_happy_holiday); //lessons_jump_and_run
//                Lessons.setLooping(true);
//            }
//
//            if (Lessons != null && !Lessons.isPlaying()) {
//                // Start the background music if it's not already playing
//                Lessons.start();
//            }
//        }
//    }
//    public static void StopLessonsBgMusic() {
//        if (Lessons != null && Lessons.isPlaying()) {
//            // Stop the background music if it's playing
//            Lessons.stop();
//            Lessons.release();
//            Lessons = null;
//            //Lessons.pause();
//        }
//    }
//    private static boolean activityLessonsPaused = false;
//    private static boolean activityCharactersPaused = false;
//    private static boolean activityRulesPaused = false;
//    private static boolean activityPaintPaused = false;

//    public static void setActivityLessonsPaused(boolean paused) {
//        activityLessonsPaused = paused;
//        if (activityLessonsPaused && activityCharactersPaused && activityRulesPaused && activityPaintPaused) {
//            Z_SoundManager.StopLessonsBgMusic();
//        }
//    }
//
//    public static void setActivityLessonsResumed(Context context) {
//        activityLessonsPaused = false;
//        Z_SoundManager.PlayLessonsBgMusic(context);
//
//    }
//    public static void setActivityCharactersPaused(boolean paused) {
//        activityCharactersPaused = paused;
//        if (activityCharactersPaused && activityLessonsPaused) {
//            Z_SoundManager.StopLessonsBgMusic();
//        }
//    }
//    public static void setActivityCharactersResumed(Context context) {
//        activityCharactersPaused = false;
//        Z_SoundManager.PlayLessonsBgMusic(context);
//    }
//    public static void setActivityRulesPaused(boolean paused) {
//        activityRulesPaused = paused;
//        if (activityRulesPaused && activityLessonsPaused) {
//            Z_SoundManager.StopLessonsBgMusic();
//        }
//    }
//    public static void setActivityRulesResumed(Context context) {
//        activityRulesPaused = false;
//        Z_SoundManager.PlayLessonsBgMusic(context);
//    }
//    public static void setActivityPaintPaused(boolean paused) {
//        activityPaintPaused = paused;
//        if (activityPaintPaused && activityLessonsPaused) {
//            Z_SoundManager.StopLessonsBgMusic();
//        }
//    }
//    public static void setActivityPaintResumed(Context context) {
//        activityPaintPaused = false;
//        Z_SoundManager.PlayLessonsBgMusic(context);
//    }


    //LESSONS DRAWING SOUND EFFECTS
    static MediaPlayer Drawing;
    public static void PlayDrawing(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            if (Drawing == null) {
                // Initialize the MediaPlayer with the audio resource
                Drawing = MediaPlayer.create(context, R.raw.draw_writing); //lessons_jump_and_run
                Drawing.setLooping(true);
            }

            if (Drawing != null && !Drawing.isPlaying()) {
                // Start the background music if it's not already playing
                Drawing.start();
            }
        }
    }
    public static void StopDrawing() {
        if (Drawing != null && Drawing.isPlaying()) {
            // Stop the background music if it's playing
            Drawing.stop();
            Drawing.release();
            Drawing = null;
        }
    }


    //PAINT ERASE SOUND
    static MediaPlayer paintEraseSound;
    public void PaintEraseSound(Context context) {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            paintEraseSound = MediaPlayer.create(context, R.raw.draw_erase);
            paintEraseSound.start();
            paintEraseSound.setOnCompletionListener(MediaPlayer::release);
        }
    }
}







