package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Lessons_Rules extends AppCompatActivity {

    int progressbarRulesCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_lessons_rules);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Get the root view of the layout
        View rootView = getWindow().getDecorView().getRootView();

        // Set the background color
        rootView.setBackgroundColor(Color.parseColor("#F2F2F2"));

        refreshRulesPb();

        Button btnPrevious = findViewById(R.id.btn_rules_previous);
        btnPrevious.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(btnPrevious);
            progressbarRulesCount = (progressbarRulesCount == 1) ? 8 : progressbarRulesCount - 1;
            setRulesBoard();
            refreshRulesPb();
        });

        Button btnNext = findViewById(R.id.btn_rules_next);
        btnNext.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(btnNext);
            progressbarRulesCount = (progressbarRulesCount == 8) ? 1 : progressbarRulesCount + 1;
            setRulesBoard();
            refreshRulesPb();
        });


        Button btnExit = findViewById(R.id.btn_rules_exit);
        btnExit.setOnClickListener(v -> onBackPressed());
    }


    //Set Rules Board Image
    public void setRulesBoard(){
        ImageView ImgviewRulesBoard = findViewById(R.id.imgview_rulesboard);
        if (progressbarRulesCount == 1){
            ImgviewRulesBoard.setImageResource(R.drawable.rules_kudlit_2a);
        } else if (progressbarRulesCount == 2) {
            ImgviewRulesBoard.setImageResource(R.drawable.rules_kudlit_2b);
        } else if (progressbarRulesCount == 3) {
            ImgviewRulesBoard.setImageResource(R.drawable.rules_pamudpod_2a);
        }else if (progressbarRulesCount == 4) {
            ImgviewRulesBoard.setImageResource(R.drawable.rules_pamudpod_2b);
        }else if (progressbarRulesCount == 5) {
            ImgviewRulesBoard.setImageResource(R.drawable.rules_tuldok2);
        }else if (progressbarRulesCount == 6) {
            ImgviewRulesBoard.setImageResource(R.drawable.rules_kudlit_2c);
        }else if (progressbarRulesCount == 7) {
            ImgviewRulesBoard.setImageResource(R.drawable.rules_howtos_a);
        }else if (progressbarRulesCount == 8) {
            ImgviewRulesBoard.setImageResource(R.drawable.rules_howtos_b);
        }


    }


    public void refreshRulesPb(){
        ProgressBar pbRules = findViewById(R.id.progressBar_rules);
        pbRules.setProgress(progressbarRulesCount);
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
        Z_SoundManager.setActivityRulesPaused(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Z_SoundManager.setActivityRulesResumed(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //BACKGROUND MUSIC
        Z_SoundManager.setActivityLessonsPaused(false);

        Intent CharSounds = new Intent(getApplicationContext(), Lessons.class);
        startActivity(CharSounds);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //CharSounds.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
    }
}