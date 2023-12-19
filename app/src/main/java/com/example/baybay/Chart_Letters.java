package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class Chart_Letters extends AppCompatActivity {

    ImageButton ImgbtnSeeAll, ImgbtnPrevious, ImgbtnNext, ImgbtnExitChartLetters;
    int cycle = 0;
    private Toast globalToast;
    ImageView ImgviewChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_chart_letters);

        // Fullscreen beyond punch hole camera
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




        // Retrieve the value of cycleselect from the previous activity
        cycle = getIntent().getIntExtra("cycle", 0);

        ImgbtnSeeAll = findViewById(R.id.imgbtn_seeall);
        ImgbtnSeeAll.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgbtnSeeAll);
//            ImgbtnSeeAll.setEnabled(false);
//            ImgbtnPrevious.setEnabled(false);
//            ImgbtnNext.setEnabled(false);
            //BACKGROUND MUSIC
            //=======Z_SoundManager.setActivityChapterPaused(false);
            new Handler().postDelayed(() -> {
                Intent SeeAll = new Intent(getApplicationContext(), Chart_Chapters.class);
                startActivity(SeeAll);
//                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                finish();
            }, 500);
        });


        ImgbtnPrevious = findViewById(R.id.imgbtn_previous);
        ImgbtnPrevious.setOnClickListener(v -> {
            disableNavigation();
            chartRotateLeft(ImgviewChart);
            FlipLeftSound();
            animateButton(ImgbtnPrevious);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (cycle > 0) {
                    cycle = cycle - 1;
                    ChartDeafult();
                }else{
                    ImgbtnPrevious.setEnabled(false);
                    cancelToast();
                    globalToast = Toasty.info(Chart_Letters.this,  "Your are on the first page.", Toast.LENGTH_SHORT);
                    globalToast.show();
                    ImgbtnNext.setEnabled(true);
                    ImgbtnExitChartLetters.setEnabled(true);
                    ImgbtnSeeAll.setEnabled(true);
                }
            }, 250);
        });


        ImgbtnNext = findViewById(R.id.imgbtn_next);
        ImgbtnNext.setOnClickListener(v -> {
            disableNavigation();
            chartRotateRight(ImgviewChart);
            FlipRightSound();
            animateButton(ImgbtnNext);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (cycle < 20){
                    cycle = cycle + 1;
                    ChartDeafult();
                }else{
                    ImgbtnNext.setEnabled(false);
                    cancelToast();
                    globalToast = Toasty.info(Chart_Letters.this, "Your are on the last page.", Toast.LENGTH_SHORT);
                    globalToast.show();
                    ImgbtnPrevious.setEnabled(true);
                    ImgbtnExitChartLetters.setEnabled(true);
                    ImgbtnSeeAll.setEnabled(true);
                }
            }, 250);

        });

        ImgbtnExitChartLetters = findViewById(R.id.imgbtn_exit_chartletter);
        ImgbtnExitChartLetters.setOnClickListener(v -> {
            ClickSoundEffect();
            cancelToast();
            //stop music
            //=======Z_SoundManager.StopChartBgMusic();

//            Intent ChartLetters = new Intent(getApplicationContext(), MainMenu.class);
//            startActivity(ChartLetters);
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

        ChartDeafult();

    }

    public void disableNavigation(){
        ImgbtnNext.setEnabled(false);
        ImgbtnPrevious.setEnabled(false);
        ImgbtnSeeAll.setEnabled(false);
        ImgbtnExitChartLetters.setEnabled(false);
    }

    public void enableNavigation(){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            ImgbtnNext.setEnabled(true);
            ImgbtnPrevious.setEnabled(true);
            ImgbtnSeeAll.setEnabled(true);
            ImgbtnExitChartLetters.setEnabled(true);
        }, 1000);

    }

    private void chartRotateRight(View view) {
        if(cycle != 20){
            AnimatorSet animatorSet = new AnimatorSet();

            // Define the scale animation
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f);

            // Combine the scale animations
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(500); // Duration for scale animation

            // Start the scale animation
            animatorSet.start();

            // Delay for the rotation animation
            int delayMillis = 150; // Adjust as needed
            new Handler().postDelayed(() -> {
                // Perform the rotation animation after the delay
                view.animate()
                        .rotationYBy(360) // Spin 360 degrees
                        .setDuration(500) // Duration for rotation
                        .withEndAction(() -> {

                            //Start the animation to scale back up
                            view.animate()
                                    .scaleX(1.0f)
                                    .scaleY(1.0f)
                                    .setDuration(300)
                                    .start();
                        })
                        .start();
            }, delayMillis);
            enableNavigation();
        }
    }

    private void chartRotateLeft(View view) {
        if(cycle != 0){
            AnimatorSet animatorSet = new AnimatorSet();

            // Define the scale animation
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f);

            // Combine the scale animations
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(500); // Duration for scale animation

            // Start the scale animation
            animatorSet.start();

            // Delay for the rotation animation
            int delayMillis = 150; // Adjust as needed
            new Handler().postDelayed(() -> {
                // Perform the rotation animation after the delay
                view.animate()
                        .rotationYBy(-360) // Spin 360 degrees
                        .setDuration(500) // Duration for rotation
                        .withEndAction(() -> {

                            //Start the animation to scale back up
                            view.animate()
                                    .scaleX(1.0f)
                                    .scaleY(1.0f)
                                    .setDuration(300)
                                    .start();
                        })
                        .start();
            }, delayMillis);
            enableNavigation();
        }
    }



    public void ChartDeafult() {
        ImgviewChart = findViewById(R.id.imgviewChart_empty);

        if (cycle == 0) {
            ImgviewChart.setImageResource(R.drawable.chart_kudlit);
        } else if (cycle == 1) {
            ImgviewChart.setImageResource(R.drawable.chart_a);
        } else if (cycle == 2) {
            ImgviewChart.setImageResource(R.drawable.chart_b);
        } else if (cycle == 3) {
            ImgviewChart.setImageResource(R.drawable.chart_k);
        } else if (cycle == 4) {
            ImgviewChart.setImageResource(R.drawable.chart_d);
        } else if (cycle == 5) {
            ImgviewChart.setImageResource(R.drawable.chart_e);
        } else if (cycle == 6) {
            ImgviewChart.setImageResource(R.drawable.chart_g);
        } else if (cycle == 7) {
            ImgviewChart.setImageResource(R.drawable.chart_h);
        } else if (cycle == 8) {
            ImgviewChart.setImageResource(R.drawable.chart_i);
        } else if (cycle == 9) {
            ImgviewChart.setImageResource(R.drawable.chart_l);
        } else if (cycle == 10) {
            ImgviewChart.setImageResource(R.drawable.chart_m);
        } else if (cycle == 11) {
            ImgviewChart.setImageResource(R.drawable.chart_n);
        } else if (cycle == 12) {
            ImgviewChart.setImageResource(R.drawable.chart_ng);
        } else if (cycle == 13) {
            ImgviewChart.setImageResource(R.drawable.chart_o);
        } else if (cycle == 14) {
            ImgviewChart.setImageResource(R.drawable.chart_p);
        } else if (cycle == 15) {
            ImgviewChart.setImageResource(R.drawable.chart_r);
        } else if (cycle == 16) {
            ImgviewChart.setImageResource(R.drawable.chart_s);
        } else if (cycle == 17) {
            ImgviewChart.setImageResource(R.drawable.chart_t);
        } else if (cycle == 18) {
            ImgviewChart.setImageResource(R.drawable.chart_u);
        } else if (cycle == 19) {
            ImgviewChart.setImageResource(R.drawable.chart_w);
        } else if (cycle == 20) {
            ImgviewChart.setImageResource(R.drawable.chart_y);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //=======Z_SoundManager.setActivityLetterPaused(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //=======Z_SoundManager.setActivityLetterResumed(this);
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


    void FlipLeftSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(Chart_Letters.this, R.raw.page_flip_left);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
    }


    void FlipRightSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(Chart_Letters.this, R.raw.pag_flip_right);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
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
        cancelToast();
//        Intent ChartLetters = new Intent(getApplicationContext(), Chart_Chapters.class);
//        startActivity(ChartLetters);
//        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
        finish();
    }

}