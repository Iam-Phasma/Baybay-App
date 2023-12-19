package com.example.baybay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class Gameplay_History extends AppCompatActivity {
    ImageButton ImgbtnGameplayExit, ImgbtnGameplayChangeViewMode;
    TextView TvGamelayChangeViewMode, TvprogressionChart;
    private Toast globalToast;
    public static ArrayList<Gameplay> gameplaysList;
    public RecyclerView recyclerView;
    public recyclerAdapter adapter;
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

        setContentView(R.layout.activity_gameplay_history);

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
        adapter = new recyclerAdapter(gameplaysList);
        recyclerView.setAdapter(adapter);


        ImgbtnGameplayExit = findViewById(R.id.btn_gameplay_exit);
        ImgbtnGameplayExit.setOnClickListener(v -> {
            //BACKGROUND MUSIC
            cancelToast();
            Z_SoundManager.setActivityRulesPaused(true);
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


        if (gameplaysList.size() == 0) {
            cancelToast();
            globalToast = Toasty.info(Gameplay_History.this, "It's empty, play some games!", Toast.LENGTH_SHORT);
            globalToast.show();
        }
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
        adapter = new recyclerAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        // Show a toast message if the filtered list is empty
        if (filteredList.isEmpty()) {
            cancelToast();
            if (viewModeCount == 2) {
                globalToast = Toasty.info(Gameplay_History.this, "No Quiz Gameplay yet!", Toasty.LENGTH_SHORT);
                globalToast.show();
            } else if (viewModeCount == 3) {
                globalToast = Toasty.info(Gameplay_History.this, "No Match Gameplay yet!", Toasty.LENGTH_SHORT);
                globalToast.show();
            }else if (viewModeCount == 4) {
                globalToast = Toasty.info(Gameplay_History.this, "No Spell Gameplay yet!", Toasty.LENGTH_SHORT);
                globalToast.show();
            }
        }
    }





    @Override
    protected void onPause() {
        super.onPause();
        Z_SoundManager.setActivityRulesPaused(true);
        Z_SoundManager.setActivityLessonsPaused(true);
        saveGameplayList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Z_SoundManager.setActivityRulesResumed(this);
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
        ImgbtnGameplayExit.performClick();

    }


}