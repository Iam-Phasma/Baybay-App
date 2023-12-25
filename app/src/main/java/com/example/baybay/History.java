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

public class History extends AppCompatActivity {

    TextView TvHistoryContent;
    NestedScrollView SvHistory;
    private int chapterSelected = 1;
    ImageView ImgviewHistoryBoard;
    ImageButton ImgbtnHistoryC1, ImgbtnHistoryC2, ImgbtnHistoryC3;
    ImageButton ImgbtnPlayC1;

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
            } else {
                ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
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

        ImgbtnHistoryC3 = findViewById(R.id.imgbtn_history_c3);
        ImgbtnHistoryC3.setOnClickListener(v -> {
            ImgbtnPlayC1.setImageResource(R.drawable.newui_history_play_button);
            chapterSelected = 3;
            setChapter();
            ImgbtnHistoryC1.setImageResource(R.drawable.newui_history_c1_unsel);
            ImgbtnHistoryC2.setImageResource(R.drawable.newui_history_c2_unsel);
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

        //Set font of Textview
//        Typeface mFont = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            mFont = getResources().getFont(R.font.times_new_roman);
//        }
//        TvHistoryContent.setTypeface(mFont);

        //line spacing
        float lineSpacingMultiplier = 1.5f;
        TvHistoryContent.setLineSpacing(0, lineSpacingMultiplier);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}