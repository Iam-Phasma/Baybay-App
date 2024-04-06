package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class NewUI_Chart_Letters extends AppCompatActivity {

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

    ImageButton ImgbtnSeeAll, ImgbtnPrevious, ImgbtnNext, ImgbtnExitChartLetters;
    int cycle = 0;
    private Toast globalToast;
    ImageView ImgviewChart;

    Swipelistener swipelistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_chart_letters);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();



        //Swipe gesture
        ImgviewChart = findViewById(R.id.imgviewChart_empty);
        swipelistener = new Swipelistener(ImgviewChart);


        // Retrieve the value from the previous activity
        cycle = getIntent().getIntExtra("cycle", -1);
        if(cycle == -1){
            globalToast = Toasty.info(NewUI_Chart_Letters.this, "You can perform swipe gestures. Simply swipe the card to the left or right.", Toasty.LENGTH_LONG);
            globalToast.show();
        }

        ImgbtnSeeAll = findViewById(R.id.imgbtn_seeall);
        ImgbtnSeeAll.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgbtnSeeAll);
            ImgbtnSeeAll.setEnabled(false);
            new Handler().postDelayed(() -> {
                ImgbtnSeeAll.setEnabled(true);
                Intent SeeAll = new Intent(getApplicationContext(), NewUI_Chart_Chapters.class);
                startActivity(SeeAll);
                finish();
            }, 500);
        });

        ImgbtnPrevious = findViewById(R.id.imgbtn_previous);
        ImgbtnPrevious.setOnClickListener(v -> {
            disableNavigation();
            animateButton(ImgbtnPrevious);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (cycle > 0) {
                    FlipLeftSound();
                    chartRotateLeft(ImgviewChart);
                    cycle = cycle - 1;
                    handler.postDelayed(() -> {
                        ChartDeafult();
                    },275);

                }else{
                    ImgbtnPrevious.setEnabled(false);
                    cancelToast();
                    globalToast = Toasty.info(NewUI_Chart_Letters.this,  "Your are on the first page.", Toast.LENGTH_SHORT);
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
                if ((cycle == -1) && (cycle < 20)) {
                    cycle = cycle + 2;
                    ChartDeafult();
                } else if (cycle < 20){
                    cycle = cycle + 1;
                    ChartDeafult();
                } else{
                    ImgbtnNext.setEnabled(false);
                    cancelToast();
                    globalToast = Toasty.info(NewUI_Chart_Letters.this, "Your are on the last page.", Toast.LENGTH_SHORT);
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
            finish();
        });

        ChartDeafult();
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

        if ((cycle == 0) || (cycle == -1)) {
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
            mediaPlayer = MediaPlayer.create(NewUI_Chart_Letters.this, R.raw.page_flip_left);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
    }

    void FlipRightSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(NewUI_Chart_Letters.this, R.raw.page_flip_right);
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
        finish();
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

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    public class Swipelistener implements View.OnTouchListener{

        //variable initialization
        GestureDetector gestureDetector;
        //constructor
        Swipelistener(View view){
            //threshold value initialization
            int threshold = 100;
            //velocity
            int velocity_threshold = 100;
            //simple swipe gesture
            GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new
                    GestureDetector.SimpleOnGestureListener(){

                        @Override
                        public boolean onDown(@NonNull MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {

                            //get x diff
                            float xDiff = e2.getX()-e1.getX();
                            //get y diff
                            float yDiff = e2.getY()-e1.getY();

                            try {
                                if (Math.abs(xDiff)>Math.abs(yDiff)){
                                    if (Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold) {
                                        if (xDiff>0){
                                            ImgbtnPrevious.performClick();
                                        }else {
                                            ImgbtnNext.performClick();
                                        }
                                        return true;

                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            return false;
                            //return super.onFling(e1, e2, velocityX, velocityY);
                        }
                    };
            gestureDetector = new GestureDetector(simpleOnGestureListener);
            view.setOnTouchListener(this);
        }

        public boolean onTouch(View v, MotionEvent event){
            return gestureDetector.onTouchEvent(event);
        }
    }

}