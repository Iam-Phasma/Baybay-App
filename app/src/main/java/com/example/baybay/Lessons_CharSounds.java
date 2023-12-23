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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Lessons_CharSounds extends AppCompatActivity {

    ImageButton BtnPrevious, BtnNext;
    int progressBarCount = 1;
    ImageView imgview_charsoundboard;
    ImageButton miniA, miniBa, miniKa, miniDa, miniE, miniGa, miniHa, miniI, miniLa, miniMa, miniNa, miniNga, miniO, miniPa, miniRa, miniSa, miniTa, miniU, miniWa, miniYa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_lessons_char_sounds);

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
        int setProgressBarCount = intent.getIntExtra("progressbar-count", 1);
        progressBarCount = setProgressBarCount;

        setCharSoundBoard();

        ImageButton btnExit = findViewById(R.id.btn_charsound_exit);
        btnExit.setOnClickListener(v -> onBackPressed());

        ImageButton ImgbtnVolume = findViewById(R.id.imgbtn_charsound_volume);
        ImgbtnVolume.setOnClickListener(v -> {
            ImgbtnVolume.setEnabled(false);
            scriptSound();

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                ImgbtnVolume.setEnabled(true);
            }, 700);

        });

        BtnNext = findViewById(R.id.btn_charsound_next);
        BtnNext.setOnClickListener(v -> {
            fadeAnimation();
            ClickSoundEffect();
            animateButton(BtnNext);

            ImgbtnVolume.setEnabled(false);
            BtnNext.setEnabled(false);
            BtnPrevious.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (progressBarCount <= 19){
                    progressBarCount = progressBarCount + 1;
                    setCharSoundBoard();
                }else if (progressBarCount == 20){
                    progressBarCount = 1;
                    setCharSoundBoard();
                }
                scriptSound();
            }, 200);

            handler.postDelayed(() -> {
                ImgbtnVolume.setEnabled(true);
                BtnNext.setEnabled(true);
                BtnPrevious.setEnabled(true);
            }, 700);
        });

        BtnPrevious = findViewById(R.id.btn_charsound_previous);
        BtnPrevious.setOnClickListener(v -> {
            fadeAnimation();
            ClickSoundEffect();
            animateButton(BtnPrevious);
            ImgbtnVolume.setEnabled(false);
            BtnNext.setEnabled(false);
            BtnPrevious.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (progressBarCount >= 2){
                    progressBarCount = progressBarCount - 1;
                    setCharSoundBoard();
                }else if (progressBarCount == 1){
                    progressBarCount = 20;
                    setCharSoundBoard();
                }
                scriptSound();
            }, 200);

            handler.postDelayed(() -> {
                ImgbtnVolume.setEnabled(true);
                BtnNext.setEnabled(true);
                BtnPrevious.setEnabled(true);
            }, 700);
        });

        int[] charIds = {R.id.imgbtn_char_mini_a, R.id.imgbtn_char_mini_e, R.id.imgbtn_char_mini_i, R.id.imgbtn_char_mini_o,
                R.id.imgbtn_char_mini_u, R.id.imgbtn_char_mini_ba, R.id.imgbtn_char_mini_ka, R.id.imgbtn_char_mini_da,
                R.id.imgbtn_char_mini_ga, R.id.imgbtn_char_mini_ha, R.id.imgbtn_char_mini_la, R.id.imgbtn_char_mini_ma,
                R.id.imgbtn_char_mini_na, R.id.imgbtn_char_mini_nga, R.id.imgbtn_char_mini_pa, R.id.imgbtn_char_mini_ra,
                R.id.imgbtn_char_mini_sa, R.id.imgbtn_char_mini_ta, R.id.imgbtn_char_mini_wa, R.id.imgbtn_char_mini_ya};

        for (int i = 0; i < charIds.length; i++) {
            ImageButton charButton = findViewById(charIds[i]);
            final int finalProgressBarCount = i + 1;
            charButton.setOnClickListener(v -> {
                progressBarCount = finalProgressBarCount;
                fadeAnimation();
                ClickSoundEffect();
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    setCharSoundBoard();
                    scriptSound();
                }, 200);

            });
        }

    }

    public void scriptSound() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            boolean[] sfxPass = Z_SoundManager.isSoundFx;
            if (sfxPass.length > 0 && sfxPass[0]) {
                MediaPlayer mediaPlayer = null;

                if (progressBarCount == 1) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_a);
                } else if (progressBarCount == 2) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_e);
                } else if (progressBarCount == 3) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_i);
                } else if (progressBarCount == 4) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_o);
                } else if (progressBarCount == 5) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_u);
                } else if (progressBarCount == 6) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ba);
                } else if (progressBarCount == 7) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ka);
                } else if (progressBarCount == 8) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_da);
                } else if (progressBarCount == 9) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ga);
                } else if (progressBarCount == 10) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ha);
                } else if (progressBarCount == 11) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_la);
                } else if (progressBarCount == 12) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ma);
                } else if (progressBarCount == 13) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_na);
                } else if (progressBarCount == 14) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_nga);
                } else if (progressBarCount == 15) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_pa);
                } else if (progressBarCount == 16) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ra);
                } else if (progressBarCount == 17) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_sa);
                } else if (progressBarCount == 18) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ta);
                } else if (progressBarCount == 19) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_wa);
                } else if (progressBarCount == 20) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.charsound_ya);
                }

                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(MediaPlayer::release);

            }
        }, 200);
    }



    //Set CharSound Bord Image
    public void setCharSoundBoard(){
        imgview_charsoundboard = findViewById(R.id.imgview_charsoundboard);
        switch(progressBarCount) {
            case 1:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_a);
                break;
            case 2:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_e);
                break;
            case 3:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_i);
                break;
            case 4:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_o);
                break;
            case 5:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_u);
                break;
            case 6:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ba);
                break;
            case 7:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ka);
                break;
            case 8:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_da);
                break;
            case 9:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ga);
                break;
            case 10:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ha);
                break;
            case 11:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_la);
                break;
            case 12:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ma);
                break;
            case 13:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_na);
                break;
            case 14:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_nga);
                break;
            case 15:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_pa);
                break;
            case 16:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ra);
                break;
            case 17:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_sa);
                break;
            case 18:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ta);
                break;
            case 19:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_wa);
                break;
            case 20:
                imgview_charsoundboard.setImageResource(R.drawable.newui_char_ya);
                break;
            default:

        }
    }






    //Fde in - out animation
    public void fadeAnimation(){
        //fade-out animation
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(200);
        fadeOut.setFillAfter(true);

        //fade-in animation
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(700);
        fadeIn.setFillAfter(true);

        //apply fade-in after fade-out
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                imgview_charsoundboard.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        imgview_charsoundboard.startAnimation(fadeOut);
    }


    private void animateButton(View view) {
        // Create a scale animator to shrink the button
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 0.9f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 0.9f)
        );
        shrinkAnimator.setDuration(200);

        //restore the button to its original size
        ObjectAnimator restoreAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f, 1.0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f, 1.0f)
        );
        restoreAnimator.setDuration(500);  //should be 400 higher

        //set to play the shrink and restore animations sequentially
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
    protected void onPause() {
        super.onPause();
        //Z_SoundManager.setActivityCharactersPaused(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Z_SoundManager.setActivityCharactersResumed(this);
        scriptSound();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //BACKGROUND MUSIC
        //Z_SoundManager.setActivityLessonsPaused(false);

//        Intent CharSounds = new Intent(getApplicationContext(), Lessons.class);
//        startActivity(CharSounds);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //CharSounds.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
    }
}