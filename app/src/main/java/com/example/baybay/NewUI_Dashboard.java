package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class NewUI_Dashboard extends AppCompatActivity {

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

    ImageButton ImgbtnDashboardMenu, ImgbtnTrivia_Refresh, ImgbtnLearn, PlayGames, Community, ArtsCrafts;
    TextView TvTrivia;
    TextView TvTextDashboard;
    private String currentText = "";
    SharedPreferences preferences;
    String versionName;
    String link;
    private TextView DLLinkQuestion;
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

        TvTrivia = findViewById(R.id.tv_trivia);
        ImgbtnTrivia_Refresh = findViewById(R.id.imgbtn_trivia_refresh);

        ImgbtnTrivia_Refresh.setEnabled(false);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ImgbtnTrivia_Refresh.setEnabled(true);
        }, 2000);

        setRandomTextWithAnimation(TvTrivia);

        ImgbtnTrivia_Refresh.setOnClickListener(v -> {
            animateButtonTrivia(ImgbtnTrivia_Refresh);
            setRandomTextWithAnimation(TvTrivia);
            ClickSoundEffect();
            ImgbtnTrivia_Refresh.setEnabled(false);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                ImgbtnTrivia_Refresh.setEnabled(true);
            }, 2000);
        });

        ImgbtnDashboardMenu = findViewById(R.id.imgbtn_dashboard_menu);
        ImgbtnDashboardMenu.setOnClickListener(v -> {
            try {
                ClickSoundEffect();
                ImgbtnDashboardMenu.setEnabled(false);
                openNewDialogSettings();

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    ImgbtnDashboardMenu.setEnabled(true);
                }, 1000);

//                ImgbtnDashboardMenu.setEnabled(false);
//
//                Dialog dlg;
//                dlg = new Dialog(NewUI_Dashboard.this, R.style.PopupDialog);
//                dlg.setCanceledOnTouchOutside(false);  //disable dialog dismiss when touch outside
//                dlg.setContentView(R.layout.activity_new_ui_sound);
//                dlg.show();
//
//                View dialogWindowView = dlg.getWindow().getDecorView();
//                Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);
//
//                // Access the button from the dialog's content view
//                ImageButton ImgBtnSoundBg = dlg.findViewById(R.id.cb_Background);
//                ImageButton ImgBtnSoundFx = dlg.findViewById(R.id.cb_SEffects);
//                ImageButton ImgbtnSoundExit = dlg.findViewById(R.id.imgbtn_sound_exit);
//
//                //Initialize buttons icon when dialog is opened
//                if (Z_SoundManager.isBgon[0]) {
//                    ImgBtnSoundBg.setImageResource(R.drawable.cb_soundon);
//                } else {
//                    ImgBtnSoundBg.setImageResource(R.drawable.cb_soundoff);
//                }
//
//                if (Z_SoundManager.isSoundFx[0]) {
//                    ImgBtnSoundFx.setImageResource(R.drawable.cb_soundon);
//                } else {
//                    ImgBtnSoundFx.setImageResource(R.drawable.cb_soundoff);
//                }
//
//                ImgBtnSoundBg.setOnClickListener(v1 -> {
//                    ClickSoundEffect();
//                    Z_SoundManager.isBgon[0] = !Z_SoundManager.isBgon[0];  // Toggle the value of isBgon
//
//                    onStop();
//
//                    // Save Background Music state
//                    SharedPreferences.Editor editorBg = preferences.edit();
//                    editorBg.putBoolean("isBgon", Z_SoundManager.isBgon[0]);
//                    editorBg.apply();
//
//                    // Initialize the background music toggle button drawable
//                    if (Z_SoundManager.isBgon[0]) {
//                        ImgBtnSoundBg.setImageResource(R.drawable.cb_soundon);
//                    } else {
//                        ImgBtnSoundBg.setImageResource(R.drawable.cb_soundoff);
//                    }
//
//                    callMusic();
//                });
//
//                ImgBtnSoundFx.setOnClickListener(v12 -> {
//                    ClickSoundEffect();
//                    Z_SoundManager.isSoundFx[0] = !Z_SoundManager.isSoundFx[0];  // Toggle the value of isSFx
//
//                    // Save Background Music state
//                    SharedPreferences.Editor editorSFXicon = preferences.edit();
//                    editorSFXicon.putBoolean("isSFx", Z_SoundManager.isSoundFx[0]);
//                    editorSFXicon.apply();
//
//                    // Initialize the background music toggle button drawable
//                    if (Z_SoundManager.isSoundFx[0]) {
//                        ImgBtnSoundFx.setImageResource(R.drawable.cb_soundon);
//                    } else {
//                        ImgBtnSoundFx.setImageResource(R.drawable.cb_soundoff);
//                    }
//                });
//
//                ImgbtnSoundExit.setOnClickListener(v13 -> {
//                    ClickSoundEffect();
//                    ImgbtnDashboardMenu.setEnabled(true);
//                    dlg.dismiss();
//                });
//
//                //Prevents back press on sound dialog menu
//                dlg.setOnKeyListener((dialog, keyCode, event) -> {
//                    return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP; // Consume the back button press event
//                });
//
//                //Set app version
//                TextView TvAppVersion = dlg.findViewById(R.id.tv_appversion);
//                versionName = getAppVersionName();
//                TvAppVersion.setText("Version: " + versionName);
//
//                TextView TvCheckUpdate = dlg.findViewById(R.id.tv_checkupdate);
//                TvCheckUpdate.setOnClickListener(v1 -> {
//                    link = "https://baybay-release-web-app.pages.dev/";
//                    gotoLink(link);
//                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ImgbtnLearn = findViewById(R.id.imgbtn_learn);
        ImgbtnLearn.setOnClickListener(v -> {
            DisableNav();
            ClickSoundEffect();
            animateButton(ImgbtnLearn);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Dashboard = new Intent(getApplicationContext(), NewUI_Learn.class);
                startActivity(Dashboard);
            }, 500);
        });

        PlayGames = findViewById(R.id.imgbtn_playgames);
        PlayGames.setOnClickListener(v -> {
            DisableNav();
            ClickSoundEffect();
            animateButton(PlayGames);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Dashboard = new Intent(getApplicationContext(), NewUI_Gamemodes.class);
                startActivity(Dashboard);
            }, 500);
        });

        ArtsCrafts = findViewById(R.id.imgbtn_artcrafts);
        ArtsCrafts.setOnClickListener(v -> {
            DisableNav();
            animateButton(ArtsCrafts);
            ClickSoundEffect();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Dashboard = new Intent(getApplicationContext(), NewUI_ArtsCrafts.class);
                startActivity(Dashboard);
            }, 500);
        });

        Community = findViewById(R.id.imgbtn_community);
        Community.setOnClickListener(v -> {
            DisableNav();
            animateButton(Community);
            ClickSoundEffect();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                EnableNav();
                Intent Dashboard = new Intent(getApplicationContext(), NewUI_Community.class);
                startActivity(Dashboard);
            }, 500);
        });
    }

    private void DisableNav(){
        ImgbtnLearn.setEnabled(false);
        PlayGames.setEnabled(false);
        ArtsCrafts.setEnabled(false);
        Community.setEnabled(false);
    }

    private void EnableNav(){
        ImgbtnLearn.setEnabled(true);
        PlayGames.setEnabled(true);
        ArtsCrafts.setEnabled(true);
        Community.setEnabled(true);
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

    private String getAppVersionName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    void gotoLink(String l){
        onStop();
        try{
            Uri uri = Uri.parse(l);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }catch (Exception e){
        }
    }

    private void applyFadeInAnimation(TextView textView) {
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(200); // Adjust the duration as needed
        textView.setAnimation(fadeIn);
    }

    // Method to animate refresh trivia
    private void animateButtonTrivia(View view) {
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

    public void openNewDialogSettings(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        // Access the button from the dialog's content view
        ImageButton ImgBtnSoundBg = dialog.findViewById(R.id.cb_BackgroundNew);
        ImageButton ImgBtnSoundFx = dialog.findViewById(R.id.cb_SEffectsNew);

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

            onStop();

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

            callMusic();
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

        //Set app version
        TextView TvAppVersion = dialog.findViewById(R.id.tv_appversion2);
        versionName = getAppVersionName();
        TvAppVersion.setText("App Version: " + versionName);

        ImageButton BtnCheckUpdate = dialog.findViewById(R.id.btn_checkupdate);
        BtnCheckUpdate.setOnClickListener(v -> {
            link = "https://baybay-release-web-app.pages.dev/";
            gotoLink(link);
        });

        //Prevents back press on sound dialog menu
        dialog.setOnKeyListener((dialogInterface, keyCode, event) -> {
            //return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP; // Consume the back button press event
            dialog.dismiss();
            ImgbtnDashboardMenu.setEnabled(true);
            return true;
        });

        //Dismiss dialog when view is clicked
        ConstraintLayout ConstraintLayoutSettings = dialog.findViewById(R.id.constarintlayout_newsettings);
        ConstraintLayoutSettings.setOnClickListener(v -> {
            dialog.dismiss();
            ImgbtnDashboardMenu.setEnabled(true);
        });

        //Strikes
        TextView TvCuriousCritters = dialog.findViewById(R.id.tv_curiouscritters);
        TvCuriousCritters.setPaintFlags(TvCuriousCritters.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        TextView TvAlice = dialog.findViewById(R.id.tv_alice);
        TvAlice.setPaintFlags(TvAlice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onBackPressed() {
        Dialog dlg = new Dialog(NewUI_Dashboard.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_newui_download_link_prompt);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        DLLinkQuestion = dlg.findViewById(R.id.tv_dl_link_question);
        DLLinkQuestion.setText("Are you sure you want to exit the application?");
        DLLinkQuestion.setTextSize(19);

        ImageButton BtnYesExit = dlg.findViewById(R.id.imgbtn_yes_exit);
        BtnYesExit.setOnClickListener(v -> {
            dlg.dismiss();
            finish();
        });

        ImageButton BtnNoExit = dlg.findViewById(R.id.imgbtn_no_exit);
        BtnNoExit.setOnClickListener(v -> {
            dlg.dismiss();
        });
    }
}