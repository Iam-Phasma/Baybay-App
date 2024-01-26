package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

import es.dmoral.toasty.Toasty;

public class NewUI_AboutApp extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_about_app);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();

        ImageButton ImgbtnAboutAppExit = findViewById(R.id.imgbtn_aboutapp_exit);
        ImgbtnAboutAppExit.setOnClickListener(v -> {
            onBackPressed();
        });

        AtomicBoolean isPurposeFlipped = new AtomicBoolean(false);
        AtomicBoolean isAboutFlipped = new AtomicBoolean(false);

        ImageView purpose = findViewById(R.id.imgview_purpose);
        ImageView About = findViewById(R.id.imgview_aboutapp);

        purpose.setOnClickListener(v -> {
            purpose.setEnabled(false);
            FlipRightSound();
            if (!isPurposeFlipped.get()) {
                chartRotateLeft(purpose);
                isPurposeFlipped.set(true);

                new Handler().postDelayed(() -> {
                    purpose.setImageResource(R.drawable.newui_aboutapp_purpose_txt);
                }, 350);
            } else {
                chartRotateRight(purpose);
                isPurposeFlipped.set(false);

                new Handler().postDelayed(() -> {
                    purpose.setImageResource(R.drawable.newui_aboutapp_purpose_face);
                }, 350);
            }

            new Handler().postDelayed(() -> {
                purpose.setEnabled(true);
            }, 380);
        });



        About.setOnClickListener(v -> {
            About.setEnabled(false);
            FlipRightSound();
            if (!isAboutFlipped.get()) {
                chartRotateLeft(About);
                isAboutFlipped.set(true);

                new Handler().postDelayed(() -> {
                    About.setImageResource(R.drawable.newui_aboutapp_about_txt);

                }, 350);
            } else {
                chartRotateRight(About);
                isAboutFlipped.set(false);

                new Handler().postDelayed(() -> {
                    About.setImageResource(R.drawable.newui_aboutapp_about_face);
                }, 350);
            }

            new Handler().postDelayed(() -> {
                About.setEnabled(true);
            }, 380);
        });
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


    private void chartRotateRight(View view) {

        AnimatorSet animatorSet = new AnimatorSet();

        // Define the scale animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.6f);

        // Combine the scale animations
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(300); // Duration for scale animation

        // Start the scale animation
        animatorSet.start();

        // Delay for the rotation animation
        int delayMillis = 50; // Adjust as needed
        new Handler().postDelayed(() -> {
            // Perform the rotation animation after the delay
            view.animate()
                    .rotationYBy(180) // Spin 360 degrees
                    .setDuration(500) // Duration for rotation
                    .withEndAction(() -> {

                        //Start the animation to scale back up
                        view.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(200)
                                .start();
                    })
                    .start();
        }, delayMillis);

    }

    private void chartRotateLeft(View view) {

        AnimatorSet animatorSet = new AnimatorSet();

        // Define the scale animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.6f);

        // Combine the scale animations
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(300); // Duration for scale animation

        // Start the scale animation
        animatorSet.start();

        // Delay for the rotation animation
        int delayMillis = 50; // Adjust as needed
        new Handler().postDelayed(() -> {
            // Perform the rotation animation after the delay
            view.animate()
                    .rotationYBy(-180) // Spin 360 degrees
                    .setDuration(500) // Duration for rotation
                    .withEndAction(() -> {

                        //Start the animation to scale back up
                        view.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(200)
                                .start();
                    })
                    .start();
        }, delayMillis);
    }

    void FlipRightSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(NewUI_AboutApp.this, R.raw.page_flip_right);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}