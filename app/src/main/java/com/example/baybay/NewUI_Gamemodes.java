package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class NewUI_Gamemodes extends AppCompatActivity {

    private ImageButton GamemodesExit;
    private ImageButton GameHistory;
    private ImageButton Progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_gamemodes);

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



        GamemodesExit = findViewById(R.id.imgbtn_gamemodes_exit);
        GamemodesExit.setOnClickListener(v -> {
            ClickSoundEffect();
            finish();
        });

        GameHistory = findViewById(R.id.imgbtn_gm_gamehistory);
        GameHistory.setOnClickListener(v -> {
            animateButton(GameHistory);
            ClickSoundEffect();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent Gamemodes = new Intent(getApplicationContext(), Gameplay_History.class);
                startActivity(Gamemodes);
            }, 500);
        });



        Progress = findViewById(R.id.imgbtn_gm_progress);
        Progress.setOnClickListener(v -> {
            animateButton(Progress);
            ClickSoundEffect();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent Gamemodes = new Intent(getApplicationContext(), LineGraph.class);
                startActivity(Gamemodes);
            }, 500);
        });







        noOfGameplay(this);
    }

    private void noOfGameplay(Context context){
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(context);

        List<Integer> quizScoreList = scoreManager.getQuizScoreList();
        List<Integer> spellScoreList = scoreManager.getSpellScoreList();
        List<Integer> matchScoreList = scoreManager.getMatchScoreList();

        TextView QuizNoOfGame = findViewById(R.id.tv_gm_quiz_numberofgame);
        QuizNoOfGame.setText(String.valueOf(quizScoreList.size()));

        TextView SpellingNoOfGame = findViewById(R.id.tv_gm_spelling_numberofgame);
        SpellingNoOfGame.setText(String.valueOf(spellScoreList.size()));

        TextView MatchNoOfGame = findViewById(R.id.tv_gm_match_numberofgame);
        MatchNoOfGame.setText(String.valueOf(matchScoreList.size()));
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
}