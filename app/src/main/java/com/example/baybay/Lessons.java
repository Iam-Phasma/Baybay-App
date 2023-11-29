package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class Lessons extends AppCompatActivity {

    ImageButton L1_Introduction;
    ImageButton L2_Characters;
    ImageButton L3_Rules;
    ImageButton L4_Handriting;
    ImageButton L5_Reading;
    private Toast globalToast;
    TextView TvKeys;
    SharedPreferences sharedPreferences;

//    public static int trophies = 0;
//    public int lesson2Price = 10;
//    public int lesson3Price = 20;
//    public int lesson4Price = 30;
//    public int lesson5Price = 40;
//    public boolean lesson2isUnlock = false;
//    public boolean lesson3isUnlock = false;
//    public boolean lesson4isUnlock = false;
//    public static boolean lesson5isUnlock = false;

    ImageButton LockIconL2;
    ImageButton LockIconL3;
    ImageButton LockIconL4;
    ImageButton LockIconL5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_lessons);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the background to a drawable resource
        getWindow().setBackgroundDrawableResource(R.drawable.bg_lessons3);

        globalToast = new Toast(getApplicationContext());

        //Trophies
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //Z_LessonManager.trophies = sharedPreferences.getInt("trophies", 0);
        Z_LessonManager.lesson2isUnlock = sharedPreferences.getBoolean("lesson2", false);
        Z_LessonManager.lesson3isUnlock = sharedPreferences.getBoolean("lesson3", false);
        Z_LessonManager.lesson4isUnlock = sharedPreferences.getBoolean("lesson4", false);
        Z_LessonManager.lesson5isUnlock[0] = sharedPreferences.getBoolean("lesson5", false);


         LockIconL2 = findViewById(R.id.lockicon_l2);
         LockIconL3 = findViewById(R.id.lockicon_l3);
         LockIconL4 = findViewById(R.id.lockicon_l4);
         LockIconL5 = findViewById(R.id.lockicon_l5);

        refreshLockIcons();

        //Refresh keys
        TvKeys = findViewById(R.id.tv_keys);
        //TvKeys.setText(String.valueOf(Z_LessonManager.trophies));

        int trophies = Z_TrophyManager.getTrophies();
        TvKeys.setText(String.valueOf(trophies));


        //Setting GIFs
        ImageView GifWave = findViewById(R.id.gif_wave1);
        try {
            Glide.with(this).load(R.drawable.lessons_wave).into(GifWave);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView GifNote = findViewById(R.id.gif_note2);
        try {
            Glide.with(this).load(R.drawable.lessons_notes).into(GifNote);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView GifFlag = findViewById(R.id.gif_flag3);
        try {
            Glide.with(this).load(R.drawable.lessons_flag).into(GifFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView GifGlobe = findViewById(R.id.gif_globe4);
        try {
            Glide.with(this).load(R.drawable.lessons_globe).into(GifGlobe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView GifCowboy = findViewById(R.id.gif_cowboy5);
        try {
            Glide.with(this).load(R.drawable.lessons_cowboy).into(GifCowboy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageButton ImgbtnGameplayHistory = findViewById(R.id.btn_lesson_gameplay_history);
        ImgbtnGameplayHistory.setOnClickListener(v -> {
            cancelToast();
            ClickSoundEffect();
            animateButton(ImgbtnGameplayHistory);
            ImgbtnGameplayHistory.setEnabled(false);
            new Handler().postDelayed(() -> {
                Intent Lessons = new Intent(getApplicationContext(), Gameplay_History.class);
                startActivity(Lessons);
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                //Lessons.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //finish();
                ImgbtnGameplayHistory.setEnabled(true);
            }, 500);
            //BACKGROUND MUSIC
            Z_SoundManager.setActivityCharactersPaused(false);
        });


        ImageButton ImgbtnInformation = findViewById(R.id.btn_lesson_information);
        ImgbtnInformation.setOnClickListener(v -> {
            cancelToast();
            ClickSoundEffect();
            animateButton(ImgbtnInformation);
            ImgbtnInformation.setEnabled(false);
            new Handler().postDelayed(() -> {
                ImgbtnInformation.setEnabled(true);
            }, 700);
            //Show Lessons Toast
            globalToast = Toasty.info(Lessons.this,"Play mini games to earn trophies and use it to unlock guide lessons!", Toast.LENGTH_SHORT);
            globalToast.show();
        });

        ImageButton ImgbtnLessonsReset = findViewById(R.id.btn_lesson_reset);
        ImgbtnLessonsReset.setOnClickListener(v -> {
            cancelToast();
            animateButton(ImgbtnLessonsReset);
            globalToast = Toasty.info(Lessons.this,"If you wish to reset your progress or start over, long press the button carefully.", Toast.LENGTH_LONG);
            globalToast.show();
        });

        ImgbtnLessonsReset.setOnLongClickListener(v -> {
            try {
                final Dialog dlg = new Dialog(Lessons.this, R.style.PopupDialog);
                dlg.setCanceledOnTouchOutside(false);
                dlg.setContentView(R.layout.activity_lessons_reset);
                dlg.show();

                //Prevents back press on sound dialog menu
                dlg.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP);

                final boolean[] isAgreed = {false};
                LottieAnimationView checkbox = dlg.findViewById(R.id.cb_reset_agree);
                checkbox.setOnClickListener(v1 -> {
                    cancelToast();
                    if (!isAgreed[0]) {
                        // Play animation forward
                        checkbox.setSpeed(1);
                        checkbox.playAnimation();
                        globalToast = Toasty.warning(Lessons.this, "I agree and understand the notice as this action cannot be undone.", Toast.LENGTH_LONG);
                        globalToast.show();

                        new Handler().postDelayed(checkbox::pauseAnimation, 1000);
                    } else {
                        globalToast = Toasty.warning(Lessons.this, "Unchecked", Toast.LENGTH_SHORT);
                        globalToast.show();
                        checkbox.setSpeed(0);
                        checkbox.playAnimation();
                    }
                    isAgreed[0] = !isAgreed[0];
                });


                ImageButton ImgbtnResetOk = dlg.findViewById(R.id.imgbtn_reset_ok);
                ImgbtnResetOk.setOnClickListener(v12 -> {
                    globalToast.cancel();
                    if(isAgreed[0]){
                        cancelToast();
                        globalToast = Toasty.success(Lessons.this, "Progress has been reset successfully!", Toast.LENGTH_SHORT);
                        globalToast.show();

//                        Z_LessonManager.trophies = 0;
//
//                        SharedPreferences.Editor editorTrophy = sharedPreferences.edit();
//                        editorTrophy.putInt("trophies", Z_LessonManager.trophies);
//                        editorTrophy.apply();
//                        TvKeys.setText(String.valueOf(Z_LessonManager.trophies));

                        Z_TrophyManager trophyManager = Z_TrophyManager.getInstance(getApplicationContext());
                        trophyManager.setTrophies(0);

                        Z_LessonManager.lesson2isUnlock = false;
                        SharedPreferences.Editor lesson2 = sharedPreferences.edit();
                        lesson2.putBoolean("lesson2", false);
                        lesson2.apply();

                        Z_LessonManager.lesson3isUnlock = false;
                        SharedPreferences.Editor lesson3 = sharedPreferences.edit();
                        lesson3.putBoolean("lesson3", false);
                        lesson3.apply();

                        Z_LessonManager.lesson4isUnlock = false;
                        SharedPreferences.Editor lesson4 = sharedPreferences.edit();
                        lesson4.putBoolean("lesson4", false);
                        lesson4.apply();

                        Z_LessonManager.lesson5isUnlock[0] = false;
                        SharedPreferences.Editor editorL5 = sharedPreferences.edit();
                        editorL5.putBoolean("lesson5", false);
                        editorL5.apply();

                        //Gameplay History
                        if (Gameplay_History.gameplaysList == null) {
                            Gameplay_History.gameplaysList = new ArrayList<>();
                        } else {
                            Gameplay_History.gameplaysList.clear();
                        }
                        saveGameplayList();

                        //Score array for chart
                        // Clear the Quizscorelist
                        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(this);
                        scoreManager.clearQuizScoreList();
                        scoreManager.clearMatchScoreList();
                        scoreManager.clearSpellScoreList();

                        refreshLockIcons();
                        dlg.dismiss();
                        recreate();
                    }else{
                        globalToast = Toasty.error(Lessons.this, "To perform the action, you must confirm your agreement.", Toast.LENGTH_SHORT);
                        globalToast.show();
                    }
                });

                ImageButton ImgbtnResetBack = dlg.findViewById(R.id.imgbtn_reset_back);
                ImgbtnResetBack.setOnClickListener(v13 -> dlg.dismiss());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        });

        ImageButton ImgbtnLessonExit = findViewById(R.id.btn_lesson_exit);
        ImgbtnLessonExit.setOnClickListener(v -> {
            ClickSoundEffect();
           onBackPressed();
        });

//        TvKeys.setOnClickListener(v -> {
//            if (Z_TrophyManager.getTrophies() <= 994){
//
//                Z_TrophyManager.trophies += 5;
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("trophies", Z_LessonManager.trophies);
//                editor.apply();
//                TvKeys.setText(String.valueOf(Z_LessonManager.trophies));
//                int currentTrophies = Z_TrophyManager.getTrophies();
//                int newTrophies = currentTrophies + 5;
//                Z_TrophyManager.setTrophies(newTrophies);
//            }
//        });

        L1_Introduction = findViewById(R.id.imgbtn_nursery);
        L1_Introduction.setOnClickListener(v -> {
            cancelToast();
            ClickSoundEffect();
            disableLessonsButton();
            animateButton(L1_Introduction);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent lessonsIntent = new Intent(getApplicationContext(), Lessons_Introduction.class);
                startActivity(lessonsIntent);
                finish();
            }, 500);

        });

        L2_Characters = findViewById(R.id.imgbtn_characters);
        L2_Characters.setOnClickListener(v -> {
            try {
                cancelToast();
                if (Z_LessonManager.lesson2isUnlock){
                    ClickSoundEffect();
                    disableLessonsButton();
                    animateButton(L2_Characters);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent Lessons = new Intent(getApplicationContext(), Lessons_CharSounds.class);
                        startActivity(Lessons);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //Lessons.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        finish();
                    }, 500);
                    //BACKGROUND MUSIC
                    Z_SoundManager.setActivityCharactersPaused(false);
                }else{
                    if(Z_TrophyManager.getTrophies() >= Z_LessonManager.lesson2Price){
                        Z_LessonManager.lesson2isUnlock = true;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("lesson2", Z_LessonManager.lesson2isUnlock);
                        editor.apply();
                        refreshLockIcons();
                        globalToast = Toasty.success(Lessons.this, "You have unlocked Lesson 2: Characters!", Toast.LENGTH_SHORT);
                        globalToast.show();
                    }else{
                        globalToast = Toasty.info(Lessons.this, "You need at least " + Z_LessonManager.lesson2Price + " trophies to unlock this Lesson.", Toast.LENGTH_SHORT);
                        globalToast.show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        L3_Rules = findViewById(R.id.imgbtn_rules);
        L3_Rules.setOnClickListener(v -> {
            try {
                cancelToast();
                if(Z_LessonManager.lesson3isUnlock){
                    ClickSoundEffect();
                    disableLessonsButton();
                    animateButton(L3_Rules);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent Lessons = new Intent(getApplicationContext(), Lessons_Rules.class);
                        startActivity(Lessons);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //Lessons.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        finish();
                    }, 500);
                    //BACKGROUND MUSIC
                    Z_SoundManager.setActivityRulesPaused(false);
                }else{
                    globalToast.cancel();
                    if(Z_LessonManager.lesson2isUnlock){
                        if(Z_TrophyManager.getTrophies() >= Z_LessonManager.lesson3Price){
                            Z_LessonManager.lesson3isUnlock = true;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("lesson3", Z_LessonManager.lesson3isUnlock);
                            editor.apply();
                            refreshLockIcons();
                            globalToast = Toasty.success(Lessons.this, "You have unlocked Lesson 3: Rules!", Toast.LENGTH_SHORT);
                            globalToast.show();
                        }else{
                            globalToast = Toasty.info(Lessons.this, "You need at least " + Z_LessonManager.lesson3Price + " trophies to unlock this Lesson.", Toast.LENGTH_SHORT);
                            globalToast.show();
                        }
                    }else{
                        globalToast = Toasty.error(Lessons.this, "Unlock Lesson 2: Characters first!", Toast.LENGTH_SHORT);
                        globalToast.show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        L4_Handriting = findViewById(R.id.imgbtn_handwriting);
        L4_Handriting.setOnClickListener(v -> {
            try {
                cancelToast();
                if(Z_LessonManager.lesson4isUnlock){
                    ClickSoundEffect();
                    disableLessonsButton();
                    animateButton(L4_Handriting);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent Lessons = new Intent(getApplicationContext(), Lessons_Paint.class);
                        startActivity(Lessons);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //Lessons.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        finish();
                    }, 500);
                    //BACKGROUND MUSIC
                    Z_SoundManager.setActivityPaintPaused(false);
                }else{
                    if(Z_LessonManager.lesson3isUnlock){
                        if(Z_TrophyManager.getTrophies() >= Z_LessonManager.lesson4Price){
                            Z_LessonManager.lesson4isUnlock = true;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("lesson4", Z_LessonManager.lesson4isUnlock);
                            editor.apply();
                            refreshLockIcons();
                            globalToast = Toasty.success(Lessons.this, "You have unlocked Lesson 4: Handwriting!", Toast.LENGTH_SHORT);
                            globalToast.show();
                        }else{
                            globalToast = Toasty.info(Lessons.this, "You need at least " + Z_LessonManager.lesson4Price + " trophies to unlock this Lesson.", Toast.LENGTH_SHORT);
                            globalToast.show();
                        }
                    }else{
                        globalToast = Toasty.error(Lessons.this, "Unlock Lesson 3: Rules first!", Toast.LENGTH_SHORT);
                        globalToast.show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        L5_Reading =  findViewById(R.id.imgbtn_reading);
        L5_Reading.setOnClickListener(v -> {
            try {
                cancelToast();
                if (Z_LessonManager.lesson4isUnlock) {
                    if (Z_LessonManager.lesson5isUnlock[0]) {
                        ClickSoundEffect();
                        disableLessonsButton();
                        animateButton(L5_Reading);
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            Intent Lessons = new Intent(getApplicationContext(), Library.class);
                            startActivity(Lessons);
                            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            //Lessons.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            finish();
                        }, 500);
                        //Stop BG Music
                        Z_SoundManager.StopLessonsBgMusic();
                    } else {
                        if (Z_TrophyManager.getTrophies() >= Z_LessonManager.lesson5Price) {
                            Z_LessonManager.lesson5isUnlock[0] = true; // Update it to true
                            SharedPreferences.Editor editorL5 = sharedPreferences.edit();
                            editorL5.putBoolean("lesson5", true); // Also update shared preferences
                            editorL5.apply();
                            refreshLockIcons();
                            globalToast = Toasty.success(Lessons.this, "You have unlocked Lesson 5: Reading!", Toast.LENGTH_SHORT);
                            globalToast.show();
                        } else {
                            globalToast = Toasty.warning(Lessons.this, "You need at least " + Z_LessonManager.lesson5Price + " trophies to unlock this Lesson.", Toast.LENGTH_SHORT);
                            globalToast.show();
                        }
                    }
                }else{
                    globalToast = Toasty.error(Lessons.this, "Unlock Lesson 4: Handwriting first!", Toast.LENGTH_SHORT);
                    globalToast.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    private void saveGameplayList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> gameplaySet = new HashSet<>();

        for (Gameplay gameplay : Gameplay_History.gameplaysList) {
            gameplaySet.add(gameplay.getGameplay());
        }
        editor.putStringSet("userList", gameplaySet);
        editor.apply();
    }

    //Prevent double click
    public void disableLessonsButton(){
        L1_Introduction.setEnabled(false);
        L2_Characters.setEnabled(false);
        L3_Rules.setEnabled(false);
        L4_Handriting.setEnabled(false);
        L5_Reading.setEnabled(false);
    }

    public void enableLessonsButtons(){
        L1_Introduction.setEnabled(true);
        L2_Characters.setEnabled(true);
        L3_Rules.setEnabled(true);
        L4_Handriting.setEnabled(true);
        L5_Reading.setEnabled(true);
    }


/*
    //Method for abakada video player
    public void abakadaVideoDialog() {
        try {
            // Stop BG Music
            Z_SoundManager.StopLessonsBgMusic();

            final Dialog dlg = new Dialog(Lessons.this, R.style.PopupDialog);
            dlg.setCanceledOnTouchOutside(false);
            dlg.setContentView(R.layout.activity_lessons_videoplayer);
            dlg.show();

            VideoView videoViewAbakada = dlg.findViewById(R.id.videoView_abakada);
            if (videoViewAbakada != null) {
                int videoResource = getResources().getIdentifier("abakada_video_compresed", "raw", getPackageName());
                if (videoResource != 0) {
                    videoViewAbakada.setVideoPath("android.resource://" + getPackageName() + "/" + videoResource);

                    SeekBar SeekbarVideo = dlg.findViewById(R.id.seekBar_video);

                    // Start the video playback
                    videoViewAbakada.start();

                    // Update SeekBar progress using a Handler
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int currentPosition = videoViewAbakada.getCurrentPosition();
                            int totalDuration = videoViewAbakada.getDuration();

                            // Update SeekBar progress based on video position
                            SeekbarVideo.setMax(totalDuration);
                            SeekbarVideo.setProgress(currentPosition);

                            // Schedule the handler to run again after a short delay
                            handler.postDelayed(this, 1000); // Update every 1 second
                        }
                    }, 0);

                    // Set an OnCompletionListener to restart the video when it reaches the end
                    videoViewAbakada.setOnCompletionListener(mp -> {
                        // Restart the video
                        videoViewAbakada.start();
                    });

                    // Add an OnSeekBarChangeListener to the SeekBar
                    SeekbarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                // Calculate the timestamp to jump to based on SeekBar progress
                                int newPosition = (int) (((float) progress / seekBar.getMax()) * videoViewAbakada.getDuration());

                                // Jump to the specified timestamp in the video
                                videoViewAbakada.seekTo(newPosition);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            // Not needed for this implementation
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            // Not needed for this implementation
                        }
                    });

                    ImageButton ImgbtnPlayPause = dlg.findViewById(R.id.imgbtn_playpause);
                    ImgbtnPlayPause.setOnClickListener(v -> {
                        if (videoViewAbakada.isPlaying()) {
                            // Pause the video and change the button icon to play
                            videoViewAbakada.pause();
                            ImgbtnPlayPause.setImageResource(R.drawable.video_pause_icon); // Change to your play icon resource
                        } else {
                            // Resume the video and change the button icon to pause
                            videoViewAbakada.start();
                            ImgbtnPlayPause.setImageResource(R.drawable.video_play_icon); // Change to your pause icon resource
                        }
                    });

                    ImageButton ImgVideoExit = dlg.findViewById(R.id.imgbtn_videoexit);
                    ImgVideoExit.setOnClickListener(v -> {
                        // Start BG Music
                        Z_SoundManager.PlayLessonsBgMusic(getApplicationContext());
                        enableLessonsButtons();
                        dlg.dismiss();
                    });
                } else {
                    // Handle the case where the resource is missing or invalid
                    dlg.dismiss();
                    // Display an error message to the user or take appropriate action
                }
            } else {
                // Handle the case where the VideoView is not found in the layout
                dlg.dismiss();
                // Display an error message to the user or take appropriate action
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception gracefully, e.g., display an error message
        }
    }*/


    private void refreshLockIcons(){
        //Refresh Lock icons
        if (Z_LessonManager.lesson2isUnlock){
            LockIconL2.setVisibility(View.INVISIBLE);
        }
        if (Z_LessonManager.lesson3isUnlock){
            LockIconL3.setVisibility(View.INVISIBLE);
        }
        if (Z_LessonManager.lesson4isUnlock){
            LockIconL4.setVisibility(View.INVISIBLE);
        }
        if (Z_LessonManager.lesson5isUnlock[0]){
            LockIconL5.setVisibility(View.INVISIBLE);
        }
    }


    // Method to animate the button click
    private void animateButton(View view) {
        // Create a scale animator to shrink the button
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 0.9f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 0.9f)
        );
        shrinkAnimator.setDuration(200); // Set the duration of the shrink animation

        // Create a scale animator to restore the button to its original size
        ObjectAnimator restoreAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f, 1.0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f, 1.0f)
        );
        restoreAnimator.setDuration(300); // Set the duration of the restore animation

        // Set up the animator set to play the shrink and restore animations sequentially
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(shrinkAnimator, restoreAnimator);

        // Start the button click animation
        animatorSet.start();
    }


    // Call the RegButtonClickSound method from Z_SoundManager
    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Dismiss the Paint Toast if showing
        cancelToast();
        Z_SoundManager.setActivityLessonsPaused(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(this::BackgroundSound, 200);
    }

    public void BackgroundSound(){
        Z_SoundManager.setActivityLessonsResumed(this);

        Z_SoundManager.setActivityCharactersPaused(true);
        Z_SoundManager.setActivityRulesPaused(true);
        Z_SoundManager.setActivityPaintPaused(true);
    }

    private void cancelToast() {
//        if (globalToast != null && globalToast.getView() != null && globalToast.getView().isShown()) {
//            globalToast.cancel();
//        }

        if (globalToast != null) {
            globalToast.cancel();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Stop BG Music
        cancelToast();
        Z_SoundManager.StopLessonsBgMusic();

        Intent Mainmenu = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(Mainmenu);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //Mainmenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
}