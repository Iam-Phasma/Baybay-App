package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Chart_Chapters extends AppCompatActivity {

    ImageButton ImgbtnExitChart;
    ImageButton Imgkudlit, ImgA, ImgB, ImgK, ImgD, ImgE, ImgG, ImgH, ImgI, ImgL, ImgM, ImgN, ImgNG, ImgO, ImgP, ImgR, ImgS, ImgT, ImgU, ImgW, ImgY;

    int cycleselect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_chart);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the background to a drawable resource
        getWindow().setBackgroundDrawableResource(R.drawable.chartletters_crop_bg);

        ImgbtnExitChart = findViewById(R.id.imgbtn_exit_chapter);
        ImgbtnExitChart.setOnClickListener(v -> {
            ClickSoundEffect();
            onBackPressed();
        });

        Imgkudlit = findViewById(R.id.btn_kudlit);
        ImgA = findViewById(R.id.btn_a);
        ImgB = findViewById(R.id.btn_b);
        ImgK = findViewById(R.id.btn_k);
        ImgD = findViewById(R.id.btn_d);
        ImgE = findViewById(R.id.btn_e);
        ImgG = findViewById(R.id.btn_g);
        ImgH = findViewById(R.id.btn_h);
        ImgI = findViewById(R.id.btn_i);
        ImgL = findViewById(R.id.btn_l);
        ImgM = findViewById(R.id.btn_m);
        ImgN = findViewById(R.id.btn_n);
        ImgNG = findViewById(R.id.btn_ng);
        ImgO = findViewById(R.id.btn_o);
        ImgP = findViewById(R.id.btn_p);
        ImgR = findViewById(R.id.btn_r);
        ImgS = findViewById(R.id.btn_s);
        ImgT = findViewById(R.id.btn_t);
        ImgU = findViewById(R.id.btn_u);
        ImgW = findViewById(R.id.btn_w);
        ImgY = findViewById(R.id.btn_y);

        // Declare the buttons array first
        ImageButton[] buttons = {
                Imgkudlit, ImgA, ImgB, ImgK, ImgD, ImgE, ImgG, ImgH, ImgI, ImgL, ImgM, ImgN,
                ImgNG, ImgO, ImgP, ImgR, ImgS, ImgT, ImgU, ImgW, ImgY
        };

        // Create a single OnClickListener instance
        View.OnClickListener buttonClickListener = v -> {
            ClickSoundEffect();
            animateButton(v);
            new Handler().postDelayed(() -> {
                Intent Chart = new Intent(getApplicationContext(), Chart_Letters.class);
                //passing the clicked button value
                Chart.putExtra("cycle", cycleselect);
                startActivity(Chart);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
                finish();
            }, 500);

            // Disable all buttons
            for (ImageButton button : buttons) {
                button.setEnabled(false);
            }

            ImgbtnExitChart.setEnabled(false);

            // BACKGROUND MUSIC
            //======Z_SoundManager.setActivityLetterPaused(false);
        };

        // Set the OnClickListener for all buttons except ImgbtnExitChart
        for (ImageButton button : buttons) {
            button.setOnClickListener(buttonClickListener);
        }

        // Array of cycleselect values for each button
        int[] cycleselectValues = {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
        };

        // Set the cycleselect value when a button is clicked
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(v -> {
                cycleselect = cycleselectValues[index];
                buttonClickListener.onClick(v);
            });
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //=======Z_SoundManager.setActivityChapterPaused(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        BackgroundSound();
    }


    void BackgroundSound() {
        //=======Z_SoundManager.setActivityChapterResumed(this);
        //=======Z_SoundManager.setActivityLetterPaused(true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Stop BG Music
        //=======Z_SoundManager.StopChartBgMusic();

        Intent Chart = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(Chart);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }


    // Method to animate the button click
    private void animateButton(View view) {
        // Create a scale animator to shrink the button
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 0.5f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 0.5f)
        );
        shrinkAnimator.setDuration(200); // Set the duration of the shrink animation

        // Create a scale animator to restore the button to its original size
        ObjectAnimator restoreAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 1.0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 1.0f)
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
}