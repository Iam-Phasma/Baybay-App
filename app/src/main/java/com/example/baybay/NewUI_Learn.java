package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.concurrent.atomic.AtomicBoolean;

public class NewUI_Learn extends AppCompatActivity {

    ImageButton ImgbtnLearExit;
    private LinearLayout hiddenButtonsLayout;
    private LinearLayout hiddenButtons2Layout;
    private LinearLayout hiddenButtons3Layout;
    private LinearLayout hiddenButtons4Layout;
    private ImageButton Origin;
    private ImageButton Introduction;
    private ImageButton Characters;
    private ImageButton Rules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_learn);

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


        ImgbtnLearExit = findViewById(R.id.imgbtn_learn_exit);
        ImgbtnLearExit.setOnClickListener(v -> {
            finish();
        });

        hiddenButtonsLayout = findViewById(R.id.hiddenButtonsLayout);
        hiddenButtons2Layout = findViewById(R.id.hiddenButtons2Layout);
        hiddenButtons3Layout = findViewById(R.id.hiddenButtons3Layout);
        hiddenButtons4Layout = findViewById(R.id.hiddenButtons4Layout);

        Origin = findViewById(R.id.mainButton1);
        Introduction = findViewById(R.id.mainButton2);
        Characters = findViewById(R.id.mainButton3);
        Rules = findViewById(R.id.mainButton4);

        AtomicBoolean isL1Clicked = new AtomicBoolean(false);
        Origin.setOnClickListener(v -> {
            animateButton(Origin);
            isL1Clicked.set(!isL1Clicked.get());

            if (isL1Clicked.get()) {
                Origin.setImageResource(R.drawable.newui_lesson1_sel);
            } else {
                Origin.setImageResource(R.drawable.newui_lesson1_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtonsLayout);
        });

        AtomicBoolean isL2Clicked = new AtomicBoolean(false);
        Introduction.setOnClickListener(view -> {
            animateButton(Introduction);
            isL2Clicked.set(!isL2Clicked.get());

            if (isL2Clicked.get()) {
                Introduction.setImageResource(R.drawable.newui_lesson2_sel);
            } else {
                Introduction.setImageResource(R.drawable.newui_lesson2_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons2Layout);
        });

        AtomicBoolean isL3Clicked = new AtomicBoolean(false);
        Characters.setOnClickListener(view -> {
            animateButton(Characters);
            isL3Clicked.set(!isL3Clicked.get());

            if (isL3Clicked.get()) {
                Characters.setImageResource(R.drawable.newui_lesson3_sel);
            } else {
                Characters.setImageResource(R.drawable.newui_lesson3_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons3Layout);
        });

        AtomicBoolean isL4Clicked = new AtomicBoolean(false);
        Rules.setOnClickListener(view -> {
            animateButton(Rules);
            isL4Clicked.set(!isL4Clicked.get());

            if (isL4Clicked.get()) {
                Rules.setImageResource(R.drawable.newui_lesson4_sel);
            } else {
                Rules.setImageResource(R.drawable.newui_lesson4_unsel);
            }

            toggleVisibilityWithAnimation(hiddenButtons4Layout);
        });

    }

    private void toggleVisibilityWithAnimation(final LinearLayout layout) {
        layout.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int width = layout.getMeasuredWidth();

        if (layout.getVisibility() == View.VISIBLE) {
            // Slide out and fade out animation
            ObjectAnimator slideOut = ObjectAnimator.ofFloat(layout, "translationX", 0f, width);
            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(layout, "alpha", 1f, 0f);

            slideOut.setInterpolator(new AccelerateDecelerateInterpolator());
            slideOut.setDuration(500);
            fadeOut.setDuration(500);

            slideOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    layout.setVisibility(View.GONE);
                }
            });

            slideOut.start();
            fadeOut.start();
        } else {
            // Slide in and fade in animation
            layout.setVisibility(View.VISIBLE);

            ObjectAnimator slideIn = ObjectAnimator.ofFloat(layout, "translationX", width, 0f);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(layout, "alpha", 0f, 1f);

            slideIn.setInterpolator(new AccelerateDecelerateInterpolator());
            slideIn.setDuration(500);
            fadeIn.setDuration(500);

            slideIn.start();
            fadeIn.start();
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

}