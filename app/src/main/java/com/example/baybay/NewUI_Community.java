package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class NewUI_Community extends AppCompatActivity {

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

    ImageButton ImgbtnCommunityExit;
    private String link = "";
    ImageView FB1, FB2, FB3, Reddit1, Workshop1;
    private TextView DLLinkQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_nuew_ui_community);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();





        ImgbtnCommunityExit = findViewById(R.id.imgbtn_community_exit);
        ImgbtnCommunityExit.setOnClickListener(v -> {
            ClickSoundEffect();
            finish();
        });

        FB1 = findViewById(R.id.imgview_fb1_board);
        FB1.setOnClickListener(v -> {
            DisableNav();
            link = "https://www.facebook.com/groups/Baybayin.PhilippineNationalWritingSystem/";
            ProceedToLink();
        });

        FB2 = findViewById(R.id.imgview_fb2_board);
        FB2.setOnClickListener(v -> {
            DisableNav();
            link = "https://www.facebook.com/groups/164170853782827/";
            ProceedToLink();
        });

        FB3 = findViewById(R.id.imgview_fb3_board);
        FB3.setOnClickListener(v -> {
            DisableNav();
            link = "https://www.facebook.com/groups/647665382468293/";
            ProceedToLink();
        });


        Reddit1 = findViewById(R.id.imgview_reddit1_board);
        Reddit1.setOnClickListener(v -> {
            DisableNav();
            link = "https://www.reddit.com/r/baybayin_script/new/";
            ProceedToLink();
        });

        Workshop1 = findViewById(R.id.imgview_workshop1_board);
        Workshop1.setOnClickListener(v -> {
            DisableNav();
            link = "https://www.baybayin.com/baybayin-live.html";
            ProceedToLink();
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

    private void ProceedToLink(){
        Dialog dlg = new Dialog(NewUI_Community.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_newui_download_link_prompt);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        ConstraintLayout Constlayout_download_link = dlg.findViewById(R.id.constlayout_download_link);
        Drawable background = Constlayout_download_link.getBackground();

        if (background instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        } else if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        }

        DLLinkQuestion = dlg.findViewById(R.id.tv_dl_link_question);
        DLLinkQuestion.setText("Do you want to proceed to the link address?");

        ImageButton BtnYesExit = dlg.findViewById(R.id.imgbtn_yes_exit);
        BtnYesExit.setOnClickListener(v -> {
            dlg.dismiss();
            ClickSoundEffect();
            gotoLink(link);
            link = "";
        });

        ImageButton BtnNoExit = dlg.findViewById(R.id.imgbtn_no_exit);
        BtnNoExit.setOnClickListener(v -> {
            ClickSoundEffect();
            dlg.dismiss();
            link = "";
        });

        EnableNav();
    }

    //DIRECT TO LINKS (SHOPS AND VIDEOS)
    void gotoLink(String l){
        onStop();
        try{
            Uri uri = Uri.parse(l);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }catch (Exception e){
        }
    }

    private void DisableNav(){
        FB1.setEnabled(false);
        FB2.setEnabled(false);
        FB3.setEnabled(false);
        Reddit1.setEnabled(false);
        Workshop1.setEnabled(false);
    }

    private void EnableNav(){
        FB1.setEnabled(true);
        FB2.setEnabled(true);
        FB3.setEnabled(true);
        Reddit1.setEnabled(true);
        Workshop1.setEnabled(true);
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
}