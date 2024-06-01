package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;


public class NewUI_Modes_Matching extends AppCompatActivity {
    private Toast globalToast;
    private Handler handler;
    ImageButton ImgbtnResume, ImgbtnRetry, ImgbtnQuit, ImgbtnHome;
    ImageButton Imgbtn_MatchingPause;
    private final List<String> MatchList = new ArrayList<>();
    private int currentIndex = 0;
    TextView Tv_MatchPrevious;
    TextView Tv_MatchCurrent;
    TextView Tv_MatchNext;
    ImageButton ImgbtnPass;
    private int wrongClickCount = 0;
    //private ImageButton imgbtnMatch;
    TextView TvResultIndicator;
    private boolean buttonsEnabled = true;
    TextView TvMatchCounter;
    int MatchQuestionCounter = 1;
    ImageButton ImgbtnResultAgain, ImgbtnResultQuit;
    SharedPreferences sharedPreferences;
    public int difficulty;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_matching);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();








        Intent intent = getIntent();
        difficulty = intent.getIntExtra("DIFFICULTY", 1);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        MatchQuestionCounter();

        InitializeTimer();

        TvResultIndicator = findViewById(R.id.tv_result_indicator);
        if(difficulty == 1){
            TvResultIndicator.setText("You have 2:00 minutes!");
        } else if (difficulty == 2) {
            TvResultIndicator.setText("You have 1:00 minute!");
        }

        TvResultIndicator.setTextSize(20);
        TvResultIndicator.setTextColor(ContextCompat.getColor(this, R.color.black));
        new Handler().postDelayed(() -> {
            TvResultIndicator.setVisibility(View.INVISIBLE);
            TvResultIndicator.setTextSize(24);
        }, 4000);

        handler = new Handler();

        Imgbtn_MatchingPause = findViewById(R.id.imgbtn_matchingpause);
        Imgbtn_MatchingPause.setOnClickListener(v -> {
            pauseTimer();

            Dialog dlg = new Dialog(NewUI_Modes_Matching.this, R.style.PopupDialog);
            dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
            dlg.setContentView(R.layout.activity_pause_menu);
            dlg.show();

            // Prevents back press on sound dialog menu
            dlg.setOnKeyListener((dialog, keyCode, event) -> {
                return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP; // Consume the back button press event
            });

            new Handler().postDelayed(this::QuizPauseVoice, 200);

            ImgbtnResume = dlg.findViewById(R.id.imgbtnResume);
            ImgbtnResume.setOnClickListener(v1 -> {
                resumeTimer();

                disablePauseMenuButtons();

                if(MatchQuestionCounter != 21){
                    resumeTimer();
                }

                animateButton(ImgbtnResume);
                new Handler().postDelayed(dlg::dismiss, 500);
            });

            ImgbtnRetry = dlg.findViewById(R.id.imgbtnRetry);
            ImgbtnRetry.setOnClickListener(v12 -> {
                disablePauseMenuButtons();
                stopTimer();
                ClickSoundEffect();
                animateButton(ImgbtnRetry);
                new Handler().postDelayed(() -> {
                    dlg.dismiss();
                    recreate();
                }, 500);
            });

            ImgbtnQuit = dlg.findViewById(R.id.imgbtnQuit);
            ImgbtnQuit.setOnClickListener(v13 -> {
                disablePauseMenuButtons();
                stopTimer();
                ClickSoundEffect();
                animateButton(ImgbtnQuit);
                new Handler().postDelayed(() -> {
                    //Stop BG Music
                    Z_SoundManager soundManager = new Z_SoundManager();
                    soundManager.StopGamesBackgroundMusic();

                    dlg.dismiss();
                    exitMatchingActivity();
                }, 500);
            });
        });


        //Set Image resource to the buttons
        initializeResourceValueMap();
        int[] imageResources = {
                R.drawable.match_script_a,
                R.drawable.match_script_ba,
                R.drawable.match_script_ka,
                R.drawable.match_script_da,
                R.drawable.match_script_e,
                R.drawable.match_script_ga,
                R.drawable.match_script_ha,
                R.drawable.match_script_i,
                R.drawable.match_script_la,
                R.drawable.match_script_ma,
                R.drawable.match_script_na,
                R.drawable.match_script_nga,
                R.drawable.match_script_o,
                R.drawable.match_script_pa,
                R.drawable.match_script_ra,
                R.drawable.match_script_sa,
                R.drawable.match_script_ta,
                R.drawable.match_script_u,
                R.drawable.match_script_wa,
                R.drawable.match_script_ya
        };

        List<Integer> imageList = new ArrayList<>();
        for (int resourceId : imageResources) {
            imageList.add(resourceId);
        }
        Collections.shuffle(imageList);


        for (int i = 1; i <= 20; i++) {
            final int resourceId = imageList.get(i - 1);
            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("imgbtnMatch" + i, "id", getPackageName());
            final ImageButton currentImgbtnMatch = findViewById(buttonId);

            currentImgbtnMatch.setImageResource(resourceId);

            currentImgbtnMatch.setOnClickListener(v -> {
                if (MatchQuestionCounter < 21/*currentIndex < MatchList.size() - 1*/) {
                    if (!buttonsEnabled) {
                        return; // Don't process clicks if buttons are disabled.
                    }

                    animateButton(currentImgbtnMatch);
                    String selectedValue = getResources().getResourceEntryName(resourceId);
                    checkMatch(selectedValue, currentImgbtnMatch);

                    disableAllButtons();

                    new Handler().postDelayed(this::enableAllButtons, 1000);
                }
            });
        }

        //Populating items to a list
        MatchList.add("a");
        MatchList.add("ba");
        MatchList.add("ka");
        MatchList.add("da");
        MatchList.add("e");
        MatchList.add("ga");
        MatchList.add("ha");
        MatchList.add("i");
        MatchList.add("la");
        MatchList.add("ma");
        MatchList.add("na");
        MatchList.add("nga");
        MatchList.add("o");
        MatchList.add("pa");
        MatchList.add("ra");
        MatchList.add("sa");
        MatchList.add("ta");
        MatchList.add("u");
        MatchList.add("wa");
        MatchList.add("ya");
        Collections.shuffle(MatchList);

        Tv_MatchPrevious = findViewById(R.id.tv_match_previous);
        Tv_MatchCurrent = findViewById(R.id.tv_match_current);
        Tv_MatchNext = findViewById(R.id.tv_match_next);

        updateTextViews();

        ImgbtnPass = findViewById(R.id.imgbtn_pass);
        ImgbtnPass.setOnClickListener(v -> {
            wrongClickCount = 0;
            if (currentIndex < MatchList.size() - 1) {
                currentIndex++;
                animateButton(ImgbtnPass);
                SkipSoundEffect();
                updateTextViews();

                MatchQuestionCounter ++;
                MatchQuestionCounter();
                ImgbtnPass.setEnabled(true);
            }

            new Handler().postDelayed(() -> ImgbtnPass.setEnabled(true), 500);
            ImgbtnPass.setEnabled(false);


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

    //Disable buttons to avoid spam
    private void disableAllButtons() {
        buttonsEnabled = false;
    }

    // Re-enable all buttons
    private void enableAllButtons() {
        buttonsEnabled = true;
    }

    //Comparing Button resource name, and item from list
    String currentToMatch;
    private HashMap<String, String> resourceValueMap;
    private void initializeResourceValueMap() {
        resourceValueMap = new HashMap<>();
        resourceValueMap.put("match_script_a", "a");
        resourceValueMap.put("match_script_ba", "ba");
        resourceValueMap.put("match_script_ka", "ka");
        resourceValueMap.put("match_script_da", "da");
        resourceValueMap.put("match_script_e", "e");
        resourceValueMap.put("match_script_ga", "ga");
        resourceValueMap.put("match_script_ha", "ha");
        resourceValueMap.put("match_script_i", "i");
        resourceValueMap.put("match_script_la", "la");
        resourceValueMap.put("match_script_ma", "ma");
        resourceValueMap.put("match_script_na", "na");
        resourceValueMap.put("match_script_nga", "nga");
        resourceValueMap.put("match_script_o", "o");
        resourceValueMap.put("match_script_pa", "pa");
        resourceValueMap.put("match_script_ra", "ra");
        resourceValueMap.put("match_script_sa", "sa");
        resourceValueMap.put("match_script_ta", "ta");
        resourceValueMap.put("match_script_u", "u");
        resourceValueMap.put("match_script_wa", "wa");
        resourceValueMap.put("match_script_ya", "ya");
    }


    private int MatchScore = 0;
    @SuppressLint("SetTextI18n")
    private void checkMatch(String selectedValue, final ImageButton clickedButton) {
        TvResultIndicator = findViewById(R.id.tv_result_indicator);
        TvResultIndicator.setVisibility(View.VISIBLE);
        String expectedValue = resourceValueMap.get(selectedValue);

        if (expectedValue != null && expectedValue.equalsIgnoreCase(currentToMatch)) {
            CorrectSound();
            TvResultIndicator.setText("Correct");
            TvResultIndicator.setTextColor(ContextCompat.getColor(this, R.color.correct_color));
            clickedButton.setImageResource(R.drawable.match_script_clicked);
            clickedButton.setEnabled(false);
            wrongClickCount = 0;
            MatchScore ++;

            if(MatchQuestionCounter == 20){
                disableAllButtons();
                new Handler().postDelayed(() -> {
                    stopTimer();
                    ScoreboardMethod();
                }, 500);
            }

            new Handler().postDelayed(() -> {
                TvResultIndicator.setVisibility(View.INVISIBLE);

                if (currentIndex < MatchList.size() - 1) {
                    currentIndex++;
                    updateTextViews();

                    MatchQuestionCounter ++;
                    MatchQuestionCounter();
                }
            }, 1000);

        }else{
            if (wrongClickCount == 0){
                WrongSound();
                TvResultIndicator.setText("Incorrect");
                TvResultIndicator.setTextColor(ContextCompat.getColor(this, R.color.incorrect_color));
                clickedButton.setEnabled(true);
                wrongClickCount++;
            }else if (wrongClickCount == 1){
                WrongSound();
                TvResultIndicator.setText("Last Guess");
                TvResultIndicator.setTextColor(ContextCompat.getColor(this, R.color.incorrect_color));
                clickedButton.setEnabled(true);
                wrongClickCount++;
            }else if (wrongClickCount == 2){
                WrongSound();
                TvResultIndicator.setText("Skipped");
                TvResultIndicator.setTextColor(ContextCompat.getColor(this, R.color.incorrect_skipped));
                wrongClickCount = 0;
                clickedButton.setEnabled(true);
                new Handler().postDelayed(() -> {
                    TvResultIndicator.setVisibility(View.INVISIBLE);
                    if (currentIndex < MatchList.size() - 1) {
                        currentIndex++;
                        updateTextViews();

                        SkipSoundEffect();

                        MatchQuestionCounter ++;
                        MatchQuestionCounter();
                    }

                    if(MatchQuestionCounter == 20){
                        disableAllButtons();
                        new Handler().postDelayed(() -> {
                            stopTimer();
                            ScoreboardMethod();
                        }, 500);
                    }
                }, 1000);
            }
        }
    }

    private void updateTextViews() {
        if (currentIndex > 0) {
            Tv_MatchPrevious.setText(MatchList.get(currentIndex - 1));
        } else {
            Tv_MatchPrevious.setText("");
        }

        String currentText = MatchList.get(currentIndex).toUpperCase();
        Tv_MatchCurrent.setText(currentText);

        currentToMatch = currentText;

        if (currentIndex < MatchList.size() - 1) {
            Tv_MatchNext.setText(MatchList.get(currentIndex + 1));
        } else {
            Tv_MatchNext.setText("");
        }
    }

    private void MatchQuestionCounter(){
        TvMatchCounter = findViewById(R.id.tv_MatchQuestionCounter);
        TvMatchCounter.setText(String.valueOf(MatchQuestionCounter) + "/20");
    }


    //Timer
    private TextView tv_MatchTimer;
    private boolean isTimerRunning;
    private int secondsPassed;
    private Runnable timerRunnable;
    //private int remainingSeconds;

    public String TimeTaken;  //Use this to result time
    private SharedPreferences preferencesTime;
    void InitializeTimer(){
        tv_MatchTimer = findViewById(R.id.tv_MatchTimer);
        preferencesTime = PreferenceManager.getDefaultSharedPreferences(this);

        //secondsPassed variable based on the stored totalSeconds
        secondsPassed = preferencesTime.getInt("secondsPassed", 0);

        //timer text
        updateTimerText(secondsPassed);

        handler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // Increment secondsPassed variable by 1 every second
                secondsPassed++;

                // Update timer text view with the new time
                updateTimerText(secondsPassed);

                //Start countdown sound effects
                if(difficulty == 1){
                    if (secondsPassed == 155) {
                        Imgbtn_MatchingPause.setEnabled(false);
                        MatchCountdown();
                    }
                } else if (difficulty == 2) {
                    if (secondsPassed == 55) {
                        Imgbtn_MatchingPause.setEnabled(false);
                        MatchCountdown();
                    }
                }


                // Stop timer when it reaches..
                if(difficulty == 1){
                    if (secondsPassed == 120) {
                        stopTimer();
                        disableAllButtons();
                        new Handler().postDelayed(() -> ScoreboardMethod(), 1000);
                    } else {
                        // Schedule the Runnable to run again after 1 second (1000 milliseconds)
                        handler.postDelayed(this, 1000);
                    }
                } else if (difficulty == 2) {
                    if (secondsPassed == 60) {
                        stopTimer();
                        disableAllButtons();
                        new Handler().postDelayed(() -> ScoreboardMethod(), 1000);
                    } else {
                        // Schedule the Runnable to run again after 1 second (1000 milliseconds)
                        handler.postDelayed(this, 1000);
                    }
                }


            }
        };
        startTimer();
    }

    private void startTimer() {
        // Check if the timer is already running
        if (isTimerRunning) {
            return;
        }

        // Start the timer by posting the Runnable immediately
        handler.post(timerRunnable);

        // Set the timer status to running
        isTimerRunning = true;
    }

    private void updateTimerText(int seconds) {
        int totalSeconds = 0;
        if (difficulty == 1){
            totalSeconds = 120;
        } else if (difficulty == 2) {
            totalSeconds = 60;
        }

        // Update the remaining seconds and minutes
        int remainingSeconds = totalSeconds - seconds;
        int remainingMinutes = remainingSeconds / 60;
        remainingSeconds = remainingSeconds % 60;

        // set time as "mm:ss" then set it to the TextView
        @SuppressLint("DefaultLocale") String timerText = String.format("%02d:%02d", remainingMinutes, remainingSeconds);
        tv_MatchTimer.setText(timerText);
        TimeTaken = timerText;

        // if the timer has reached 00:00
        if (remainingSeconds == 0 && remainingMinutes == 0) {
            // Store the totalSeconds value in SharedPreferences
            SharedPreferences.Editor editor = preferencesTime.edit();
            editor.putInt("totalSeconds", totalSeconds);
            editor.apply();
        }
    }

    private void stopTimer() {
        // Remove any pending Runnable callbacks to stop the timer
        handler.removeCallbacks(timerRunnable);

        // Set the timer status to not running
        isTimerRunning = false;
    }

    private void pauseTimer() {
        // Check if the timer is running before pausing it
        if (isTimerRunning) {
            // Remove any pending Runnable callbacks to pause the timer
            handler.removeCallbacks(timerRunnable);

            // Set the timer status to not running
            isTimerRunning = false;
        }
    }

    private void resumeTimer() {
        // Check if the timer is paused before resuming it
        if (!isTimerRunning) {
            // Start the timer by posting the Runnable with the remaining seconds
            handler.postDelayed(timerRunnable, 1000); // Delay 1 second before resuming

            // Set the timer status to running
            isTimerRunning = true;
        }
    }

    public String StarCollected;
    void ScoreboardMethod() {
        Dialog dlg = new Dialog(NewUI_Modes_Matching.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_result_board);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        // Prevents back press on dialog menu
        dlg.setOnKeyListener((dialog, keyCode, event) -> {
            return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP; // Consume the back button press event
        });

        //GIF confetti
        ImageView imgview_confettiGIF = null;
        if (!isFinishing()) {
            imgview_confettiGIF = dlg.findViewById(R.id.imgview_confetti);
            Glide.with(this).load(R.drawable.confetti).into(imgview_confettiGIF);
        }

        TextView TvResultScore = dlg.findViewById(R.id.tvResult_Score);
        TvResultScore.setText(String.valueOf(MatchScore));

        TextView TvResultTimeTaken = dlg.findViewById(R.id.tvResult_TimeTaken);
        TvResultTimeTaken.setText(TimeTaken);

        ImageView ImgResultScoreBoard = dlg.findViewById(R.id.imgviewScoreBoard);
        if (MatchScore <= 0) {
            assert imgview_confettiGIF != null;
            imgview_confettiGIF.setVisibility(View.INVISIBLE);
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_0star);
            StarCollected = "0";
            MatchResultVoice0();
        } else if (MatchScore <= 10) {
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_1star);
            StarCollected = "1";
            MatchResultVoice1();
        } else if (MatchScore <= 15) {
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_2stars);
            StarCollected = "2";
            QuizResultVoice2();
        } else if (MatchScore <= 20) {
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_3stars);
            StarCollected = "3";
            MatchResultVoice3();
        } else {
            showToast();
        }

        saveGameHistory();

        ImgbtnResultAgain = dlg.findViewById(R.id.imgbtnResult_Again);
        ImgbtnResultAgain.setOnClickListener(v -> {
            stopTimer();
            ClickSoundEffect();
            animateButton(ImgbtnResultAgain);
            new Handler().postDelayed(() -> {
                dlg.dismiss();
                recreate();
            }, 500);
        });

        ImgbtnResultQuit = dlg.findViewById(R.id.imgbtnResult_Quit);
        ImgbtnResultQuit.setOnClickListener(v -> {
            stopTimer();
            ClickSoundEffect();
            animateButton(ImgbtnResultQuit);
            new Handler().postDelayed(() -> {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.StopGamesBackgroundMusic();
                dlg.dismiss();
                finish();
            }, 500);
        });
    }

    public void saveGameHistory() {
        String gamemode = "";
        if(difficulty == 1){
            gamemode = "CLS";
        } else if (difficulty == 2) {
            gamemode = "ADV";
        }

        LocalDate currentDate = null;
        DateTimeFormatter formatter = null;
        String formattedDate = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
            formattedDate = currentDate.format(formatter);
        }

        if (NewUI_Gameplay_History.gameplaysList == null) {
            NewUI_Gameplay_History.gameplaysList = new ArrayList<>();
            NewUI_Gameplay_History.loadGameplayList(sharedPreferences);
        }

        NewUI_Gameplay_History.gameplaysList.add(0, new Gameplay("MATCH  :  " + String.format("%02d", MatchScore) + "  :  " + gamemode + "  :  " + TimeTaken + "  :  " + formattedDate));

        if (NewUI_Gameplay_History.gameplaysList.size() > 100) {
            NewUI_Gameplay_History.gameplaysList.remove(NewUI_Gameplay_History.gameplaysList.size() - 1);
        }

        // Save the updated list
        saveGameplayList(sharedPreferences);

        // Adding a score to the Matchscorelist
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(NewUI_Modes_Matching.this);
        scoreManager.addItemToMatchScoreList(MatchScore);
    }

    private void saveGameplayList(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        Set<String> gameplaySet = new HashSet<>();

        for (Gameplay gameplay : NewUI_Gameplay_History.gameplaysList) {
            gameplaySet.add(gameplay.getGameplay());
        }
        editor.putStringSet("userList", gameplaySet);
        editor.apply();
    }


    // Prevents double/multiple clicking at pause menu buttons
    public void disablePauseMenuButtons(){
        ImgbtnResume.setEnabled(false);
        ImgbtnRetry.setEnabled(false);
        ImgbtnQuit.setEnabled(false);
        //ImgbtnHome.setEnabled(false);
    }

    void QuizPauseVoice() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.QuizVoicePause(this);
        }
    }

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    void SkipSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.buttonSkipSound(this);
        }
    }

    void MatchCountdown() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.MatchCountdown(this);
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

    void MatchResultVoice0() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice0(NewUI_Modes_Matching.this);
            }
        }, 200);
    }
    void MatchResultVoice1() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice1(NewUI_Modes_Matching.this);
            }
        }, 200);
    }

    void QuizResultVoice2() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice2(NewUI_Modes_Matching.this);
            }
        }, 200);
    }

    void MatchResultVoice3() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice3(NewUI_Modes_Matching.this);
            }
        }, 200);
    }

    private void showToast() {
        cancelToast();
        globalToast = Toasty.error(this, "Score not in range", Toast.LENGTH_LONG);
        globalToast.show();
    }

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

    private void exitMatchingActivity(){
//        super.onBackPressed();
//        Intent Matching = new Intent(getApplicationContext(), Modes.class);
//        startActivity(Matching);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        Matching.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseTimer();

        Z_SoundManager soundManager = new Z_SoundManager();
        soundManager.StopGamesBackgroundMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeTimer();

        Z_SoundManager soundManager = new Z_SoundManager();
        soundManager.GamesBackgroundMusic(getApplicationContext());
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
        //If timer only has 5 seconds left, disable pause
        if (secondsPassed <= 113) {
            Imgbtn_MatchingPause.performClick();
        }
    }
}
