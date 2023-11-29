package com.example.baybay;

import static com.example.baybay.Gameplay_History.loadGameplayList;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class Modes_Quiz extends AppCompatActivity {
    private Toast globalToast;
    private ImageView imgview_questionimage;
    private TextView tvChoice1, tvChoice2, tvChoice3, tvChoice4;

    private List<Z_QuizClassic_QuestionManager.Question> questionList_classic;
    private List<Z_QuizWord_QuestionManager.Question> questionList_word;
    public int difficulty;
    private int currentQuestionIndex;
    private ImageButton imgbtnChoice1, imgbtnChoice2, imgbtnChoice3, imgbtnChoice4, imgbtnQuizPause;

    Dialog dlg;
    private Handler handler;
    ImageButton ImgbtnResume, ImgbtnRetry, ImgbtnQuit, ImgbtnHome;
    ImageButton ImgbtnResultAgain, ImgbtnResultQuit;
    MediaPlayer songQuiz;
    TextView Tv_QuizQuestionCounter;
    int questionItems = 20;  //number of items
    int QuizQuestionCounter = questionItems;
    SharedPreferences sharedPreferences;

//    private static class Question {
//        private int questionImage;
//        private List<String> choices;
//        private int answerIndex;
//
//        public Question(int questionImage, List<String> choices, int answerIndex) {
//            this.questionImage = questionImage;
//            this.choices = choices;
//            this.answerIndex = answerIndex;
//        }
//
//        public int getQuestionImage() {
//            return questionImage;
//        }
//
//        public List<String> getChoices() {
//            return choices;
//        }
//
//        public int getAnswerIndex() {
//            return answerIndex;
//        }
//    }

    //private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_quiz);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the background to a drawable resource
        getWindow().setBackgroundDrawableResource(R.drawable.bg_quiz3);

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("DIFFICULTY", 1);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        InitializeTimer();

        imgview_questionimage = findViewById(R.id.imgview_questionimage);
        tvChoice1 = findViewById(R.id.tvChoice1);
        tvChoice2 = findViewById(R.id.tvChoice2);
        tvChoice3 = findViewById(R.id.tvChoice3);
        tvChoice4 = findViewById(R.id.tvChoice4);

        // Initialize the question manager
        Z_QuizClassic_QuestionManager questionManager_q = new Z_QuizClassic_QuestionManager();
        questionList_classic = questionManager_q.getQuiz_questionList();
        Collections.shuffle(questionList_classic);

        Z_QuizWord_QuestionManager questionManager_m = new Z_QuizWord_QuestionManager();
        questionList_word = questionManager_m.getQuizword_questionList();
        Collections.shuffle(questionList_word);


        // Display the first question
        currentQuestionIndex = 0;
        displayQuestion();

        imgbtnChoice1 = findViewById(R.id.imgbtnChoice1);
        imgbtnChoice2 = findViewById(R.id.imgbtnChoice2);
        imgbtnChoice3 = findViewById(R.id.imgbtnChoice3);
        imgbtnChoice4 = findViewById(R.id.imgbtnChoice4);

        imgbtnChoice1.setOnClickListener(v -> {
            animateButton(imgbtnChoice1);
            animateButton(tvChoice1);
            checkAnswer(v);
        });

        imgbtnChoice2.setOnClickListener(v -> {
            animateButton(imgbtnChoice2);
            animateButton(tvChoice2);
            checkAnswer(v);
        });

        imgbtnChoice3.setOnClickListener(v -> {
            animateButton(imgbtnChoice3);
            animateButton(tvChoice3);
            checkAnswer(v);
        });

        imgbtnChoice4.setOnClickListener(v -> {
            animateButton(imgbtnChoice4);
            animateButton(tvChoice4);
            checkAnswer(v);
        });


        handler = new Handler();

        imgbtnQuizPause = findViewById(R.id.imgbtn_quizpause);
        imgbtnQuizPause.setOnClickListener(v -> {
            pauseTimer();
            dlg = new Dialog(Modes_Quiz.this, R.style.PopupDialog);
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
                    exitQuizActivity();
                }, 500);
            });

            ImgbtnHome = dlg.findViewById(R.id.imgbtnHome);
            ImgbtnHome.setOnClickListener(v14 -> {
                disablePauseMenuButtons();
                stopTimer();
                ClickSoundEffect();
                animateButton(ImgbtnHome);
                new Handler().postDelayed(() -> {
                    //Stop BG Music
                    Z_SoundManager soundManager = new Z_SoundManager();
                    soundManager.StopGamesBackgroundMusic();

                    dlg.dismiss();
                    Intent Quiz = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(Quiz);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    //Quiz.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    //Quiz.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }, 500);
            });

        });
    }

    // Important
    public void disablePauseMenuButtons(){
        ImgbtnResume.setEnabled(false);
        ImgbtnRetry.setEnabled(false);
        ImgbtnQuit.setEnabled(false);
        ImgbtnHome.setEnabled(false);
    }

//    public void enablePauseMenuButtons(){
//        ImgbtnResume.setEnabled(true);
//        ImgbtnRetry.setEnabled(true);
//        ImgbtnQuit.setEnabled(true);
//        ImgbtnHome.setEnabled(true);
//    }

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

    void choiceDisable(){
        imgbtnChoice1.setEnabled(false);
        imgbtnChoice2.setEnabled(false);
        imgbtnChoice3.setEnabled(false);
        imgbtnChoice4.setEnabled(false);
    }

    void choiceEnable(){
        imgbtnChoice1.setEnabled(true);
        imgbtnChoice2.setEnabled(true);
        imgbtnChoice3.setEnabled(true);
        imgbtnChoice4.setEnabled(true);
    }

    private final List<Integer> usedQuestionIndices = new ArrayList<>();
    private void displayQuestion() {
        //Establish question counter
        Tv_QuizQuestionCounter = findViewById(R.id.tv_quizQuestionCounter);
        Tv_QuizQuestionCounter.setText(String.valueOf(QuizQuestionCounter));
        QuizQuestionCounter --;

        if (questionsAnswered == questionItems) {
            pauseTimer();
            choiceDisable();
            ScoreboardMethod();
            return;
        }else{
            // Shuffle the questionList before selecting the current question
            //Collections.shuffle(questionList);

            // Find a question that hasn't been used yet
            // Z_Quiz_QuestionManager.Question currentQuestion = null;
            // for (Z_Quiz_QuestionManager.Question question : questionList_q) {
            //     if (!usedQuestionIndices.contains(questionList_q.indexOf(question))) {
            //         currentQuestion = question;
            //         break;
            //     }
            // }

            if (difficulty == 1) {
                Z_QuizClassic_QuestionManager.Question currentQuestion = null;
                for (Z_QuizClassic_QuestionManager.Question question : questionList_classic) {
                    if (!usedQuestionIndices.contains(questionList_classic.indexOf(question))) {
                        currentQuestion = question;
                        break;
                    }
                }

                // If no unused question is found, all questions have been used, and the quiz is completed.
                if (currentQuestion == null) {

                    //showToast("Quiz completed! 2");
                    choiceDisable();
                    return;
                }

                // Mark the current question as used
                usedQuestionIndices.add(questionList_classic.indexOf(currentQuestion));

                // Rest of the existing code...
                imgview_questionimage.setImageResource(currentQuestion.getQuestionImage());

                List<String> choices = currentQuestion.getChoices();
                tvChoice1.setText(choices.get(0));
                tvChoice2.setText(choices.get(1));
                tvChoice3.setText(choices.get(2));
                tvChoice4.setText(choices.get(3));

                // Increment the number of questions answered
                questionsAnswered++;

            } else if (difficulty == 2) {
                Z_QuizWord_QuestionManager.Question currentQuestion = null;
                for (Z_QuizWord_QuestionManager.Question question : questionList_word) {
                    if (!usedQuestionIndices.contains(questionList_word.indexOf(question))) {
                        currentQuestion = question;
                        break;
                    }
                }

                // If no unused question is found, all questions have been used, and the quiz is completed.
                if (currentQuestion == null) {

                    //showToast("Quiz completed! 2");
                    choiceDisable();
                    return;
                }

                // Mark the current question as used
                usedQuestionIndices.add(questionList_word.indexOf(currentQuestion));

                // Rest of the existing code...
                imgview_questionimage.setImageResource(currentQuestion.getQuestionImage());

                List<String> choices = currentQuestion.getChoices();
                tvChoice1.setText(choices.get(0));
                tvChoice2.setText(choices.get(1));
                tvChoice3.setText(choices.get(2));
                tvChoice4.setText(choices.get(3));

                // Increment the number of questions answered
                questionsAnswered++;

            }
        }


    }

    private int questionsAnswered = 0;
    int correctAnswer = 0;
    public void checkAnswer(View view) {
        choiceDisable();

        ImageButton selectedButton = (ImageButton) view;
        int selectedChoice = getSelectedChoiceIndex(selectedButton);

        if (difficulty == 1) {
            if (selectedChoice == questionList_classic.get(currentQuestionIndex).getAnswerIndex()) {
                correctAnswer ++;
                CorrectSound();
                selectedButton.setImageResource(R.drawable.quiz_choice_correct);
            } else {
                WrongSound();
                selectedButton.setImageResource(R.drawable.quiz_choice_wrong);
                int correctChoiceIndex = questionList_classic.get(currentQuestionIndex).getAnswerIndex();
                ImageButton correctButton = getChoiceButtonByIndex(correctChoiceIndex);
                correctButton.setImageResource(R.drawable.quiz_choice_correct);
            }
        } else if (difficulty == 2) {
            if (selectedChoice == questionList_word.get(currentQuestionIndex).getAnswerIndex()) {
                correctAnswer ++;
                CorrectSound();
                selectedButton.setImageResource(R.drawable.quiz_choice_correct);
            } else {
                WrongSound();
                selectedButton.setImageResource(R.drawable.quiz_choice_wrong);
                int correctChoiceIndex = questionList_word.get(currentQuestionIndex).getAnswerIndex();
                ImageButton correctButton = getChoiceButtonByIndex(correctChoiceIndex);
                correctButton.setImageResource(R.drawable.quiz_choice_correct);
            }
        }


        // Delay for 1 second before moving to the next question
        handler.postDelayed(() -> {
            // Reset button drawables to default
            resetChoiceButtonDrawables();

            // Move to the next question if not all questions have been answered
            currentQuestionIndex++;

            if (difficulty == 1){
                 if (currentQuestionIndex < questionList_classic.size()) {
                    displayQuestion();
                } else {
                    //showToast("Quiz completed! 3");
                    choiceDisable();
                }
            } else if (difficulty == 2) {
                if (currentQuestionIndex < questionList_word.size()) {
                    displayQuestion();
                } else {
                    //showToast("Quiz completed! 3");
                    choiceDisable();
                }
            }

        }, 1000);
    }



    private void resetChoiceButtonDrawables() {
        choiceEnable();
        imgbtnChoice1.setImageResource(R.drawable.quiz_choice_default);
        imgbtnChoice2.setImageResource(R.drawable.quiz_choice_default);
        imgbtnChoice3.setImageResource(R.drawable.quiz_choice_default);
        imgbtnChoice4.setImageResource(R.drawable.quiz_choice_default);
    }

    private ImageButton getChoiceButtonByIndex(int choiceIndex) {
        ImageButton choiceButton = null;
        switch (choiceIndex) {
            case 0:
                choiceButton = imgbtnChoice1;
                break;
            case 1:
                choiceButton = imgbtnChoice2;
                break;
            case 2:
                choiceButton = imgbtnChoice3;
                break;
            case 3:
                choiceButton = imgbtnChoice4;
                break;
        }
        return choiceButton;
    }

    //this change the img src of what button is clicked
    private int getSelectedChoiceIndex(ImageButton selectedButton) {
        int selectedChoiceIndex = -1;
        int selectedButtonId = selectedButton.getId();

        if (selectedButtonId == R.id.imgbtnChoice1) {
            selectedChoiceIndex = 0;
        } else if (selectedButtonId == R.id.imgbtnChoice2) {
            selectedChoiceIndex = 1;
        } else if (selectedButtonId == R.id.imgbtnChoice3) {
            selectedChoiceIndex = 2;
        } else if (selectedButtonId == R.id.imgbtnChoice4) {
            selectedChoiceIndex = 3;
        }

        return selectedChoiceIndex;
    }

    public String TimeTaken;
    public String StarCollected;
    void ScoreboardMethod(){
        Dialog dlg = new Dialog(Modes_Quiz.this, R.style.PopupDialog);
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
            if(Z_TrophyManager.getTrophies() <= 996){
                cancelToast();
                globalToast = Toasty.info(this, "Earned + 3 trophies!", Toast.LENGTH_LONG);
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
            if(Z_TrophyManager.getTrophies() <= 995){
                cancelToast();
                globalToast = Toasty.info(this, "Earned + 4 trophies!", Toast.LENGTH_LONG);
                globalToast.show();
                int newTrophies = currentTrophies + earnedTrophies;
                Z_TrophyManager.setTrophies(newTrophies);
            }
        } else if (correctAnswer <= 20) {
            ImgResultScoreBoard.setImageResource(R.drawable.quizresult_scoreboard_3stars);
            StarCollected = "3";
            QuizResultVoice3();

            // Earn +5 trophies
            int earnedTrophies = 5;
            int currentTrophies = Z_TrophyManager.getTrophies();
            if(Z_TrophyManager.getTrophies() <= 994){
                cancelToast();
                globalToast = Toasty.info(this, "Earned + 5 trophies!", Toast.LENGTH_LONG);
                globalToast.show();
                int newTrophies = currentTrophies + earnedTrophies;
                Z_TrophyManager.setTrophies(newTrophies);
            }
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

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loadGameplayList(sharedPreferences);

        if (Gameplay_History.gameplaysList == null) {
            Gameplay_History.gameplaysList = new ArrayList<>();
            loadGameplayList(sharedPreferences);
        }

        Gameplay_History.gameplaysList.add(0, new Gameplay("QUIZ       " + String.format("%02d", correctAnswer) + "       " + gamemode + "       "+ TimeTaken + "       " + formattedDate));

        if (Gameplay_History.gameplaysList.size() > 50) {
            Gameplay_History.gameplaysList.remove(Gameplay_History.gameplaysList.size() - 1);
        }

        // Save the updated list
        saveGameplayList(sharedPreferences);

        // Adding a score to the Quizscorelist
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(Modes_Quiz.this);
        scoreManager.addItemToQuizScoreList(correctAnswer);
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


    private void showToast() {
        cancelToast();
        globalToast = Toasty.error(this, "Score not in range", Toast.LENGTH_LONG);
        globalToast.show();
    }


    //Timer
    private TextView tv_quizTimer;
    private boolean isTimerRunning;
    private int secondsPassed;
    private Runnable timerRunnable;
    private int remainingSeconds;


    void InitializeTimer(){
        tv_quizTimer = findViewById(R.id.tv_quizTimer);

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
        tv_quizTimer.setText(timerText);
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


    // Call the RegButtonClickSound method from Z_SoundManager
    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
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

    void QuizResultVoice0() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice0(Modes_Quiz.this);
            }
        }, 200);
    }

    void QuizResultVoice1() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice1(Modes_Quiz.this);
            }
        }, 200);
    }

    void QuizResultVoice2() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice2(Modes_Quiz.this);
            }
        }, 200);
    }

    void QuizResultVoice3() {
        new Handler().postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                Z_SoundManager soundManager = new Z_SoundManager();
                soundManager.QuizVoice3(Modes_Quiz.this);
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

    private void exitQuizActivity(){
        Intent Quiz = new Intent(getApplicationContext(), Modes.class);
        startActivity(Quiz);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //Quiz.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //Quiz.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
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
        imgbtnQuizPause.performClick();
    }
}
