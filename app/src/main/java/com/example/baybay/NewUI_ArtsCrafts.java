package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class NewUI_ArtsCrafts extends AppCompatActivity {

    private ImageButton ImgbtnArtsCratsExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_arts_crafts);

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




        ImgbtnArtsCratsExit = findViewById(R.id.imgbtn_artscrafts_exit);
        ImgbtnArtsCratsExit.setOnClickListener(v -> {
            ClickSoundEffect();
            finish();
        });

    }


    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }


}