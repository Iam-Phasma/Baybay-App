package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class Modes extends AppCompatActivity {

    private Toast globalToast;
    ImageButton BtnMode1, BtnMode2, BtnMode3;
    //private static MediaPlayer mediaPlayer;
    ImageView BoardSelected;
    TextView TxtMode1, TxtMode2, TxtMode3, TvPlay;
    int GametoPlay = 0;
    int difficulty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_game_modes);

        //Fullscreen beyond punch hole camera
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





        TextView TvGamemodesClassic = findViewById(R.id.tv_gamemodes_classic);
        TvGamemodesClassic.setEnabled(false);
        TextView TvGamemodesExtra = findViewById(R.id.tv_gamemodes_extra);
        TvGamemodesExtra.setEnabled(false);

        TvGamemodesClassic.setTextColor(Color.parseColor("#4C2424"));
        TvGamemodesClassic.setText("Classic");
        TvGamemodesExtra.setTextColor(Color.parseColor("#4C2424"));
        TvGamemodesExtra.setText("Advanced");

        TxtMode1 = findViewById(R.id.txt_quiz);
        TxtMode2 = findViewById(R.id.txt_search);
        TxtMode3 = findViewById(R.id.txt_match);

        BoardSelected = findViewById(R.id.imgview_board_selected);
        BtnMode1 = findViewById(R.id.btn_modes_1);
        BtnMode1.setOnClickListener(v -> {
            cancelToast();
            globalToast = Toasty.info(Modes.this, "Direction: Select the correct answer from the provided options.", Toast.LENGTH_LONG);
            globalToast.show();

            ClickSoundEffect();
            BtnMode1.setEnabled(false);
            BtnMode2.setEnabled(false);
            BtnMode3.setEnabled(false);

            TxtMode1.setTextColor(Color.parseColor("#FFEB3B"));
            TxtMode2.setTextColor(Color.parseColor("#4C2424"));
            TxtMode3.setTextColor(Color.parseColor("#4C2424"));

            // Add animation for the button click
            animateButton(BtnMode1);
            animateButton(TxtMode1);

            //TvGamemodesClassic.performClick();

            TvGamemodesClassic.setEnabled(true);
            TvGamemodesExtra.setEnabled(true);

            GametoPlay = 1;
            TvPlay.setEnabled(true);
            new Handler().postDelayed(() -> {
                BtnMode1.setEnabled(true);
                BtnMode2.setEnabled(true);
                BtnMode3.setEnabled(true);
            }, 300);
        });

        BtnMode2 = findViewById(R.id.btn_modes_2);
        BtnMode2.setOnClickListener(v -> {
            cancelToast();
            globalToast = Toasty.info(Modes.this, "Direction: Click the syllables in the correct order to spell the given word", Toast.LENGTH_LONG);
            globalToast.show();
            ClickSoundEffect();
            BtnMode1.setEnabled(false);
            BtnMode2.setEnabled(false);
            BtnMode3.setEnabled(false);

            TxtMode2.setTextColor(Color.parseColor("#FFEB3B"));
            TxtMode1.setTextColor(Color.parseColor("#4C2424"));
            TxtMode3.setTextColor(Color.parseColor("#4C2424"));

            // Add animation for the button click
            animateButton(BtnMode2);
            animateButton(TxtMode2);

            //TvGamemodesClassic.performClick();

            TvGamemodesClassic.setEnabled(true);
            TvGamemodesExtra.setEnabled(true);

            GametoPlay = 2;
            TvPlay.setEnabled(true);
            new Handler().postDelayed(() -> {
                BtnMode1.setEnabled(true);
                BtnMode2.setEnabled(true);
                BtnMode3.setEnabled(true);
            }, 300);
        });

        BtnMode3 = findViewById(R.id.btn_modes_3);
        BtnMode3.setOnClickListener(v -> {
            cancelToast();
            globalToast = Toasty.info(Modes.this, "Direction: Identify the matching counterpart of the given letter from the script pool.", Toast.LENGTH_LONG);
            globalToast.show();
            ClickSoundEffect();
            BtnMode1.setEnabled(false);
            BtnMode2.setEnabled(false);
            BtnMode3.setEnabled(false);

            TxtMode3.setTextColor(Color.parseColor("#FFEB3B"));
            TxtMode1.setTextColor(Color.parseColor("#4C2424"));
            TxtMode2.setTextColor(Color.parseColor("#4C2424"));

            // Add animation for the button click
            animateButton(BtnMode3);
            animateButton(TxtMode3);

            //TvGamemodesClassic.performClick();

            TvGamemodesClassic.setEnabled(true);
            TvGamemodesExtra.setEnabled(true);
            TvPlay.setEnabled(true);
            GametoPlay = 3;

            new Handler().postDelayed(() -> {
                BtnMode1.setEnabled(true);
                BtnMode2.setEnabled(true);
                BtnMode3.setEnabled(true);
            }, 300);
        });

        TvPlay = findViewById(R.id.tvPlay);
        TvPlay.setText("\nPLAY");
        TvPlay.setEnabled(false);

        TvGamemodesClassic.setOnClickListener(view -> {
            //animateButton(TvGamemodesClassic);
            difficulty = 1;
            TvGamemodesClassic.setTextColor(Color.parseColor("#FFEB3B"));
            TvGamemodesExtra.setTextColor(Color.parseColor("#4C2424"));

            TvGamemodesClassic.setText("- Classic -");
            TvGamemodesExtra.setText("Advanced");

            cancelToast();
            if (GametoPlay == 1){
                globalToast = Toasty.warning(Modes.this, "One script at a time!", Toast.LENGTH_LONG);
                globalToast.show();
            }else if (GametoPlay == 2){
                globalToast = Toasty.warning(Modes.this, "You have five attempts!", Toast.LENGTH_LONG);
                globalToast.show();
            }else if (GametoPlay == 3){
                globalToast = Toasty.warning(Modes.this, "You have two minutes timer!", Toast.LENGTH_LONG);
                globalToast.show();
            }
        });
        TvGamemodesExtra.setOnClickListener(view -> {
            //animateButton(TvGamemodesExtra);
            difficulty = 2;
            TvGamemodesClassic.setTextColor(Color.parseColor("#4C2424"));
            TvGamemodesExtra.setTextColor(Color.parseColor("#FFEB3B"));

            TvGamemodesClassic.setText("Classic");
            TvGamemodesExtra.setText("- Advanced -");

            cancelToast();
            if (GametoPlay == 1){
                globalToast = Toasty.warning(Modes.this, "One word at a time!", Toast.LENGTH_LONG);
                globalToast.show();
            }else if (GametoPlay == 2){
                globalToast = Toasty.warning(Modes.this, "Can't afford a mistake!", Toast.LENGTH_LONG);
                globalToast.show();
            }else if (GametoPlay == 3){
                globalToast = Toasty.warning(Modes.this, "Down to one minute timer!", Toast.LENGTH_LONG);
                globalToast.show();
            }
        });

        TvPlay.setOnClickListener(v -> {
            if (difficulty == 0){
                cancelToast();
                globalToast = Toasty.error(Modes.this, "Choose a game difficulty, Classic or Advanced!", Toast.LENGTH_SHORT);
                globalToast.show();
            }else{
                cancelToast();
                if (GametoPlay == 1){
                    Intent Gamemodes = new Intent(getApplicationContext(), Modes_Quiz.class);
                    Gamemodes.putExtra("DIFFICULTY", difficulty);
                    startActivity(Gamemodes);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    Gamemodes.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                } else if (GametoPlay == 2) {
                    Intent Gamemodes = new Intent(getApplicationContext(), Modes_Spell.class);
                    Gamemodes.putExtra("DIFFICULTY", difficulty);
                    startActivity(Gamemodes);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    Gamemodes.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();

                } else if (GametoPlay == 3) {
                    Intent Gamemodes = new Intent(getApplicationContext(), Modes_Matching.class);
                    Gamemodes.putExtra("DIFFICULTY", difficulty);
                    startActivity(Gamemodes);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            }

        });

        ImageButton BtnModesBack = findViewById(R.id.btn_modes_back);
        BtnModesBack.setOnClickListener(v -> {
            ClickSoundEffect();
            onBackPressed();
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        Z_SoundManager.setActivityModesPaused(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Z_SoundManager.setActivityModesResumed(this);
    }


    // Method to animate the button click
    private void animateButton(View view) {
        // Create a scale animator to shrink the button
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 0.7f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 0.7f)
        );
        shrinkAnimator.setDuration(200); // Set the duration of the shrink animation

        // Create a scale animator to restore the button to its original size
        ObjectAnimator restoreAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.7f, 1.0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.7f, 1.0f)
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
        cancelToast();
        Z_SoundManager.setActivityMainMenuPaused(false);

        Intent Mainmenu = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(Mainmenu);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //Mainmenu.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
    }

}