package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class NewUI_Gameplay_History extends AppCompatActivity {
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
    ImageButton ImgbtnGameplayExit, ImgbtnGameplayChangeViewMode, ImgbtnGameplayHistoryReset;
    TextView TvGamelayChangeViewMode, TvprogressionChart;
    private Toast globalToast;
    public static ArrayList<Gameplay> gameplaysList;
    public RecyclerView recyclerView;
    public Gameplay_History_Recycler_Adapter adapter;
    static SharedPreferences sharedPreferences;
    private boolean isFilterApplied = true;
    int viewModeCount = 1;
    TextView TvGameplayViewMode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_newui_gameplay_history);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();





        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loadGameplayList(sharedPreferences);

        if (gameplaysList == null) {
            gameplaysList = new ArrayList<>();
            loadGameplayList(sharedPreferences);
            //adapter = new recyclerAdapter(gameplaysList);
        }

        recyclerView = findViewById(R.id.recyclerview_gameplayhistory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Gameplay_History_Recycler_Adapter(gameplaysList);
        recyclerView.setAdapter(adapter);


        ImgbtnGameplayExit = findViewById(R.id.btn_gameplay_exit);
        ImgbtnGameplayExit.setOnClickListener(v -> {
            //BACKGROUND MUSIC
            ClickSoundEffect();
            cancelToast();
            finish();
        });

//        ImageButton ImgbtnChart = findViewById(R.id.imgbtn_gameplayhistory_progressionchart);
//        ImgbtnChart.setOnClickListener(view -> {
//            Intent GameplayHistory = new Intent(getApplicationContext(), LineGraph.class);
//            Z_SoundManager.setActivityRulesPaused(true);
//            startActivity(GameplayHistory);
//        });
//
//        TvprogressionChart = findViewById(R.id.tv_history_progressionchart);
//        TvprogressionChart.setOnClickListener(view -> {
//            ImgbtnChart.performClick();
//        });

//        TvGamelayChangeViewMode = findViewById(R.id.tv_gameplayhistory_viewmode);
//        TvGamelayChangeViewMode.setOnClickListener(view -> {
//            ImgbtnGameplayChangeViewMode.performClick();
//        });

        ImgbtnGameplayChangeViewMode = findViewById(R.id.imgbtn_gameplayhistory_changeviewmode);
        ImgbtnGameplayChangeViewMode.setOnClickListener(view -> {
            animateButton(ImgbtnGameplayChangeViewMode);
            TvGameplayViewMode = findViewById(R.id.tv_gameplayhistory_viewmode);
            if (viewModeCount != 4){
                if (viewModeCount == 1){
                    TvGameplayViewMode.setText("View Mode: Quiz");
                } else if (viewModeCount == 2) {
                    TvGameplayViewMode.setText("View Mode: Match");
                }else if (viewModeCount == 3) {
                    TvGameplayViewMode.setText("View Mode: Spell");
                }
                viewModeCount++;
            }else{
                viewModeCount = 1;
                TvGameplayViewMode.setText("View Mode: All");
            }
            updateRecyclerView();
        });

        ImgbtnGameplayHistoryReset = findViewById(R.id.imgbtn_gameplayhistory_reset);
        ImgbtnGameplayHistoryReset.setOnClickListener(v -> {
            animateButton(ImgbtnGameplayHistoryReset);
            cancelToast();
            globalToast = Toasty.info(NewUI_Gameplay_History.this, "If you wish to reset your gameplay history and progress, long press the button carefully.", Toast.LENGTH_SHORT);
            globalToast.show();
        });
        ImgbtnGameplayHistoryReset.setOnLongClickListener(v -> {
            animateButton(ImgbtnGameplayHistoryReset);

            final Dialog dlg = new Dialog(NewUI_Gameplay_History.this, R.style.PopupDialog);
            dlg.setCanceledOnTouchOutside(false);
            dlg.setContentView(R.layout.activity_lessons_reset);
            dlg.show();

            //Prevents back press on sound dialog menu
            dlg.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP);

            final boolean[] isAgreed = {false};
            LottieAnimationView checkbox = dlg.findViewById(R.id.cb_reset_agree);
            checkbox.setOnClickListener(v1 -> {
                cancelToast();
                if (!isAgreed[0]) {
                    // Play animation forward
                    checkbox.setSpeed(1);
                    checkbox.playAnimation();
                    globalToast = Toasty.warning(NewUI_Gameplay_History.this, "I agree and understand the notice as this action cannot be undone.", Toast.LENGTH_LONG);
                    globalToast.show();

                    new Handler().postDelayed(checkbox::pauseAnimation, 1000);
                } else {
                    globalToast = Toasty.warning(NewUI_Gameplay_History.this, "Unchecked", Toast.LENGTH_SHORT);
                    globalToast.show();
                    checkbox.setSpeed(0);
                    checkbox.playAnimation();
                }
                isAgreed[0] = !isAgreed[0];
            });

            ImageButton ImgbtnResetOk = dlg.findViewById(R.id.imgbtn_reset_ok);
            ImgbtnResetOk.setOnClickListener(v12 -> {
                globalToast.cancel();
                if(isAgreed[0]){
                    cancelToast();
                    globalToast = Toasty.success(NewUI_Gameplay_History.this, "Progress has been reset successfully!", Toast.LENGTH_SHORT);
                    globalToast.show();

                    //Gameplay History
                    if (NewUI_Gameplay_History.gameplaysList == null) {
                        NewUI_Gameplay_History.gameplaysList = new ArrayList<>();
                    } else {
                        NewUI_Gameplay_History.gameplaysList.clear();
                    }
                    saveGameplayList();

                    // Clear the Games Scores array
                    Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(this);
                    scoreManager.clearQuizScoreList();
                    scoreManager.clearMatchScoreList();
                    scoreManager.clearSpellScoreList();

                    dlg.dismiss();
                    recreate();
                }else{
                    cancelToast();
                    globalToast = Toasty.error(NewUI_Gameplay_History.this, "To perform the action, you must confirm your agreement.", Toast.LENGTH_SHORT);
                    globalToast.show();
                }
            });

            ImageButton ImgbtnResetBack = dlg.findViewById(R.id.imgbtn_reset_back);
            ImgbtnResetBack.setOnClickListener(v13 -> dlg.dismiss());

            return false;
        });

        if (gameplaysList.size() == 0) {
            cancelToast();
            globalToast = Toasty.info(NewUI_Gameplay_History.this, "It's empty, play some games!", Toast.LENGTH_SHORT);
            globalToast.show();
        }
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

    public static void loadGameplayList(SharedPreferences sharedPreferences) {
        Set<String> gameplaySet = sharedPreferences.getStringSet("userList", new HashSet<>());
        gameplaysList = new ArrayList<>();

        for (String gameplay : gameplaySet) {
            gameplaysList.add(new Gameplay(gameplay));
        }
    }

    static void saveGameplayList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> gameplaySet = new HashSet<>();

        for (Gameplay gameplay : gameplaysList) {
            gameplaySet.add(gameplay.getGameplay());
        }
        editor.putStringSet("userList", gameplaySet);
        editor.apply();
    }

//    private void setAdapter() {
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//    }

    private void updateRecyclerView() {
        ArrayList<Gameplay> filteredList = new ArrayList<>();
        TvGameplayViewMode = findViewById(R.id.tv_gameplayhistory_viewmode);

        for (Gameplay gameplay : gameplaysList) {
            if (viewModeCount == 1) {
                TvGameplayViewMode.setText("View Mode: All");
                filteredList.add(gameplay); // Show all items
            } else if (viewModeCount == 2 && isFilterApplied && gameplay.getGameplay().contains("QUIZ")) {
                TvGameplayViewMode.setText("View Mode: Quiz");
                filteredList.add(gameplay); // Show items containing "QUIZ"
            } else if (viewModeCount == 3 && isFilterApplied && gameplay.getGameplay().contains("MATCH")) {
                TvGameplayViewMode.setText("View Mode: Match");
                filteredList.add(gameplay); // Show items containing "MATCH"
            } else if (viewModeCount == 4 && isFilterApplied && gameplay.getGameplay().contains("SPELL")) {
                TvGameplayViewMode.setText("View Mode: Spell");
                filteredList.add(gameplay); // Show items containing "SPELL"
            }
        }

        // Update the adapter with the filtered data
        adapter = new Gameplay_History_Recycler_Adapter(filteredList);
        recyclerView.setAdapter(adapter);

        // Show a toast message if the filtered list is empty
        if (filteredList.isEmpty()) {
            cancelToast();
            if (viewModeCount == 2) {
                globalToast = Toasty.info(NewUI_Gameplay_History.this, "No Quiz Gameplay yet!", Toasty.LENGTH_SHORT);
                globalToast.show();
            } else if (viewModeCount == 3) {
                globalToast = Toasty.info(NewUI_Gameplay_History.this, "No Match Gameplay yet!", Toasty.LENGTH_SHORT);
                globalToast.show();
            }else if (viewModeCount == 4) {
                globalToast = Toasty.info(NewUI_Gameplay_History.this, "No Spell Gameplay yet!", Toasty.LENGTH_SHORT);
                globalToast.show();
            }
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



//    @Override
//    protected void onPause() {
//        super.onPause();
//        saveGameplayList();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Z_SoundManager.setActivityMainMenuResumed(this);
//    }

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
        ImgbtnGameplayExit.performClick();

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

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
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