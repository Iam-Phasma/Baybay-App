package com.example.baybay;

import static com.example.baybay.Gameplay_History.loadGameplayList;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class Modes_Spell extends AppCompatActivity {
    private Toast globalToast;
    Dialog dlg;
    public int difficulty;
    SharedPreferences sharedPreferences;
    private Handler handler;
    ImageButton ImgbtnResume, ImgbtnRetry, ImgbtnQuit, ImgbtnHome;
    ImageButton ImgbtnResultAgain, ImgbtnResultQuit;
    ImageButton SpellPauseBtn;
    int questionItems = 10;  //number of items
    int QuizQuestionCounter = questionItems;

    private TextView randomNumberTextView;
    private TextView remainingAttemptsTextView;
    private ImageButton[] imageButtons;
    private List<List<Integer>> imageLists;

    private int currentListIndex = 0;
    private int remainingAttempts = 5;
    private List<List<Integer>> correctOrders;
    public int correctAnswer = 0;
    int initalItems;
    TextView TvSpellQuestion;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_modes_spell);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the background to a drawable resource
        getWindow().setBackgroundDrawableResource(R.drawable.bg_spell);

        InitializeTimer();

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("DIFFICULTY", 1);

        //sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loadGameplayList(sharedPreferences);

        handler = new Handler();

        SpellPauseBtn = findViewById(R.id.spell_pause_btn);
        SpellPauseBtn.setOnClickListener(view -> {
            spellPauseMethod();
        });

        // Initialize your components by finding them using their IDs
        randomNumberTextView = findViewById(R.id.tv_spell_randomnumber);
        remainingAttemptsTextView = findViewById(R.id.tv_spell_remaining);
        imageButtons = new ImageButton[5];

        imageButtons[0] = findViewById(R.id.spell_choice1);
        imageButtons[1] = findViewById(R.id.spell_choice2);
        imageButtons[2] = findViewById(R.id.spell_choice3);
        imageButtons[3] = findViewById(R.id.spell_choice4);
        imageButtons[4] = findViewById(R.id.spell_choice5);

        // Create your lists of images
        imageLists = new ArrayList<>();

        List<Integer> imageList1 = new ArrayList<>();
        imageList1.add(R.drawable.sp_q1_pa); // Replace with your image resource IDs
        imageList1.add(R.drawable.sp_q1_sa);
        imageList1.add(R.drawable.sp_q1_la);
        imageList1.add(R.drawable.sp_q1_ma);
        imageList1.add(R.drawable.sp_q1_tan);
        imageLists.add(imageList1);

        List<Integer> imageList2 = new ArrayList<>();
        imageList2.add(R.drawable.sp_q2_nag);
        imageList2.add(R.drawable.sp_q2_ka);
        imageList2.add(R.drawable.sp_q2_tu);
        imageList2.add(R.drawable.sp_q2_wa);
        imageList2.add(R.drawable.sp_q2_an);
        imageLists.add(imageList2);

        List<Integer> imageList3 = new ArrayList<>();
        imageList3.add(R.drawable.sp_q3_ki);
        imageList3.add(R.drawable.sp_q3_na);
        imageList3.add(R.drawable.sp_q3_gi);
        imageList3.add(R.drawable.sp_q3_li);
        imageList3.add(R.drawable.sp_q3_wan);
        imageLists.add(imageList3);

        List<Integer> imageList4 = new ArrayList<>();
        imageList4.add(R.drawable.sp_q4_hi);
        imageList4.add(R.drawable.sp_q4_na);
        imageList4.add(R.drawable.sp_q4_ha);
        imageList4.add(R.drawable.sp_q4_nga);
        imageList4.add(R.drawable.sp_q4_an);
        imageLists.add(imageList4);

        List<Integer> imageList5 = new ArrayList<>();
        imageList5.add(R.drawable.sp_q5_ka);
        imageList5.add(R.drawable.sp_q5_li);
        imageList5.add(R.drawable.sp_q5_ga);
        imageList5.add(R.drawable.sp_q5_ya);
        imageList5.add(R.drawable.sp_q5_han);
        imageLists.add(imageList5);

        List<Integer> imageList6 = new ArrayList<>();
        imageList6.add(R.drawable.sp_q6_pi);
        imageList6.add(R.drawable.sp_q6_na);
        imageList6.add(R.drawable.sp_q6_ra);
        imageList6.add(R.drawable.sp_q6_nga);
        imageList6.add(R.drawable.sp_q6_lan);
        imageLists.add(imageList6);

        List<Integer> imageList7 = new ArrayList<>();
        imageList7.add(R.drawable.sp_q7_ki);
        imageList7.add(R.drawable.sp_q7_na);
        imageList7.add(R.drawable.sp_q7_ga);
        imageList7.add(R.drawable.sp_q7_la);
        imageList7.add(R.drawable.sp_q7_kan);
        imageLists.add(imageList7);

        List<Integer> imageList8 = new ArrayList<>();
        imageList8.add(R.drawable.sp_q8_ka);
        imageList8.add(R.drawable.sp_q8_sa);
        imageList8.add(R.drawable.sp_q8_ga);
        imageList8.add(R.drawable.sp_q8_na);
        imageList8.add(R.drawable.sp_q8_han);
        imageLists.add(imageList8);

        List<Integer> imageList9 = new ArrayList<>();
        imageList9.add(R.drawable.sp_q9_na);
        imageList9.add(R.drawable.sp_q9_ka);
        imageList9.add(R.drawable.sp_q9_a);
        imageList9.add(R.drawable.sp_q9_an);
        imageList9.add(R.drawable.sp_q9_tig);
        imageLists.add(imageList9);

        List<Integer> imageList10 = new ArrayList<>();
        imageList10.add(R.drawable.sp_q10_na);
        imageList10.add(R.drawable.sp_q10_pa);
        imageList10.add(R.drawable.sp_q10_ka);
        imageList10.add(R.drawable.sp_q10_bu);
        imageList10.add(R.drawable.sp_q10_ti);
        imageLists.add(imageList10);

        // Initialize correctOrders
        correctOrders = new ArrayList<>();
        correctOrders.add(Arrays.asList(R.drawable.sp_q1_pa, R.drawable.sp_q1_sa, R.drawable.sp_q1_la, R.drawable.sp_q1_ma, R.drawable.sp_q1_tan));
        correctOrders.add(Arrays.asList(R.drawable.sp_q2_nag, R.drawable.sp_q2_ka, R.drawable.sp_q2_tu, R.drawable.sp_q2_wa, R.drawable.sp_q2_an));
        correctOrders.add(Arrays.asList(R.drawable.sp_q3_ki, R.drawable.sp_q3_na, R.drawable.sp_q3_gi, R.drawable.sp_q3_li, R.drawable.sp_q3_wan));
        correctOrders.add(Arrays.asList(R.drawable.sp_q4_hi, R.drawable.sp_q4_na, R.drawable.sp_q4_ha, R.drawable.sp_q4_nga, R.drawable.sp_q4_an));
        correctOrders.add(Arrays.asList(R.drawable.sp_q5_ka, R.drawable.sp_q5_li, R.drawable.sp_q5_ga, R.drawable.sp_q5_ya, R.drawable.sp_q5_han));

        correctOrders.add(Arrays.asList(R.drawable.sp_q6_pi, R.drawable.sp_q6_na, R.drawable.sp_q6_ra, R.drawable.sp_q6_nga, R.drawable.sp_q6_lan));
        correctOrders.add(Arrays.asList(R.drawable.sp_q7_ki, R.drawable.sp_q7_na, R.drawable.sp_q7_ga, R.drawable.sp_q7_la, R.drawable.sp_q7_kan));
        correctOrders.add(Arrays.asList(R.drawable.sp_q8_ka, R.drawable.sp_q8_sa, R.drawable.sp_q8_ga, R.drawable.sp_q8_na, R.drawable.sp_q8_han));
        correctOrders.add(Arrays.asList(R.drawable.sp_q9_na, R.drawable.sp_q9_ka, R.drawable.sp_q9_a, R.drawable.sp_q9_an, R.drawable.sp_q9_tig));
        correctOrders.add(Arrays.asList(R.drawable.sp_q10_na, R.drawable.sp_q10_pa, R.drawable.sp_q10_ka, R.drawable.sp_q10_bu, R.drawable.sp_q10_ti));

        // Set up initial values
        remainingAttemptsTextView.setText("ATTEMPTS REMAINING: " + remainingAttempts);
        showNextRandomList();

        // Set click listeners for the ImageButtons
        for (ImageButton imageButton : imageButtons) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if(questionItems != -1){
                        onImageButtonClick(view);
                    //}else{
//                        Toasty.info(Modes_Spell.this, "question is equal to 2 ONE", Toast.LENGTH_LONG).show();
//                        endGameSpell();
//                        disableChoices();
//                    }
                }
            });
        }



        initalItems = 10;
        randomNumberTextView.setText(String.valueOf(initalItems));

    }

    private int expectedButtonIndex = 0;

    // Add this method to reset the button visibility and enable all buttons
    private void resetButtons() {
        for (ImageButton imageButton : imageButtons) {
            imageButton.setVisibility(View.VISIBLE);
            imageButton.setEnabled(true);
        }
    }


    int questionLimit;
    private void showNextRandomList() {
        if(difficulty == 1){
            questionLimit = 0;
        }else if (difficulty == 2){
            questionLimit = 0; //Do not change this
        }
        // Remove the strike-through style
        TvSpellQuestion = findViewById(R.id.tv_spell_question);
        TvSpellQuestion.setPaintFlags(TvSpellQuestion.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        int tvSpellQuestionDefaultColor = Color.parseColor("#502F8A");
        TvSpellQuestion.setTextColor(tvSpellQuestionDefaultColor);

        if (questionItems != questionLimit){
            if (currentListIndex <= imageLists.size()) {
                remainingAttempts = 5;
                remainingAttemptsTextView.setText("Remaining Attempts: " + remainingAttempts);
                setWordQuestion();

                int randomNumber = new Random().nextInt(imageLists.size());
                //randomNumberTextView.setText(String.valueOf(randomNumber + 1));

                List<Integer> currentList = new ArrayList<>(imageLists.get(currentListIndex));
                Collections.shuffle(currentList);

                resetButtons();

                // Set the correct order based on the random number
                List<Integer> correctOrder = correctOrders.get(randomNumber); // Assign the correctOrder variable

                for (int i = 0; i < imageButtons.length; i++) {
                    imageButtons[i].setImageResource(currentList.get(i));
                    imageButtons[i].setTag(currentList.get(i)); // Set the tag with the image resource ID
                }
                expectedButtonIndex = 0;
            } else {
                //Toasty.info(Modes_Spell.this, "current index is less than or equal to imagelist size", Toast.LENGTH_LONG).show();
                //Toasty.info(Modes_Spell.this, "END 1", Toast.LENGTH_LONG).show();
                endGameSpell();
            }

            initalItems --;
            questionItems --;
            randomNumberTextView.setText(String.valueOf(initalItems));

        }else{
            //Toasty.info(Modes_Spell.this, "question is equal to -1 TWO", Toast.LENGTH_LONG).show();
            stopTimer();
            //Toasty.info(Modes_Spell.this, "END 2", Toast.LENGTH_LONG).show();
            endGameSpell();
        }

    }

    //logic in scoring
    //if chooses difficulty 2, 2 points each max
    //if no mistake, give 2.
    //If chooses difficulty 1. If not perfect but has correct answer, give 1. If perfect, give 2.
    //Both, If attempt is 0, give nothing.


    public void onImageButtonClick(View view) {
        ImageButton clickedButton = (ImageButton) view;
        int clickedImageResource = (int) clickedButton.getTag();

        // Correct button pressed
        if (currentListIndex <= correctOrders.size()) {
            if (clickedImageResource == correctOrders.get(currentListIndex).get(expectedButtonIndex)) {
                // Check if the clicked button is correct
                fadeOutAndShrinkAnimation((ImageButton) view);
                CorrectSound();

                if (difficulty == 1){
                    //correctAnswer += 1; // (1)
                    //Toasty.info(Modes_Spell.this, "Score: " + correctAnswer, Toast.LENGTH_SHORT).show();
                }
                expectedButtonIndex++;
            } else {
                // Incorrect button pressed

                // Apply a strike-through style in difficulty 2
                if (difficulty == 2){
                    TvSpellQuestion.setPaintFlags(TvSpellQuestion.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    TvSpellQuestion.setTextColor(Color.DKGRAY);
                }

                //Toasty.error(Modes_Spell.this, "OOPS!", Toast.LENGTH_SHORT).show();
                animateButton((ImageButton) view);
                WrongSound();
                remainingAttempts--;
                remainingAttemptsTextView.setText("Remaining Attempts: " + remainingAttempts);

                if (remainingAttempts == 0) {
                    disableChoices();
                    new Handler().postDelayed(() -> {
                        currentListIndex++;
                        expectedButtonIndex = 0; // Reset the expectedButtonIndex
                        remainingAttemptsTextView.setText("Remaining Attempts: " + remainingAttempts);

                        correctAnswer += 0; // -> IF RUN OUT OF ATTEMPT (1 & 2)
                        //Toasty.info(Modes_Spell.this, "RUN OUT OF ATTEMPT", Toast.LENGTH_LONG).show();

                        showNextRandomList();
                    }, 1000);
                    return;
                }
            }
        } else {
            //Toasty.info(Modes_Spell.this, "END 3", Toast.LENGTH_LONG).show();
            endGameSpell();
        }

        // For when there are still remaining attempts but the choices are all already picked.
        if (currentListIndex < correctOrders.size() && expectedButtonIndex == correctOrders.get(currentListIndex).size()) {
            disableChoices();
            new Handler().postDelayed(() -> {
                currentListIndex++;
                expectedButtonIndex = 0; // Reset the expectedButtonIndex
                remainingAttemptsTextView.setText("Remaining Attempts: " + remainingAttempts);
                //Toasty.info(Modes_Spell.this, "Index: " + (currentListIndex) + "CO: " + (correctOrders.size()), Toast.LENGTH_SHORT).show();
                if (currentListIndex <= correctOrders.size()) {
                    if (difficulty == 2) {
                        if (remainingAttempts == 5) {
                            correctAnswer += 2; // -> IF PERFECT
                            cancelToast();
                            globalToast = Toasty.success(Modes_Spell.this, "PERFECT!", Toast.LENGTH_SHORT);
                            globalToast.show();
                        }
                    } else if (difficulty == 1) {
                        if (remainingAttempts != 5 && remainingAttempts != 0){
                            correctAnswer += 1; // -> IF HAS AT LEAST 1 CORRECT
                        }else if (remainingAttempts == 5){
                            correctAnswer += 2; // -> IF PERFECT
                            cancelToast();
                            globalToast = Toasty.success(Modes_Spell.this, "PERFECT!", Toast.LENGTH_SHORT);
                            globalToast.show();
                        }
                    }
                    //Toasty.info(Modes_Spell.this, "Score: " + (correctAnswer), Toast.LENGTH_SHORT).show();
                    showNextRandomList();
                } else {
                    //Toasty.info(Modes_Spell.this, "currentlist index is less than correctOrders", Toast.LENGTH_LONG).show();
                    //Toasty.info(Modes_Spell.this, "END 4", Toast.LENGTH_LONG).show();
                    disableChoices();
                    endGameSpell();
                }
            }, 1000);
        }
    }

    public void setWordQuestion(){
        TvSpellQuestion = findViewById(R.id.tv_spell_question);

        if (currentListIndex == 0){
            TvSpellQuestion.setText("PASALAMATAN");
        } else if (currentListIndex == 1) {
            TvSpellQuestion.setText("NAGKATUWAAN");
        }else if (currentListIndex == 2) {
            TvSpellQuestion.setText("KINAGILIWAN");
        }else if (currentListIndex == 3) {
            TvSpellQuestion.setText("HINAHANGAAN");
        }else if (currentListIndex == 4) {
            TvSpellQuestion.setText("KALIGAYAHAN");
        }else if (currentListIndex == 5) {
            TvSpellQuestion.setText("PINARANGALAN");
        }else if (currentListIndex == 6) {
            TvSpellQuestion.setText("KINAGALAKAN");
        }else if (currentListIndex == 7) {
            TvSpellQuestion.setText("KASAGANAHAN");
        }else if (currentListIndex == 8) {
            TvSpellQuestion.setText("NAKAAANTIG");
        }else if (currentListIndex == 9) {
            TvSpellQuestion.setText("NAPAKABUTI");
        }
    }


    public void endGameSpell(){
        disableChoices();
        //Toasty.info(Modes_Spell.this, "Done", Toast.LENGTH_LONG).show();
        ScoreboardMethod();
    }

    public void disableChoices(){
        imageButtons[0].setEnabled(false);
        imageButtons[1].setEnabled(false);
        imageButtons[2].setEnabled(false);
        imageButtons[3].setEnabled(false);
        imageButtons[4].setEnabled(false);
        new Handler().postDelayed(() -> {
            imageButtons[0].setEnabled(true);
            imageButtons[1].setEnabled(true);
            imageButtons[2].setEnabled(true);
            imageButtons[3].setEnabled(true);
            imageButtons[4].setEnabled(true);
        }, 1000);
    }

    public String StarCollected;
    void ScoreboardMethod(){
        Dialog dlg = new Dialog(Modes_Spell.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_result_board);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyBounceAnimation(dialogWindowView);

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
        TvResultScore.setText(String.valueOf(correctAnswer));

        TextView TvResultTimeTaken = dlg.findViewById(R.id.tvResult_TimeTaken);
        TvResultTimeTaken.setText(TimeTaken);

        ImageView ImgResultScoreBoard = dlg.findViewById(R.id.imgviewScoreBoard);
        if (correctAnswer <= 0) {
            assert imgview_confettiGIF != null;
            imgview_confettiGIF.setVisibility(View.INVISIBLE);
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_0star);
            QuizResultVoice0();
            StarCollected = "0";
        } else if (correctAnswer <= 10) {
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_1star);
            StarCollected = "1";
            QuizResultVoice1();

            // Earn +3 trophies
            int earnedTrophies = 3;
            int currentTrophies = Z_TrophyManager.getTrophies();
            if (currentTrophies <= 996) {
                cancelToast();
                globalToast = Toasty.info(this, "Earned + " + earnedTrophies + " trophies!", Toast.LENGTH_LONG);
                globalToast.show();
                int newTrophies = currentTrophies + earnedTrophies;
                Z_TrophyManager.setTrophies(newTrophies);
            }
        } else if (correctAnswer <= 15) {
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_2stars);
            StarCollected = "2";
            QuizResultVoice2();

            // Earn +4 trophies
            int earnedTrophies = 4;
            int currentTrophies = Z_TrophyManager.getTrophies();
            if (currentTrophies <= 995) {
                cancelToast();
                globalToast = Toasty.info(this, "Earned + " + earnedTrophies + " trophies!", Toast.LENGTH_LONG);
                globalToast.show();
                int newTrophies = currentTrophies + earnedTrophies;
                Z_TrophyManager.setTrophies(newTrophies);
            }
        } else if (correctAnswer <= 25) {
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_3stars);
            StarCollected = "3";
            QuizResultVoice3();

            // Earn +5 trophies
            int earnedTrophies = 5;
            int currentTrophies = Z_TrophyManager.getTrophies();
            if (currentTrophies <= 994) {
                cancelToast();
                globalToast = Toasty.info(this, "Earned + " + earnedTrophies + " trophies!", Toast.LENGTH_LONG);
                globalToast.show();
                int newTrophies = currentTrophies + earnedTrophies;
                Z_TrophyManager.setTrophies(newTrophies);
            }
        } else {
            cancelToast();
            globalToast = Toasty.error(this, "Score not in range", Toast.LENGTH_LONG);
            globalToast.show();
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
                //Stop BG Music
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.StopGamesBackgroundMusic();

                dlg.dismiss();
                Intent Quiz = new Intent(getApplicationContext(), Modes.class);
                startActivity(Quiz);
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                //Quiz.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
            formatter = DateTimeFormatter.ofPattern("dd-MMM");
            formattedDate = currentDate.format(formatter);
        }

        if (Gameplay_History.gameplaysList == null) {
            Gameplay_History.gameplaysList = new ArrayList<>();
            loadGameplayList(sharedPreferences);
        }

        Gameplay_History.gameplaysList.add(0, new Gameplay("SPELL       " + String.format("%02d", correctAnswer) + "       " + gamemode + "       "+ TimeTaken + "       " + formattedDate));

        if (Gameplay_History.gameplaysList.size() > 50) {
            Gameplay_History.gameplaysList.remove(Gameplay_History.gameplaysList.size() - 1);
        }

        // Save the updated list
        saveGameplayList(sharedPreferences);

        // Adding a score to the Spellscorelist
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(Modes_Spell.this);
        scoreManager.addItemToSpellScoreList(correctAnswer);
    }

    private void saveGameplayList(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        Set<String> gameplaySet = new HashSet<>();

        for (Gameplay gameplay : Gameplay_History.gameplaysList) {
            gameplaySet.add(gameplay.getGameplay());
        }
        editor.putStringSet("userList", gameplaySet);
        editor.apply();
    }



    public void spellPauseMethod(){
        pauseTimer();
        dlg = new Dialog(Modes_Spell.this, R.style.PopupDialog);
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
            disablePauseMenuButtons();
            new Handler().postDelayed(this::QuizResumeVoice, 200);

            if(QuizQuestionCounter != -1){
                resumeTimer();
            }

            animateButton(ImgbtnResume);
            new Handler().postDelayed(() -> dlg.dismiss(), 500);
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
            animateButton(ImgbtnQuit);
            new Handler().postDelayed(() -> {
                //Stop BG Music
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.StopGamesBackgroundMusic();

                dlg.dismiss();
                exitSpellActivity();
            }, 500);
        });

//        ImgbtnHome = dlg.findViewById(R.id.imgbtnHome);
//        ImgbtnHome.setOnClickListener(v14 -> {
//            disablePauseMenuButtons();
//            stopTimer();
//            ClickSoundEffect();
//            animateButton(ImgbtnHome);
//            new Handler().postDelayed(() -> {
//                //Stop BG Music
//                Z_SoundManager soundManager = new Z_SoundManager();
//                soundManager.StopGamesBackgroundMusic();
//
//                dlg.dismiss();
//                Intent Quiz = new Intent(getApplicationContext(), MainMenu.class);
//                startActivity(Quiz);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                //Quiz.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                //Quiz.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
//            }, 500);
//        });
    }

    // Important
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

    void QuizResumeVoice() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.QuizVoiceResume(this);
        }
    }

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    void QuizResultVoice0() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice0(Modes_Spell.this);
            }
        }, 200);
    }

    void QuizResultVoice1() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice1(Modes_Spell.this);
            }
        }, 200);
    }

    void QuizResultVoice2() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice2(Modes_Spell.this);
            }
        }, 200);
    }

    void QuizResultVoice3() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice3(Modes_Spell.this);
            }
        }, 200);
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

    //Timer
    private TextView tv_spellTimer;
    private boolean isTimerRunning;
    private int secondsPassed;
    private Runnable timerRunnable;
    private int remainingSeconds;
    public String TimeTaken;


    void InitializeTimer(){
        tv_spellTimer = findViewById(R.id.tv_spell_time);

        // Initialize the timer to 00:00
        updateTimerText(0);

        // Create a new Handler and Runnable to update the timer every second
        handler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // Increment the secondsPassed variable by 1 every second
                secondsPassed++;

                // Update the timer text view with the new time
                updateTimerText(secondsPassed);

                // Schedule the Runnable to run again after 1 second (1000 milliseconds)
                handler.postDelayed(this, 1000);
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
        // Calculate the minutes and seconds from the total seconds
        int minutes = seconds / 60;
        remainingSeconds = seconds % 60; // Update remainingSeconds variable

        // Format the time as "mm:ss" and set it to the TextView
        @SuppressLint("DefaultLocale") String timerText = String.format("%02d:%02d", minutes, remainingSeconds);
        tv_spellTimer.setText(timerText);
        TimeTaken = timerText;
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
            secondsPassed = remainingSeconds;
            handler.post(timerRunnable);

            // Set the timer status to running
            isTimerRunning = true;
        }
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

    private void fadeOutAndShrinkAnimation(final ImageButton button) {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(400);
        anim.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(anim);

        ScaleAnimation scaleAnim = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(1000);
        animationSet.addAnimation(scaleAnim);

        button.startAnimation(animationSet);
        button.setVisibility(View.INVISIBLE);

        // After the animation, you can perform other actions if needed
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended, you can perform any post-animation actions here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated (if set)
            }
        });
    }


    private void exitSpellActivity(){
//        Intent Spell = new Intent(getApplicationContext(), Modes.class);
//        startActivity(Spell);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //Quiz.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //Quiz.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeTimer();
        Z_SoundManager.setActivityModesPaused(true);

        Z_SoundManager soundManager = new Z_SoundManager();
        soundManager.GamesBackgroundMusic(getApplicationContext());
    }


    @Override
    protected void onPause() {
        super.onPause();
        pauseTimer();
        if (dlg != null && dlg.isShowing()) {
            dlg.dismiss();
        }

        Z_SoundManager soundManager = new Z_SoundManager();
        soundManager.StopGamesBackgroundMusic();

//        if (songQuiz != null && songQuiz.isPlaying()) {
//            songQuiz.pause();
//        }
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
        spellPauseMethod();
    }

}