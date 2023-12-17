package com.example.baybay;



import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Random;

import es.dmoral.toasty.Toasty;

public class MainMenu extends AppCompatActivity {

    private ImageButton ImgbtnDrills, ImgbtnChart, ImgbtnGuide, ImgbtnTool;
    private ImageButton ImgbtnSettings, ImgbtnMainQuit;
    static MediaPlayer songMain;
    //MediaPlayer soundFX;
    private Toast globalToast;
    SharedPreferences preferences;

//    public static boolean[] isSoundFx = {true};
//    public static boolean[] isBgon = {true};
    Handler handler = new Handler();

    // Declare the buttons as instance variables
    private ImageButton ImgbtnHistory;
    private ImageButton ImgbtnLessons;
    private ImageButton ImgbtnLibrary;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main_menu);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the background to a drawable resource
        getWindow().setBackgroundDrawableResource(R.drawable.bg_main_menu2);

        // Initialize the TrophyManager
        Z_TrophyManager.getInstance(this);

        //Setting GIFs
        ImageView imgview_leavesGIF = findViewById(R.id.imgview_leavesGIF);
        try {
            Glide.with(this).load(R.drawable.leaves).into(imgview_leavesGIF);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgview_leavesGIF.setOnClickListener(view -> {
            Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(MainMenu.this);
            Random random = new Random();
            int randomValue1 = random.nextInt(21);
            int randomValue2 = random.nextInt(21);
            scoreManager.addItemToQuizScoreList(randomValue1);
            scoreManager.addItemToMatchScoreList(randomValue2);
        });

        //Initialize Music and SFx
        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        Z_SoundManager.isBgon[0] = preferences.getBoolean("isBgon", true);
        Z_SoundManager.isSoundFx[0] = preferences.getBoolean("isSFx", true);

        BackgroundSound();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Z_LessonManager.lesson5isUnlock[0] = sharedPreferences.getBoolean("lesson5", false);
        //Toasty.info(MainMenu.this, String.valueOf(Z_LessonManager.lesson5isUnlock[0]), Toasty.LENGTH_SHORT).show();

        ImageView MainTitle = findViewById(R.id.main_title);
        MainTitle.setOnClickListener(v -> {
            Intent Mainmenu = new Intent(getApplicationContext(), NewUI_Dashboard.class);
            startActivity(Mainmenu);
        });



        //Play button
        ImgbtnDrills = findViewById(R.id.imgbtn_play);
        ImgbtnDrills.setOnClickListener(v -> {
            disableMainMenuButtons();
            ClickSoundEffect();
            animateButton(ImgbtnDrills);
            handler.postDelayed(() -> {
                Intent MainMenu = new Intent(getApplicationContext(), Modes.class);
                startActivity(MainMenu);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //MainMenu.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //MainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }, 500);
            //BACKGROUND MUSIC
            Z_SoundManager.setActivityModesPaused(false);
        });

        //Chart button
        ImgbtnChart = findViewById(R.id.imgbtn_chart);
        ImgbtnChart.setOnClickListener(v -> {
            disableMainMenuButtons();
            ClickSoundEffect();
            animateButton(ImgbtnChart);
            handler.postDelayed(() -> {
                Intent MainMenu = new Intent(getApplicationContext(), Chart_Chapters.class);
                startActivity(MainMenu);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //MainMenu.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //MainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }, 500);

        });

        //Guide button
        ImgbtnGuide = findViewById(R.id.imgbtn_guide);
        ImgbtnGuide.setOnClickListener(v -> {
            disableMainMenuButtons();
            ClickSoundEffect();
            animateButton(ImgbtnGuide);
            handler.postDelayed(this::GuideMenu, 500);

        });

        //Tool button
        ImgbtnTool = findViewById(R.id.imgbtn_tool);
        ImgbtnTool.setOnClickListener(v -> {
            disableMainMenuButtons();
            ClickSoundEffect();
            animateButton(ImgbtnTool);
            handler.postDelayed(() -> {
                Intent MainMenu = new Intent(getApplicationContext(), More.class);
                startActivity(MainMenu);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //MainMenu.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //MainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }, 500);

        });

        ImgbtnMainQuit = findViewById(R.id.imgbtn_mainquit);
        ImgbtnMainQuit.setOnClickListener(v -> {
            ClickSoundEffect();

            Dialog dlg = new Dialog(MainMenu.this, R.style.PopupDialog);
            dlg.setCanceledOnTouchOutside(false);
            dlg.setContentView(R.layout.activity_exit_menu);
            dlg.show();

            View dialogWindowView = dlg.getWindow().getDecorView();
            Z_Dialogs_Animation.applyBounceAnimation(dialogWindowView);

            ImageButton BtnYesExit = dlg.findViewById(R.id.imgbtn_yes_exit);
            BtnYesExit.setOnClickListener(x -> {
                Z_SoundManager.StopMainMenu_ModesBackgroundMusic();
                dlg.dismiss();
                ClickSoundEffect();
                finish();
            });

            ImageButton BtnNoExit = dlg.findViewById(R.id.imgbtn_no_exit);
            BtnNoExit.setOnClickListener(x -> {
                enableMainMenuButtons();
                ClickSoundEffect();
                dlg.dismiss();
            });
        });

        //Dialog for Sound Menu and saving state
        ImgbtnSettings = findViewById(R.id.imgbtn_settings);
        ImgbtnSettings.setOnClickListener(v -> {

            try {
                disableMainMenuButtons();
                ImgbtnSettings.setVisibility(View.INVISIBLE);
                ImgbtnMainQuit.setVisibility(View.INVISIBLE);
                ClickSoundEffect();
                ImgbtnSettings.setEnabled(false);

                // Create a fade out animation
                Animation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
                fadeOutAnimation.setDuration(300); // Adjust the duration as needed
                fadeOutAnimation.setFillAfter(true); // Keep the button invisible after the animation

                ImgbtnGuide.startAnimation(fadeOutAnimation);
                ImgbtnDrills.startAnimation(fadeOutAnimation);
                ImgbtnTool.startAnimation(fadeOutAnimation);
                ImgbtnChart.startAnimation(fadeOutAnimation);

                Dialog dlg;
                dlg = new Dialog(MainMenu.this, R.style.PopupDialog);
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
                    enableMainMenuButtons();
                    ClickSoundEffect();
                    //ImgbtnSettings.setEnabled(true);
                    dlg.dismiss();
                    ImgbtnSettings.setEnabled(false);
                    ImgbtnMainQuit.setEnabled(false);
                    ImgbtnSettings.setVisibility(View.VISIBLE);
                    ImgbtnMainQuit.setVisibility(View.VISIBLE);
                    handler.postDelayed(() -> {
                        ImgbtnSettings.setEnabled(true);
                        ImgbtnMainQuit.setEnabled(true);
                    }, 800);
                    ExitGuideMenu(); //for buttons animation
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


    public void disableMainMenuButtons(){
        ImgbtnDrills.setEnabled(false);
        ImgbtnChart.setEnabled(false);
        ImgbtnGuide.setEnabled(false);
        ImgbtnTool.setEnabled(false);
        ImgbtnMainQuit.setEnabled(false);
        ImgbtnSettings.setEnabled(false);
    }

    public void enableMainMenuButtons(){
        ImgbtnDrills.setEnabled(true);
        ImgbtnChart.setEnabled(true);
        ImgbtnGuide.setEnabled(true);
        ImgbtnTool.setEnabled(true);
        ImgbtnMainQuit.setEnabled(true);
        ImgbtnSettings.setEnabled(true);
    }


    // Call the RegButtonClickSound method from Z_SoundManager
    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }

//        if (isSoundFx[0]) {
//            soundFX = MediaPlayer.create(MainMenu.this, R.raw.button_click);
//            soundFX.start();
//            soundFX.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    mp.release();
//                }
//            });
//        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        cancelToast();
//        Z_SoundManager soundManager = new Z_SoundManager();
//        soundManager.StopMainMenu_ModesBackgroundMusic();
//        if (songMain != null && songMain.isPlaying()) {
//            songMain.pause();
//        }
        Z_SoundManager.setActivityMainMenuPaused(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BackgroundSound();
    }

    void BackgroundSound() {
        Z_SoundManager.setActivityMainMenuResumed(this);
        Z_SoundManager.setActivityModesPaused(true);
    }


    public void GuideMenu() {
        try {
            // Create a fade out animation
        Animation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeOutAnimation.setDuration(300); // Adjust the duration as needed
        fadeOutAnimation.setFillAfter(true); // Keep the button invisible after the animation

        ImgbtnGuide.startAnimation(fadeOutAnimation);
        ImgbtnDrills.startAnimation(fadeOutAnimation);
        ImgbtnTool.startAnimation(fadeOutAnimation);
        ImgbtnChart.startAnimation(fadeOutAnimation);

//            ImgbtnGuide.setVisibility(View.INVISIBLE);
//            ImgbtnDrills.setVisibility(View.INVISIBLE);
//            ImgbtnTool.setVisibility(View.INVISIBLE);
//            ImgbtnChart.setVisibility(View.INVISIBLE);

            final Dialog dlg = new Dialog(MainMenu.this, R.style.PopupDialog);
            dlg.setCanceledOnTouchOutside(false);
            dlg.setContentView(R.layout.activity_guide_menu);
            dlg.show();

            //Prevents back press on sound dialog menu
            dlg.setOnKeyListener((dialog, keyCode, event) -> {
                return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP; // Consume the back button press event
            });

            TextView TvHistory = dlg.findViewById(R.id.tv_history);
            TextView TvLessons = dlg.findViewById(R.id.tv_lessons);
            TextView TvLibrary = dlg.findViewById(R.id.tv_library);

            ImgbtnHistory = dlg.findViewById(R.id.img_history);
            ImgbtnHistory.setOnClickListener(v -> {
                cancelToast();
                animateButton(ImgbtnHistory);
                animateButton(TvHistory);
                ImgbtnHistory.setEnabled(false);
                //disableGuideMenuButtons();
                handler.postDelayed(() -> {
                    Intent Mainmenu = new Intent(getApplicationContext(), History.class);
                    startActivity(Mainmenu);
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //Mainmenu.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    //Mainmenu.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    dlg.dismiss();
                }, 500);
            });

            ImgbtnLessons = dlg.findViewById(R.id.img_lessons);
            ImgbtnLessons.setOnClickListener(v -> {
                cancelToast();
                animateButton(ImgbtnLessons);
                animateButton(TvLessons);
                ImgbtnLessons.setEnabled(false);
                handler.postDelayed(() -> {
                    Intent Mainmenu = new Intent(getApplicationContext(), Lessons.class);
                    startActivity(Mainmenu);
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //Mainmenu.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    //Mainmenu.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    dlg.dismiss();
                }, 500);

            });

            ImgbtnLibrary = dlg.findViewById(R.id.img_library);
            if (Z_LessonManager.lesson5isUnlock[0]) {
                ImgbtnLibrary.setImageResource(R.drawable.guide_librarybutton_unlocked);
            } else {
                ImgbtnLibrary.setImageResource(R.drawable.guide_librarybutton_locked);
            }

            ImgbtnLibrary.setOnClickListener(v -> {
                cancelToast();
                animateButton(ImgbtnLibrary);
                animateButton(TvLibrary);
                //disableGuideMenuButtons();
                if(!Z_LessonManager.lesson5isUnlock[0]){
                    globalToast = Toasty.error(MainMenu.this, "Unlock Lesson 5: Reading, first!", Toast.LENGTH_SHORT);
                    globalToast.show();
                }else{
                    handler.postDelayed(() -> {
                        ImgbtnLibrary.setEnabled(false);
                        Intent Mainmenu = new Intent(getApplicationContext(), Library.class);
                        startActivity(Mainmenu);
                        finish();
                        dlg.dismiss();
                    }, 500);
                }
            });

            ImageButton imgbtnGuideExit = dlg.findViewById(R.id.imgbtn_guideexit);
            imgbtnGuideExit.setOnClickListener(v -> {
                cancelToast();
                enableMainMenuButtons();
                dlg.dismiss();
                ExitGuideMenu();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    public void disableGuideMenuButtons(){
//        ImgbtnHistory.setEnabled(false);
//        ImgbtnLessons.setEnabled(false);
//        ImgbtnLibrary.setEnabled(false);
//        ImgbtnGuideExit.setEnabled(false);
//    }



    //When the Exit button is clicked
    public void ExitGuideMenu() {
        // Start the button click animations one after the other with a delay
        try {
            animateButtonSequentially(ImgbtnDrills, 0);
            animateButtonSequentially(ImgbtnChart, 100);
            animateButtonSequentially(ImgbtnGuide, 200);
            animateButtonSequentially(ImgbtnTool, 300);

            // Start the fade-in animations one after the other
            animateFadeInSequentially(ImgbtnDrills, 0);
            animateFadeInSequentially(ImgbtnChart, 100);
            animateFadeInSequentially(ImgbtnGuide, 200);
            animateFadeInSequentially(ImgbtnTool, 300);

            ImgbtnGuide.setEnabled(true);
            ImgbtnDrills.setEnabled(true);
            ImgbtnTool.setEnabled(true);
            ImgbtnChart.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ImgbtnGuide.setVisibility(View.VISIBLE);
//        ImgbtnDrills.setVisibility(View.VISIBLE);
//        ImgbtnTool.setVisibility(View.VISIBLE);
//        ImgbtnChart.setVisibility(View.VISIBLE);
    }


    //Animate fade-in Main menu buttons one by one
    private void animateFadeInSequentially(final View view, int delay) {
        new Handler().postDelayed(() -> {
            Animation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
            fadeInAnimation.setDuration(300); // Adjust the duration as needed
            fadeInAnimation.setFillAfter(true); // Keep the button visible after the animation

            view.startAnimation(fadeInAnimation);
        }, delay);
    }


    //Click animate Main menu buttons one by one
    private void animateButtonSequentially(final View view, int delay) {
        new Handler().postDelayed(() -> animateButton(view), delay);
    }


    // Method to animate the button click
    private void animateButton(View view) {
        // Create a scale animator to shrink the button
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 0.7f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 0.7f)
        );
        shrinkAnimator.setDuration(200); // Set the duration of the shrink animation

        // Create a scale animator to restore the button to its original size
        ObjectAnimator restoreAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.7f, 1.0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.7f, 1.0f)
        );
        restoreAnimator.setDuration(300); // Set the duration of the restore animation

        // Set up the animator set to play the shrink and restore animations sequentially
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(shrinkAnimator, restoreAnimator);

        // Start the button click animation
        animatorSet.start();
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
        ImgbtnMainQuit.performClick();
    }

}
