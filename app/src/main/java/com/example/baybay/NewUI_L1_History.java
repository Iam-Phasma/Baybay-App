package com.example.baybay;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import java.util.concurrent.atomic.AtomicBoolean;

public class NewUI_L1_History extends AppCompatActivity {

    TextView TvHistoryContent;
    NestedScrollView SvHistory;
    private int chapterSelected = 1;
    ImageView ImgviewHistoryBoard;
    ImageButton ImgbtnHistoryC1, ImgbtnHistoryC2, ImgbtnHistoryC3;
    ImageButton ImgbtnPlayC1, ImgbtnPlayC2, ImgbtnPlayC3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_history);

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
        int setChapter = intent.getIntExtra("chapter-count", 1);
        chapterSelected = setChapter;

        ImageButton HistoryExit = findViewById(R.id.imgbtn__history_exit);
        HistoryExit.setOnClickListener(v -> onBackPressed());

        ImgviewHistoryBoard = findViewById(R.id.imgview_history_board);
        ImgbtnHistoryC1 = findViewById(R.id.imgbtn_history_c1);
        ImgbtnHistoryC1.setOnClickListener(v -> {
            chapterSelected = 1;
            setChapter();
            ImgbtnHistoryC2.setImageResource(R.drawable.newui_history_c2_unsel);
            ImgbtnHistoryC3.setImageResource(R.drawable.newui_history_c3_unsel);
        });

        AtomicBoolean isPlayC1Clicked = new AtomicBoolean(false);
        ImgbtnPlayC1 = findViewById(R.id.imgbtn_history_playc1);
        ImgbtnPlayC1.setOnClickListener(v -> {
            ImgbtnHistoryC1.performClick();
            isPlayC1Clicked.set(!isPlayC1Clicked.get());

            if (isPlayC1Clicked.get()) {
                ImgbtnPlayC1.setImageResource(R.drawable.newui_history_stop_button);
                Z_History_Narration_Manager.stopChapterB();
                Z_History_Narration_Manager.stopChapterC();
                ImgbtnPlayC2.setImageResource(R.drawable.newui_history_play_button);
                ImgbtnPlayC3.setImageResource(R.drawable.newui_history_play_button);

                Z_History_Narration_Manager.playChapterA(this);
            } else {
                ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
                Z_History_Narration_Manager.stopChapterA();
            }
        });

        ImgbtnHistoryC2 = findViewById(R.id.imgbtn_history_c2);
        ImgbtnHistoryC2.setOnClickListener(v -> {
            ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
            chapterSelected = 2;
            setChapter();
            ImgbtnHistoryC1.setImageResource(R.drawable.newui_history_c1_unsel);
            ImgbtnHistoryC3.setImageResource(R.drawable.newui_history_c3_unsel);
        });

        AtomicBoolean isPlayC2Clicked = new AtomicBoolean(false);
        ImgbtnPlayC2 = findViewById(R.id.imgbtn_history_playc2);
        ImgbtnPlayC2.setOnClickListener(v -> {
            ImgbtnHistoryC2.performClick();
            isPlayC2Clicked.set(!isPlayC2Clicked.get());

            if (isPlayC2Clicked.get()) {
                ImgbtnPlayC2.setImageResource(R.drawable.newui_history_stop_button);
                Z_History_Narration_Manager.stopChapterA();
                Z_History_Narration_Manager.stopChapterC();
                ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
                ImgbtnPlayC3.setImageResource(R.drawable.newui_history_play_button);

                Z_History_Narration_Manager.playChapterB(this);
            } else {
                ImgbtnPlayC2.setImageResource(R.drawable.newui_history_play_button);
                Z_History_Narration_Manager.stopChapterB();
            }
        });

        ImgbtnHistoryC3 = findViewById(R.id.imgbtn_history_c3);
        ImgbtnHistoryC3.setOnClickListener(v -> {
            ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
            chapterSelected = 3;
            setChapter();
            ImgbtnHistoryC1.setImageResource(R.drawable.newui_history_c1_unsel);
            ImgbtnHistoryC2.setImageResource(R.drawable.newui_history_c2_unsel);
        });

        AtomicBoolean isPlayC3Clicked = new AtomicBoolean(false);
        ImgbtnPlayC3 = findViewById(R.id.imgbtn_history_playc3);
        ImgbtnPlayC3.setOnClickListener(v -> {
            ImgbtnHistoryC3.performClick();
            isPlayC3Clicked.set(!isPlayC3Clicked.get());

            if (isPlayC3Clicked.get()) {
                ImgbtnPlayC3.setImageResource(R.drawable.newui_history_stop_button);
                Z_History_Narration_Manager.stopChapterA();
                Z_History_Narration_Manager.stopChapterB();
                ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
                ImgbtnPlayC2.setImageResource(R.drawable.newui_history_play_button);

                Z_History_Narration_Manager.playChapterC(this);
            } else {
                ImgbtnPlayC3.setImageResource(R.drawable.newui_history_play_button);
                Z_History_Narration_Manager.stopChapterC();
            }
        });

        setChapter();
    }

    private void setChapter(){
        TvHistoryContent = findViewById(R.id.tvHistoryContent);

        if (chapterSelected == 1){
            ImgviewHistoryBoard.setImageResource(R.drawable.newui_history_c1_board);
            ImgbtnHistoryC1.setImageResource(R.drawable.newui_history_c1_sel);
            TvHistoryContent.setText(R.string.history_c1);
        } else if (chapterSelected == 2) {
            ImgviewHistoryBoard.setImageResource(R.drawable.newui_history_c2_board);
            ImgbtnHistoryC2.setImageResource(R.drawable.newui_history_c2_sel);
            TvHistoryContent.setText(R.string.history_c2);
        } else if (chapterSelected == 3) {
            ImgviewHistoryBoard.setImageResource(R.drawable.newui_history_c3_board);
            ImgbtnHistoryC3.setImageResource(R.drawable.newui_history_c3_sel);
            TvHistoryContent.setText(R.string.history_c3);
        }

        float lineSpacingMultiplier = 1.5f;
        TvHistoryContent.setLineSpacing(0, lineSpacingMultiplier);
    }

    @Override
    protected void onPause() {
        Z_History_Narration_Manager.stopChapterA();
        Z_History_Narration_Manager.stopChapterB();
        Z_History_Narration_Manager.stopChapterC();

        ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
        ImgbtnPlayC2.setImageResource(R.drawable.newui_history_play_button);
        ImgbtnPlayC3.setImageResource(R.drawable.newui_history_play_button);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Z_History_Narration_Manager.stopChapterA();
        Z_History_Narration_Manager.stopChapterB();
        Z_History_Narration_Manager.stopChapterC();

        super.onBackPressed();
        finish();
    }
}