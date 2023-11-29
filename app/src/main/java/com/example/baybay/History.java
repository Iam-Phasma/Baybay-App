package com.example.baybay;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

public class History extends AppCompatActivity {

    TextView TvHistoryContent;
    ProgressBar PbHistory;
    NestedScrollView SvHistory;

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

        //Setting String Content to Textview
        TvHistoryContent = findViewById(R.id.tvHistoryContent);
        TvHistoryContent.setText(R.string.baybayin_overview);

        //Syncing Progressbar to Scrollview
        PbHistory = findViewById(R.id.progressBar_history);
        SvHistory = findViewById(R.id.scrollViewHistory);
        SvHistory.getViewTreeObserver().addOnScrollChangedListener(this::updateProgressBar);

        // Set the initial background and text color
        getWindow().setBackgroundDrawableResource(R.drawable.history_light_bg);
        TvHistoryContent.setTextColor(Color.parseColor("#484B53"));

        //Set initial custom font of Textview
        Typeface mFont = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mFont = getResources().getFont(R.font.times_new_roman);
        }
        TvHistoryContent.setTypeface(mFont);

        // Set line spacing of Textview
        float lineSpacingMultiplier = 1.5f;
        TvHistoryContent.setLineSpacing(0, lineSpacingMultiplier);

        final boolean[] isDark = {false};
        ImageButton Themebtn = findViewById(R.id.imgbtn__history_theme);
        Themebtn.setImageResource(R.drawable.guide_history_lighticon);
        Themebtn.setOnClickListener(v -> {
            if (!isDark[0]){
                getWindow().setBackgroundDrawableResource(R.drawable.history_dark_bg);
                TvHistoryContent.setTextColor(Color.parseColor("#F2F2F2"));
                Themebtn.setImageResource(R.drawable.guide_history_darkicon);
                isDark[0] = true;
            }else {
                getWindow().setBackgroundDrawableResource(R.drawable.history_light_bg);
                TvHistoryContent.setTextColor(Color.parseColor("#484B53"));
                Themebtn.setImageResource(R.drawable.guide_history_lighticon);
                isDark[0] = false;
            }
        });

        ImageButton HistoryExit = findViewById(R.id.imgbtn__history_exit);
        HistoryExit.setOnClickListener(v -> onBackPressed());
    }

    //Update progressbar status in-sync to scrollview
    private void updateProgressBar() {
        int maxScroll = SvHistory.getChildAt(0).getHeight() - SvHistory.getHeight();
        int currentScroll = SvHistory.getScrollY();
        int progress = (int) ((currentScroll / (float) maxScroll) * 1000);
        PbHistory.setProgress(progress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent Mainmenu = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(Mainmenu);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        Mainmenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
}