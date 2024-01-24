package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewUI_Gamemodes extends AppCompatActivity {
    // NEW BGMUSIC MANAGER
    private Z_BackgroundMusicService musicService;
    private boolean isBound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Z_BackgroundMusicService.LocalBinder binder = (Z_BackgroundMusicService.LocalBinder) service;
            musicService = binder.getService();
            musicService.startMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService.stopMusic();
            musicService = null;
        }
    };
    // -------
    int GametoPlay = 0;
    int difficulty = 0;
    private ImageButton GamemodesExit;
    private ImageButton GameHistory;
    private ImageButton Progress;
    private ImageView Quiz;
    private ImageView Spelling;
    private ImageView Matching;
    public ImageView ImgviewModeSlectedboard;
    private ImageButton ImgbtnSelectionstart;
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
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();




        SwipeRefreshLayout swipeContainer = findViewById(R.id.gamemodes_swiperefresh);
        swipeContainer.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                swipeContainer.setRefreshing(false);
                recreate();
            }, 700);
        });

        GamemodesExit = findViewById(R.id.imgbtn_gamemodes_exit);
        GamemodesExit.setOnClickListener(v -> {
            ClickSoundEffect();
            finish();
        });

        GameHistory = findViewById(R.id.imgbtn_gameplayhistory_reset);
        GameHistory.setOnClickListener(v -> {
            animateButton(GameHistory);
            ClickSoundEffect();
            DisableNav();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Gamemodes = new Intent(getApplicationContext(), NewUI_Gameplay_History.class);
                startActivity(Gamemodes);
            }, 500);
        });

        Progress = findViewById(R.id.imgbtn_gm_advanced);
        Progress.setOnClickListener(v -> {
            animateButton(Progress);
            ClickSoundEffect();
            DisableNav();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Gamemodes = new Intent(getApplicationContext(), NewUi_Progress_Chart.class);
                startActivity(Gamemodes);
            }, 500);
        });

        noOfGameplay(this);

        Quiz = findViewById(R.id.imgview_fb1_board);
        Quiz.setOnClickListener(v -> {
            GametoPlay = 1;
            ClickSoundEffect();
            DisableNav();
            openDialogGameSelection();
        });

        Spelling = findViewById(R.id.imgview_dl_r2_board);
        Spelling.setOnClickListener(v -> {
            GametoPlay = 2;
            ClickSoundEffect();
            DisableNav();
            openDialogGameSelection();
        });

        Matching = findViewById(R.id.imgview_dl_r3_board);
        Matching.setOnClickListener(v -> {
            GametoPlay = 3;
            ClickSoundEffect();
            DisableNav();
            openDialogGameSelection();
        });
    }

    private void setBackgroundColor(){
        int singleColor = Color.parseColor(Theme_Color.getDefaultColor());

        // Create the custom GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{singleColor, singleColor});

        // Set the gradient heights
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setGradientCenter(0, 0);
        gradientDrawable.setBounds(0, 0, getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

        // Set the custom GradientDrawable as the window background
        getWindow().setBackgroundDrawable(gradientDrawable);
    }

    private void openDialogGameSelection(){
        Dialog dlg;
        dlg = new Dialog(NewUI_Gamemodes.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  //disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_new_ui_gameselected_dialog);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        //Dismiss dialog when view is clicked
        ConstraintLayout ConstraintLayoutSnapshot = dlg.findViewById(R.id.constraintlayout_gamemodeselection);
        ConstraintLayoutSnapshot.setOnClickListener(v -> {
            dlg.dismiss();
        });

        //Dismiss dialog when clicked outside the layout
        dialogWindowView.setOnClickListener(v -> {
            dlg.dismiss();
        });

        ImgviewModeSlectedboard = dlg.findViewById(R.id.imgview_mode_selectedboard);

        if (GametoPlay == 1){
            ImgviewModeSlectedboard.setImageResource(R.drawable.newui_gm_g1selected);
        }else if (GametoPlay == 2){
            ImgviewModeSlectedboard.setImageResource(R.drawable.newui_gm_g2selected);
        }else if (GametoPlay == 3){
            ImgviewModeSlectedboard.setImageResource(R.drawable.newui_gm_g3selected);
        }

        //Close dialog reminder
        TextView TvSelectionBackreminder = dlg.findViewById(R.id.tv_gm_selection_backreminder);
        Animation fadeInAnimation = createContinuousFadeInAnimation();
        TvSelectionBackreminder.setVisibility(View.INVISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            TvSelectionBackreminder.startAnimation(fadeInAnimation);
            TvSelectionBackreminder.setVisibility(View.VISIBLE);
        }, 1000);

        //Textview difficulty reminder
        TextView TvDifficultySelected = dlg.findViewById(R.id.tv_difficulty_selected);

        ImageButton ImgbtnGamemodeClassic = dlg.findViewById(R.id.imgbtn_gameplayhistory_reset);
        ImageButton ImgbtnGamemodeAdvanced = dlg.findViewById(R.id.imgbtn_gm_advanced);

        difficulty = 1;
        ImgbtnGamemodeClassic.setOnClickListener(v -> {
            ClickSoundEffect();
            ImgbtnGamemodeClassic.setImageResource(R.drawable.newui_gm_classic_sel);
            ImgbtnGamemodeAdvanced.setImageResource(R.drawable.newui_gm_advanced_unsel);
            TvDifficultySelected.setText("Selected: Classic");

            difficulty = 1;
        });

        ImgbtnGamemodeAdvanced.setOnClickListener(v -> {
            ClickSoundEffect();
            ImgbtnGamemodeClassic.setImageResource(R.drawable.newui_gm_classic_unsel);
            ImgbtnGamemodeAdvanced.setImageResource(R.drawable.newui_gm_advanced_sel);
            TvDifficultySelected.setText("Selected: Advanced");

            difficulty = 2;
        });

        ImgbtnSelectionstart = dlg.findViewById(R.id.imgbtn_gm_selectionstart);
        ImgbtnSelectionstart.setOnClickListener(v -> {
            ClickSoundEffect();
            onStop();

            if (GametoPlay == 1){
                Intent Gamemodes = new Intent(getApplicationContext(), NewUI_Modes_Quiz.class);
                Gamemodes.putExtra("DIFFICULTY", difficulty);
                startActivity(Gamemodes);
                dlg.dismiss();
            } else if (GametoPlay == 2) {
                Intent Gamemodes = new Intent(getApplicationContext(), NewUI_Modes_Spelling.class);
                Gamemodes.putExtra("DIFFICULTY", difficulty);
                startActivity(Gamemodes);
                dlg.dismiss();
            } else if (GametoPlay == 3) {
                Intent Gamemodes = new Intent(getApplicationContext(), NewUI_Modes_Matching.class);
                Gamemodes.putExtra("DIFFICULTY", difficulty);
                startActivity(Gamemodes);
                dlg.dismiss();
            }

        });

        EnableNav();
    }


    private void DisableNav(){
        Quiz.setEnabled(false);
        Spelling.setEnabled(false);
        Matching.setEnabled(false);

        GameHistory.setEnabled(false);
        Progress.setEnabled(false);
    }

    private void EnableNav(){
        Quiz.setEnabled(true);
        Spelling.setEnabled(true);
        Matching.setEnabled(true);

        GameHistory.setEnabled(true);
        Progress.setEnabled(true);
    }

//    private void PauseBGMusic(){
//        Z_SoundManager.StopMainMenu_ModesBackgroundMusic();
//    }
//
//    @Override
//    protected void onResume() {
//        Z_SoundManager.setActivityMainMenuResumed(this);
//        super.onResume();
//    }

    private Animation createContinuousFadeInAnimation() {
        // Create a continuous fade-in animation
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000); // Set the duration of the fade-in animation in milliseconds
        fadeIn.setInterpolator(new LinearInterpolator()); // Use a linear interpolator
        fadeIn.setRepeatMode(Animation.REVERSE); // Reverse the animation when it reaches the end
        fadeIn.setRepeatCount(Animation.INFINITE); // Repeat indefinitely

        return fadeIn;
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

    @Override
    protected void onStart() {
        super.onStart();
        callMusic();
    }

    private void callMusic(){
        Intent intent = new Intent(this, Z_BackgroundMusicService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
}