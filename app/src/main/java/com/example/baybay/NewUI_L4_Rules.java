package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class NewUI_L4_Rules extends AppCompatActivity {

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

    int progressbarRulesCount = 1;
    private List<Integer> PracticeQuestionCount;
    ImageView ImgviewPracticeQuestion;
    EditText EdittextPractice;

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





        Intent intent = getIntent();
        int getrulesCount = intent.getIntExtra("rules-count", 1);
        progressbarRulesCount = getrulesCount;


        Button btnPrevious = findViewById(R.id.btn_rules_previous);
        btnPrevious.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(btnPrevious);
            progressbarRulesCount = (progressbarRulesCount == 1) ? 8 : progressbarRulesCount - 1;
            setRulesBoard();
        });

        Button btnNext = findViewById(R.id.btn_rules_next);
        btnNext.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(btnNext);
            progressbarRulesCount = (progressbarRulesCount == 8) ? 1 : progressbarRulesCount + 1;
            setRulesBoard();
        });

        ImageButton btnExit = findViewById(R.id.btn_rules_exit);
        btnExit.setOnClickListener(v -> onBackPressed());

        setRulesBoard();



        PracticeQuestionCount = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        Collections.shuffle(PracticeQuestionCount);
        RefreshPracticeQuestion();

        Button BtnSubmitPractice = findViewById(R.id.btn_rules_practice_submit);
        BtnSubmitPractice.setOnClickListener(v -> {
            if (!PracticeQuestionCount.isEmpty()) {
                PracticeQuestion();
            }else {
                Toasty.info(NewUI_L4_Rules.this, "You answered them all, great job!", Toast.LENGTH_SHORT).show();
                BtnSubmitPractice.setEnabled(false);
            }
        });
    }

    private void RefreshPracticeQuestion(){
        ImgviewPracticeQuestion = findViewById(R.id.imgview_practice_question);
        EdittextPractice = findViewById(R.id.edittext_practiceanswer);

        if (!PracticeQuestionCount.isEmpty()) {
            int currentQuestion = PracticeQuestionCount.get(0);
            switch (currentQuestion) {
                case 1:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_araw);
                    break;
                case 2:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_gamot);
                    break;
                case 3:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_nabasa);
                    break;
                case 4:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_saging);
                    break;
                case 5:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_bata);
                    break;
                case 6:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_hanap);
                    break;
                case 7:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_ngayon);
                    break;
                case 8:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_tasa);
                    break;
                case 9:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_kapote);
                    break;
                case 10:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_ilaw);
                    break;
                case 11:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_oras);
                    break;
                case 12:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_ulam);
                    break;
                case 13:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_dagat);
                    break;
                case 14:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_lamok);
                    break;
                case 15:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_pako);
                    break;
                case 16:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_wasto);
                    break;
                case 17:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_elesi);
                    break;
                case 18:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_manok);
                    break;
                case 19:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_rason);
                    break;
                case 20:
                    ImgviewPracticeQuestion.setImageResource(R.drawable.newui_tr_yaman);
                    break;
            }
        }
    }
    private void PracticeQuestion() {
        if (!PracticeQuestionCount.isEmpty()) {
            int currentQuestion = PracticeQuestionCount.get(0);
            String userAnswer = EdittextPractice.getText().toString().trim();

            switch (currentQuestion) {
                case 1:
                    handleQuestionAnswer(userAnswer, "Araw", "Correct!", "Try again.");
                    break;
                case 2:
                    handleQuestionAnswer(userAnswer, "Gamot", "Correct!", "Try again.");
                    break;
                case 3:
                    handleQuestionAnswer(userAnswer, "Nabasa", "Correct!", "Try again.");
                    break;
                case 4:
                    handleQuestionAnswer(userAnswer, "Saging", "Correct!", "Try again.");
                    break;
                case 5:
                    handleQuestionAnswer(userAnswer, "Bata", "Correct!", "Try again.");
                    break;
                case 6:
                    handleQuestionAnswer(userAnswer, "Hanap", "Correct!", "Try again.");
                    break;
                case 7:
                    handleQuestionAnswer(userAnswer, "Ngayon", "Correct!", "Try again.");
                    break;
                case 8:
                    handleQuestionAnswer(userAnswer, "Tasa", "Correct!", "Try again.");
                    break;
                case 9:
                    handleQuestionAnswer(userAnswer, "Kapote", "Correct!", "Try again.");
                    break;
                case 10:
                    handleQuestionAnswer(userAnswer, "Ilaw", "Correct!", "Try again.");
                    break;
                case 11:
                    handleQuestionAnswer(userAnswer, "Oras", "Correct!", "Try again.");
                    break;
                case 12:
                    handleQuestionAnswer(userAnswer, "Ulam", "Correct!", "Try again.");
                    break;
                case 13:
                    handleQuestionAnswer(userAnswer, "Dagat", "Correct!", "Try again.");
                    break;
                case 14:
                    handleQuestionAnswer(userAnswer, "Lamok", "Correct!", "Try again.");
                    break;
                case 15:
                    handleQuestionAnswer(userAnswer, "Pako", "Correct!", "Try again.");
                    break;
                case 16:
                    handleQuestionAnswer(userAnswer, "Wasto", "Correct!", "Try again.");
                    break;
                case 17:
                    handleQuestionAnswer(userAnswer, "Elesi", "Correct!", "Try again.");
                    break;
                case 18:
                    handleQuestionAnswer(userAnswer, "Manok", "Correct!", "Try again.");
                    break;
                case 19:
                    handleQuestionAnswer(userAnswer, "Rason", "Correct!", "Try again.");
                    break;
                case 20:
                    handleQuestionAnswer(userAnswer, "Yaman", "Correct!", "Try again.");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + currentQuestion);
            }
        }
    }

    private void handleQuestionAnswer(String userAnswer, String correctAnswer, String correctMessage, String wrongMessage) {
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            Toasty.success(NewUI_L4_Rules.this, correctMessage, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> {
                CorrectSound();
                PracticeQuestionCount.remove(0);
                RefreshPracticeQuestion();
                EdittextPractice.setText("");
            }, 1000);
        } else {
            Toasty.error(NewUI_L4_Rules.this, wrongMessage, Toast.LENGTH_SHORT).show();
            EdittextPractice.setText("");
            WrongSound();
        }
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

    void CorrectSound(){
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.GameCorrectSound(this);
        }
    }
    void WrongSound(){
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.GameWrongSound(this);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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