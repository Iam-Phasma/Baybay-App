package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class NewUI_Community extends AppCompatActivity {

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
        int singleColor = Color.parseColor("#FCF4E7");

        // Create the custom GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{singleColor, singleColor});

        // Set the gradient heights
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setGradientCenter(0, 0);
        gradientDrawable.setBounds(0, 0, getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

        // Set the custom GradientDrawable as the window background
        getWindow().setBackgroundDrawable(gradientDrawable);





        ImgbtnCommunityExit = findViewById(R.id.imgbtn_community_exit);
        ImgbtnCommunityExit.setOnClickListener(v -> {
            ClickSoundEffect();
            finish();
        });

        FB1 = findViewById(R.id.imgview_fb1_board);
        FB1.setOnClickListener(v -> {
            link = "https://www.facebook.com/groups/Baybayin.PhilippineNationalWritingSystem/";
            ProceedToLink();
        });

        FB2 = findViewById(R.id.imgview_fb2_board);
        FB2.setOnClickListener(v -> {
            link = "https://www.facebook.com/groups/164170853782827/";
            ProceedToLink();
        });

        FB3 = findViewById(R.id.imgview_fb3_board);
        FB3.setOnClickListener(v -> {
            link = "https://www.facebook.com/groups/647665382468293/";
            ProceedToLink();
        });


        Reddit1 = findViewById(R.id.imgview_reddit1_board);
        Reddit1.setOnClickListener(v -> {
            link = "https://www.reddit.com/r/baybayin_script/new/";
            ProceedToLink();
        });

        Workshop1 = findViewById(R.id.imgview_workshop1_board);
        Workshop1.setOnClickListener(v -> {
            link = "https://www.baybayin.com/baybayin-live.html";
            ProceedToLink();
        });
    }


    private void ProceedToLink(){
        Dialog dlg = new Dialog(NewUI_Community.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_newui_download_link_prompt);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

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
    }

    //DIRECT TO LINKS (SHOPS AND VIDEOS)
    void gotoLink(String l){
        try{
            Uri uri = Uri.parse(l);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }catch (Exception e){
        }
    }

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }
}