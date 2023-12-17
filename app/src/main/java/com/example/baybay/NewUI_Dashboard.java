package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;

public class NewUI_Dashboard extends AppCompatActivity {

    ImageButton ImgbtnDashboardMenu;
    ImageButton ImgbtnTrivia_Refresh;
    TextView TvTrivia;
    private String currentText = "";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_dashboard);

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




        //Initialize Music and SFx
        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        Z_SoundManager.isBgon[0] = preferences.getBoolean("isBgon", true);
        Z_SoundManager.isSoundFx[0] = preferences.getBoolean("isSFx", true);
        BackgroundSound();


        TvTrivia = findViewById(R.id.tv_trivia);
        ImgbtnTrivia_Refresh = findViewById(R.id.imgbtn_trivia_refresh);

        ImgbtnTrivia_Refresh.setEnabled(false);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ImgbtnTrivia_Refresh.setEnabled(true);
        }, 2000); // 700 milliseconds delay

        setRandomTextWithAnimation(TvTrivia);


        ImgbtnTrivia_Refresh.setOnClickListener(v -> {
            animateButton(ImgbtnTrivia_Refresh);
            setRandomTextWithAnimation(TvTrivia);
            ClickSoundEffect();
            ImgbtnTrivia_Refresh.setEnabled(false);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                ImgbtnTrivia_Refresh.setEnabled(true);
            }, 2000); // 700 milliseconds delay
        });

        ImgbtnDashboardMenu = findViewById(R.id.imgbtn_dashboard_menu);
        ImgbtnDashboardMenu.setOnClickListener(v -> {
            try {
                ClickSoundEffect();
                ImgbtnDashboardMenu.setEnabled(false);

                Dialog dlg;
                dlg = new Dialog(NewUI_Dashboard.this, R.style.PopupDialog);
                dlg.setCanceledOnTouchOutside(false);  //disable dialog dismiss when touch outside
                dlg.setContentView(R.layout.activity_sound);
                dlg.show();

                View dialogWindowView = dlg.getWindow().getDecorView();
                Z_Dialogs_Animation.applyBounceAnimation(dialogWindowView);

                // Access the button from the dialog's content view
                ImageButton ImgBtnSoundBg = dlg.findViewById(R.id.cb_Background);
                ImageButton ImgBtnSoundFx = dlg.findViewById(R.id.cb_SEffects);
                ImageButton ImgbtnSoundExit = dlg.findViewById(R.id.imgbtn_sound_exit);

                //Initialize buttons icon when dialog is opened
                if (Z_SoundManager.isBgon[0]) {
                    ImgBtnSoundBg.setImageResource(R.drawable.cb_soundon);
                } else {
                    ImgBtnSoundBg.setImageResource(R.drawable.cb_soundoff);
                }

                if (Z_SoundManager.isSoundFx[0]) {
                    ImgBtnSoundFx.setImageResource(R.drawable.cb_soundon);
                } else {
                    ImgBtnSoundFx.setImageResource(R.drawable.cb_soundoff);
                }

                ImgBtnSoundBg.setOnClickListener(v1 -> {
                    ClickSoundEffect();
                    Z_SoundManager.isBgon[0] = !Z_SoundManager.isBgon[0];  // Toggle the value of isBgon

                    // Set the background music based on isBgon
                    BackgroundSound();

                    // Save Background Music state
                    SharedPreferences.Editor editorBg = preferences.edit();
                    editorBg.putBoolean("isBgon", Z_SoundManager.isBgon[0]);
                    editorBg.apply();

                    // Initialize the background music toggle button drawable
                    if (Z_SoundManager.isBgon[0]) {
                        ImgBtnSoundBg.setImageResource(R.drawable.cb_soundon);
                    } else {
                        ImgBtnSoundBg.setImageResource(R.drawable.cb_soundoff);
                    }
                });

                ImgBtnSoundFx.setOnClickListener(v12 -> {
                    ClickSoundEffect();
                    Z_SoundManager.isSoundFx[0] = !Z_SoundManager.isSoundFx[0];  // Toggle the value of isSFx

                    // Save Background Music state
                    SharedPreferences.Editor editorSFXicon = preferences.edit();
                    editorSFXicon.putBoolean("isSFx", Z_SoundManager.isSoundFx[0]);
                    editorSFXicon.apply();

                    // Initialize the background music toggle button drawable
                    if (Z_SoundManager.isSoundFx[0]) {
                        ImgBtnSoundFx.setImageResource(R.drawable.cb_soundon);
                    } else {
                        ImgBtnSoundFx.setImageResource(R.drawable.cb_soundoff);
                    }
                });

                ImgbtnSoundExit.setOnClickListener(v13 -> {
                    ClickSoundEffect();
                    ImgbtnDashboardMenu.setEnabled(true);
                    dlg.dismiss();
                });

                //Prevents back press on sound dialog menu
                dlg.setOnKeyListener((dialog, keyCode, event) -> {
                    return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP; // Consume the back button press event
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    void BackgroundSound() {
        Z_SoundManager.setActivityMainMenuResumed(this);
        Z_SoundManager.setActivityModesPaused(true);
    }

    //Set random trivia
    private void setRandomTextWithAnimation(final TextView textView) {
        String[] stringArray = getResources().getStringArray(R.array.baybayin_trivia);
        int randomIndex = new Random().nextInt(stringArray.length);
        final String newText = stringArray[randomIndex];

        currentText = "";
        final int length = newText.length();
        final int duration = 12; // Adjust here

        for (int i = 0; i < length; i++) {
            final int finalI = i;
            textView.postDelayed(() -> {
                currentText += newText.charAt(finalI);
                textView.setText(currentText);
                applyFadeInAnimation(textView);
            }, i * duration);
        }
    }

    private void applyFadeInAnimation(TextView textView) {
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(200); // Adjust the duration as needed
        textView.setAnimation(fadeIn);
    }


    // Method to animate refresh trivia
    private void animateButton(View view) {
        //Scale shrink
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 0.7f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 0.7f)
        );
        shrinkAnimator.setDuration(200); // Set the duration of the shrink animation

        //Rotate
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f);
        rotateAnimator.setDuration(500); // Set the duration of the rotation animation

        //Scale restore
        ObjectAnimator restoreAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.7f, 1.0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.7f, 1.0f)
        );
        restoreAnimator.setDuration(300); // Set the duration of the restore animation

        //Animate at the same time
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(shrinkAnimator).with(rotateAnimator);
        animatorSet.play(restoreAnimator).after(rotateAnimator);

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