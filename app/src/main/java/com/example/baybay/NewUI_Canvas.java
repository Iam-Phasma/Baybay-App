package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mrudultora.colorpicker.ColorPickerDialog;
import com.mrudultora.colorpicker.listeners.OnSelectColorListener;
import com.mrudultora.colorpicker.util.ColorItemShape;

import java.util.ArrayList;
import java.util.Arrays;

public class NewUI_Canvas extends AppCompatActivity {
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

    private Toast globalToast;
    Button ColorPicker;
    private ColorPickerDialog colorPickerDialog;
    ImageButton Canvas_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_canvas);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        //Theme_Color.init(this);
        setBackgroundColor();





        ColorPicker = findViewById(R.id.btn_color_picker);
        ColorPicker.setOnClickListener(v -> ColorPicker());

        Canvas_exit = findViewById(R.id.imgbtn_canvas_exit);
        Canvas_exit.setOnClickListener(v -> {
            ClickSoundEffect();
            cancelToast();
            finish();
        });

    }

    private void ColorPicker() {
        colorPickerDialog = new ColorPickerDialog(new ContextThemeWrapper(this, R.style.MoreActivityTheme));
        colorPickerDialog.setDialogTitle("Select a color");
        colorPickerDialog.setColors(new ArrayList<>(Arrays.asList(
                        "#FFC1C1", "#FFD1A1", "#FFFFB3", "#B3FFB3", "#B3D9FF",
                        "#EE7272", "#FCB274", "#F9DC5C", "#8AF0BF", "#8093F1",
                        "#C2A3FF", "#E6B3FF", "#EBDEBE", "#FFFFFF", "#A6A6A6",
                        "#9C8FE5", "#B795E4", "#AA7A79", "#0A0A0A", "#D8B5BE")))
                .setColumns(5)
                .setDefaultSelectedColor(0xFFFFA500)
                .setColorItemShape(ColorItemShape.SQUARE)
                .setColorItemDimenInDp(38)
                .setOnSelectColorListener(new OnSelectColorListener() {
                    @Override
                    public void onColorSelected(int color, int position) {
                        if (color == Color.WHITE) {
                            colorPickerDialog.setTickColor(Color.BLACK);
                        } else {
                            colorPickerDialog.setTickColor(Color.WHITE);
                        }
                    }

                    @Override
                    public void cancel() {
                        colorPickerDialog.dismissDialog();
                    }
                });

        if (!isFinishing() && !isDestroyed()) {
            colorPickerDialog.show();
        }
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

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    private void cancelToast() {

        if (globalToast != null) {
            globalToast.cancel();
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
    protected void onDestroy() {
        super.onDestroy();
        if (colorPickerDialog != null) {
            colorPickerDialog.dismissDialog();
            colorPickerDialog = null;
        }
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