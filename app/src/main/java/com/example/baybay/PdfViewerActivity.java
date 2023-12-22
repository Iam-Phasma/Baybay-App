package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.util.concurrent.atomic.AtomicBoolean;

public class PdfViewerActivity extends AppCompatActivity {

    ImageButton Imgbtn_pdfview_exit;
    TextView TvReadingTitle;
    ImageButton ImgbtnStartPause, ImgbtnReset;
    TextView TvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_pdf_viewer);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Get the root view of the layout
        View rootView = getWindow().getDecorView().getRootView();

        // Set the background color using a hexadecimal color value
        rootView.setBackgroundColor(Color.parseColor("#fffbff"));



        PDFView pdfView = findViewById(R.id.pdfView);
        TvReadingTitle = findViewById(R.id.tv_reading_title);

        Intent intent = getIntent();
        int bookNumber = intent.getIntExtra("bookNumber", 1);
        String pdfFileName = "";

        if(bookNumber == 1){
            pdfFileName = "pdf_library_mga_tula.pdf";
            TvReadingTitle.setText("Mga Tula");
        } else if (bookNumber == 2) {
            pdfFileName = "pdf_library_maikling_kwento.pdf";
            TvReadingTitle.setText("Maikling Kwento");
            TvReadingTitle.setTextSize(16);
        } else if (bookNumber == 3) {
            pdfFileName = "pdf_library_mga_alamat.pdf";
            TvReadingTitle.setText("Mga Alamat");
        } else if (bookNumber == 4) {
            pdfFileName = "pdf_library_likhang_makabayan.pdf";
            TvReadingTitle.setText("Likhang Makabayan");
            TvReadingTitle.setTextSize(16);
        }

        pdfView.fromAsset(pdfFileName)
                .enableSwipe(true)
                //.scrollHandle(new DefaultScrollHandle(this))
                .load();


        Imgbtn_pdfview_exit = findViewById(R.id.imgbtn_pdfview_exit);
        Imgbtn_pdfview_exit.setOnClickListener(view -> {
            stopTimer();
            finish();
        });

        InitializeTimer();
        pauseTimer();

        AtomicBoolean isStartClicked = new AtomicBoolean(false);
        ImgbtnStartPause = findViewById(R.id.imgbtn_pdfview_startpause);

        ImgbtnStartPause.setImageResource(R.drawable.newui_readingview_start_button);
        ImgbtnStartPause.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgbtnStartPause);
            isStartClicked.set(!isStartClicked.get());
            if (isStartClicked.get()) {
                resumeTimer();
                ImgbtnStartPause.setImageResource(R.drawable.newui_readingview_pause_button);
            } else {
                stopTimer();
                ImgbtnStartPause.setImageResource(R.drawable.newui_readingview_resume_button);
            }
        });

        ImgbtnReset = findViewById(R.id.imgbtn_pdfview_reset);
        ImgbtnReset.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgbtnReset);
            resetTimer();
            isStartClicked.set(false);
            ImgbtnStartPause.setImageResource(R.drawable.newui_readingview_start_button);
        });


    }


    private boolean isTimerRunning;
    private int secondsPassed;
    private Runnable timerRunnable;
    private int remainingSeconds;
    public String TimeTaken;
    Handler handler = new Handler();
    void InitializeTimer(){
        TvTime = findViewById(R.id.tv_readingview_time);

        // Initialize the timer to 00:00
        updateTimerText(0);

        // Create a new Handler and Runnable to update the timer every second

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
        TvTime.setText(timerText);
        TimeTaken = timerText;
    }

    private void stopTimer() {
        // Remove any pending Runnable callbacks to stop the timer
        handler.removeCallbacks(timerRunnable);

        // Set the timer status to not running
        isTimerRunning = false;
    }

    private void resetTimer() {
        // Stop the timer if it's running
        stopTimer();

        // Reset the timer values
        secondsPassed = 0;
        remainingSeconds = 0;

        // Update the timer text view with the new time
        updateTimerText(secondsPassed);
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
    public void onBackPressed() {
        super.onBackPressed();
        Imgbtn_pdfview_exit.performClick();
    }
}