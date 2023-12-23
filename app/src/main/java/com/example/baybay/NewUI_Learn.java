package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.concurrent.atomic.AtomicBoolean;

public class NewUI_Learn extends AppCompatActivity {

    ImageButton ImgbtnLearExit;
    private LinearLayout hiddenButtonsLayout, hiddenButtons2Layout, hiddenButtons3Layout, hiddenButtons4Layout, hiddenButtons5Layout, hiddenButtons6Layout;
    private ImageButton Origin, Introduction, Characters, Rules, Handwriting, Reading;
    private ImageButton FullChart, Downloadable;
    private int bookNumber;
    private ImageButton L3Sub1, L3Sub2;
    private int CharSoundCount;
    private ImageButton L5Sub1, L5Sub2, L5Sub3;
    private int WritingCount;
    private ImageButton L6Sub1, L6Sub2, L6Sub3, L6Sub4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_learn);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        int singleColor = Color.parseColor("#FCF4E7");

        // Create the custom GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{singleColor, singleColor});

        // Set the gradient heights
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setGradientCenter(0, 0);
        gradientDrawable.setBounds(0, 0, getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

        // Set the custom GradientDrawable as the window background
        getWindow().setBackgroundDrawable(gradientDrawable);




        ImgbtnLearExit = findViewById(R.id.imgbtn_learn_exit);
        ImgbtnLearExit.setOnClickListener(v -> {
            ClickSoundEffect();
            finish();
        });

        FullChart = findViewById(R.id.imgbtn_gm_advanced);
        FullChart.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(FullChart);
            DisableNav();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Learn = new Intent(getApplicationContext(), NewUI_Chart_Letters.class);
                startActivity(Learn);
            }, 500);
        });

        Downloadable = findViewById(R.id.imgbtn_gameplayhistory_reset);
        Downloadable.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(Downloadable);
            DisableNav();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Learn = new Intent(getApplicationContext(), NewUI_Downloadable.class);
                startActivity(Learn);
            }, 500);
        });



        hiddenButtonsLayout = findViewById(R.id.hiddenButtonsLayout);
        hiddenButtons2Layout = findViewById(R.id.hiddenButtons2Layout);
        hiddenButtons3Layout = findViewById(R.id.hiddenButtons3Layout);
        hiddenButtons4Layout = findViewById(R.id.hiddenButtons4Layout);
        hiddenButtons5Layout = findViewById(R.id.hiddenButtons5Layout);
        hiddenButtons6Layout = findViewById(R.id.hiddenButtons6Layout);

        Origin = findViewById(R.id.mainButton1);
        Introduction = findViewById(R.id.mainButton2);
        Characters = findViewById(R.id.mainButton3);
        Rules = findViewById(R.id.mainButton4);
        Handwriting = findViewById(R.id.mainButton5);
        Reading = findViewById(R.id.mainButton6);

        AtomicBoolean isL1Clicked = new AtomicBoolean(false);
        Origin.setOnClickListener(v -> {
            animateButton(Origin);

            isL1Clicked.set(!isL1Clicked.get());

            if (isL1Clicked.get()) {
                FlipRightSound();
                Origin.setImageResource(R.drawable.newui_lesson1_sel);
            } else {
                FlipLeftSound();
                Origin.setImageResource(R.drawable.newui_lesson1_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtonsLayout);
        });

        AtomicBoolean isL2Clicked = new AtomicBoolean(false);
        Introduction.setOnClickListener(view -> {
            animateButton(Introduction);
            isL2Clicked.set(!isL2Clicked.get());

            if (isL2Clicked.get()) {
                FlipRightSound();
                Introduction.setImageResource(R.drawable.newui_lesson2_sel);
            } else {
                FlipLeftSound();
                Introduction.setImageResource(R.drawable.newui_lesson2_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons2Layout);
        });

        AtomicBoolean isL3Clicked = new AtomicBoolean(false);
        Characters.setOnClickListener(view -> {
            animateButton(Characters);
            isL3Clicked.set(!isL3Clicked.get());

            if (isL3Clicked.get()) {
                FlipRightSound();
                Characters.setImageResource(R.drawable.newui_lesson3_sel);
            } else {
                FlipLeftSound();
                Characters.setImageResource(R.drawable.newui_lesson3_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons3Layout);
        });

        L3Sub1 = findViewById(R.id.hidden3Button1);
        L3Sub1.setOnClickListener(v -> {
            CharSoundCount = 1;
            Intent Learn = new Intent(getApplicationContext(), Lessons_CharSounds.class);
            Learn.putExtra("progressbar-count", CharSoundCount);
            startActivity(Learn);
        });

        L3Sub2 = findViewById(R.id.hidden3Button2);
        L3Sub2.setOnClickListener(v -> {
            CharSoundCount = 6;
            Intent Learn = new Intent(getApplicationContext(), Lessons_CharSounds.class);
            Learn.putExtra("progressbar-count", CharSoundCount);
            startActivity(Learn);
        });

        AtomicBoolean isL4Clicked = new AtomicBoolean(false);
        Rules.setOnClickListener(view -> {
            animateButton(Rules);
            isL4Clicked.set(!isL4Clicked.get());

            if (isL4Clicked.get()) {
                FlipRightSound();
                Rules.setImageResource(R.drawable.newui_lesson4_sel);
            } else {
                FlipLeftSound();
                Rules.setImageResource(R.drawable.newui_lesson4_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons4Layout);
        });

        AtomicBoolean isL5Clicked = new AtomicBoolean(false);
        Handwriting.setOnClickListener(view -> {
            animateButton(Handwriting);
            isL5Clicked.set(!isL5Clicked.get());

            if (isL5Clicked.get()) {
                FlipRightSound();
                Handwriting.setImageResource(R.drawable.newui_lesson5_sel);
            } else {
                FlipLeftSound();
                Handwriting.setImageResource(R.drawable.newui_lesson5_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons5Layout);
        });

        L5Sub1 = findViewById(R.id.hidden5Button1);
        L5Sub1.setOnClickListener(v -> {
            WritingCount = 1;
            ClickSoundEffect();
            Intent Learn = new Intent(getApplicationContext(), Lessons_Paint.class);
            Learn.putExtra("writing-count", WritingCount);
            startActivity(Learn);
        });

        L5Sub2 = findViewById(R.id.hidden5Button2);
        L5Sub2.setOnClickListener(v -> {
            WritingCount = 6;
            ClickSoundEffect();
            Intent Learn = new Intent(getApplicationContext(), Lessons_Paint.class);
            Learn.putExtra("writing-count", WritingCount);
            startActivity(Learn);
        });

        AtomicBoolean isL6Clicked = new AtomicBoolean(false);
        Reading.setOnClickListener(view -> {
            animateButton(Reading);
            isL6Clicked.set(!isL6Clicked.get());

            if (isL6Clicked.get()) {
                FlipRightSound();
                Reading.setImageResource(R.drawable.newui_lesson6_sel);
            } else {
                FlipLeftSound();
                Reading.setImageResource(R.drawable.newui_lesson6_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons6Layout);
        });

        L6Sub1 = findViewById(R.id.hidden6Button1);
        L6Sub1.setOnClickListener(v -> {
            bookNumber = 1;
            ClickSoundEffect();
            goToBook();
        });
        L6Sub2 = findViewById(R.id.hidden6Button2);
        L6Sub2.setOnClickListener(v -> {
            bookNumber = 2;
            ClickSoundEffect();
            goToBook();
        });
        L6Sub3 = findViewById(R.id.hidden6Button3);
        L6Sub3.setOnClickListener(v -> {
            bookNumber = 3;
            ClickSoundEffect();
            goToBook();
        });
        L6Sub4 = findViewById(R.id.hidden6Button4);
        L6Sub4.setOnClickListener(v -> {
            bookNumber = 4;
            ClickSoundEffect();
            goToBook();
        });

    }

    private void goToBook(){
        PauseBGMusic();
        Intent intent = new Intent(NewUI_Learn.this, PdfViewerActivity.class);
        intent.putExtra("bookNumber", bookNumber);
        startActivity(intent);
    }

    private void DisableNav(){
        Downloadable.setEnabled(false);
        FullChart.setEnabled(false);
    }

    private void EnableNav(){
        Downloadable.setEnabled(true);
        FullChart.setEnabled(true);
    }

    private void toggleVisibilityWithAnimation(final LinearLayout layout) {
        layout.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int width = layout.getMeasuredWidth();

        if (layout.getVisibility() == View.VISIBLE) {
            // Slide out and fade out animation
            ObjectAnimator slideOut = ObjectAnimator.ofFloat(layout, "translationX", 0f, width);
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(layout, "alpha", 1f, 0f);

            slideOut.setInterpolator(new AccelerateDecelerateInterpolator());
            slideOut.setDuration(500);
            fadeOut.setDuration(500);

            slideOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    layout.setVisibility(View.GONE);
                }
            });

            slideOut.start();
            fadeOut.start();
        } else {
            // Slide in and fade in animation
            layout.setVisibility(View.VISIBLE);

            ObjectAnimator slideIn = ObjectAnimator.ofFloat(layout, "translationX", width, 0f);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(layout, "alpha", 0f, 1f);

            slideIn.setInterpolator(new AccelerateDecelerateInterpolator());
            slideIn.setDuration(500);
            fadeIn.setDuration(500);

            slideIn.start();
            fadeIn.start();
        }
    }

    private void PauseBGMusic(){
        Z_SoundManager.StopMainMenu_ModesBackgroundMusic();
    }
    @Override
    protected void onResume() {
        Z_SoundManager.setActivityMainMenuResumed(this);
        super.onResume();
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

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    void FlipLeftSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(NewUI_Learn.this, R.raw.page_flip_left);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
    }


    void FlipRightSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(NewUI_Learn.this, R.raw.pag_flip_right);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
    }

}