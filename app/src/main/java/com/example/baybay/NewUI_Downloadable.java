package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class NewUI_Downloadable extends AppCompatActivity {

    private ImageButton ImgbtnDownloadableExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_downloadable);

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



        ImgbtnDownloadableExit = findViewById(R.id.imgbtn_downloadable_exit);
        ImgbtnDownloadableExit.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgbtnDownloadableExit);
            finish();
        });

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
}