package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import es.dmoral.toasty.Toasty;

public class NewUI_L5_Handwriting extends AppCompatActivity {

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

    private Z_Paint_Manager drawingView;
    private ImageButton btnClear;
    public int currentGIFCount = 1;
    TextView TvDrawingGuideTittle, TvDrawingGuide;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_lessons_paint);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Get the root view of the layout
        View rootView = getWindow().getDecorView().getRootView();

        // Set the background color to white
        rootView.setBackgroundColor(getResources().getColor(android.R.color.white));





        Intent intent = getIntent();
        int setWritingCount = intent.getIntExtra("writing-count", 1);
        currentGIFCount = setWritingCount;

        Toasty.info(NewUI_L5_Handwriting.this, "Follow the strokes above. Use the empty space to draw.", Toasty.LENGTH_LONG).show();

        setGIF();

        drawingView = findViewById(R.id.drawingView);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable roundedCornersDrawable = getResources().getDrawable(R.drawable.rounded_corners);
        drawingView.setBackground(roundedCornersDrawable);

        TextView TvGifCount = findViewById(R.id.tv_gifcount);
        TvGifCount.setText(currentGIFCount + " / 25");

        btnClear = findViewById(R.id.btn_draw_clear);
        btnClear.setOnClickListener(v -> {
            animateButton(btnClear);
            EraseSoundEffect();
            drawingView.clearDrawing();
        });

        ImageButton BtnDrawInfo = findViewById(R.id.imgbtn_draw_info);
        BtnDrawInfo.setOnClickListener(v -> {
            openDrawingGuidePrompt();
        });

        ImageButton BtnDrawExit = findViewById(R.id.btn_draw_exit);
        BtnDrawExit.setOnClickListener(v -> {
            finish();
        });

        ImageButton BtnLeft = findViewById(R.id.btn_draw_left);
        BtnLeft.setOnClickListener(v -> {
            animateButton(BtnLeft);
            drawingView.clearDrawing();
            if(currentGIFCount != 1){
                currentGIFCount--;
            } else {
                currentGIFCount = 25;
            }
            TvGifCount.setText(currentGIFCount + " / 25");
            setGIF();
        });

        ImageButton BtnRight = findViewById(R.id.btn_draw_right);
        BtnRight.setOnClickListener(v -> {
            animateButton(BtnRight);
            drawingView.clearDrawing();
            if(currentGIFCount != 25){
                currentGIFCount++;
            }else {
                currentGIFCount = 1;
            }
            TvGifCount.setText(currentGIFCount + " / 25");
            setGIF();
        });

        ImageButton BtnCharacters = findViewById(R.id.btn_draw_characters);
        BtnCharacters.setOnClickListener(v -> {
            animateButton(BtnCharacters);
            ClickSoundEffect();
            CharactersButtons();
        });
    }

    //SET GIF
    @SuppressLint("SetTextI18n")
    public void setGIF(){
        openDrawingGuidePrompt();

        ImageView ImgviewDrawIllustration = findViewById(R.id.imgview_draw_illustrattion);
        TextView TvGifLatin = findViewById(R.id.tv_gif_latin);

        TextView TvGifCount = findViewById(R.id.tv_gifcount);
        TvGifCount.setText(currentGIFCount + " / 25");
        switch(currentGIFCount) {
            case 1:
                Glide.with(this).load(R.drawable.draw_a).into(ImgviewDrawIllustration);
                TvGifLatin.setText("A");
                break;
            case 2:
                Glide.with(this).load(R.drawable.draw_e).into(ImgviewDrawIllustration);
                TvGifLatin.setText("E");
                break;
            case 3:
                Glide.with(this).load(R.drawable.draw_i).into(ImgviewDrawIllustration);
                TvGifLatin.setText("I");
                break;
            case 4:
                Glide.with(this).load(R.drawable.draw_o).into(ImgviewDrawIllustration);
                TvGifLatin.setText("O");
                break;
            case 5:
                Glide.with(this).load(R.drawable.draw_u).into(ImgviewDrawIllustration);
                TvGifLatin.setText("U");
                break;
            case 6:
                Glide.with(this).load(R.drawable.draw_b).into(ImgviewDrawIllustration);
                TvGifLatin.setText("BA");
                break;
            case 7:
                Glide.with(this).load(R.drawable.draw_k).into(ImgviewDrawIllustration);
                TvGifLatin.setText("KA");
                break;
            case 8:
                Glide.with(this).load(R.drawable.draw_d).into(ImgviewDrawIllustration);
                TvGifLatin.setText("DA");
                break;
            case 9:
                Glide.with(this).load(R.drawable.draw_g).into(ImgviewDrawIllustration);
                TvGifLatin.setText("GA");
                break;
            case 10:
                Glide.with(this).load(R.drawable.draw_h).into(ImgviewDrawIllustration);
                TvGifLatin.setText("HA");
                break;
            case 11:
                Glide.with(this).load(R.drawable.draw_l).into(ImgviewDrawIllustration);
                TvGifLatin.setText("LA");
                break;
            case 12:
                Glide.with(this).load(R.drawable.draw_m).into(ImgviewDrawIllustration);
                TvGifLatin.setText("MA");
                break;
            case 13:
                Glide.with(this).load(R.drawable.draw_n).into(ImgviewDrawIllustration);
                TvGifLatin.setText("NA");
                break;
            case 14:
                Glide.with(this).load(R.drawable.draw_ng).into(ImgviewDrawIllustration);
                TvGifLatin.setText("NGA");
                break;
            case 15:
                Glide.with(this).load(R.drawable.draw_p).into(ImgviewDrawIllustration);
                TvGifLatin.setText("PA");
                break;
            case 16:
                Glide.with(this).load(R.drawable.draw_r).into(ImgviewDrawIllustration);
                TvGifLatin.setText("RA");
                break;
            case 17:
                Glide.with(this).load(R.drawable.draw_s).into(ImgviewDrawIllustration);
                TvGifLatin.setText("SA");
                break;
            case 18:
                Glide.with(this).load(R.drawable.draw_t).into(ImgviewDrawIllustration);
                TvGifLatin.setText("TA");
                break;
            case 19:
                Glide.with(this).load(R.drawable.draw_w).into(ImgviewDrawIllustration);
                TvGifLatin.setText("WA");
                break;
            case 20:
                Glide.with(this).load(R.drawable.draw_y).into(ImgviewDrawIllustration);
                TvGifLatin.setText("YA");
                break;
            case 21:
                Glide.with(this).load(R.drawable.draw_eo).into(ImgviewDrawIllustration);
                TvGifLatin.setText("E/O SOUND");
                break;
            case 22:
                Glide.with(this).load(R.drawable.draw_iu).into(ImgviewDrawIllustration);
                TvGifLatin.setText("I/U SOUND");
                break;
            case 23:
                Glide.with(this).load(R.drawable.draw_pamudpod).into(ImgviewDrawIllustration);
                TvGifLatin.setText("X-MARK");
                break;
            case 24:
                Glide.with(this).load(R.drawable.draw_period).into(ImgviewDrawIllustration);
                TvGifLatin.setText("PERIOD");
                break;
            case 25:
                Glide.with(this).load(R.drawable.draw_comma).into(ImgviewDrawIllustration);
                TvGifLatin.setText("COMMA");
                break;
            default:
                // code block
        }
    }

    public void CharactersButtons(){
        Dialog dlg = new Dialog(NewUI_L5_Handwriting.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(R.layout.activity_paint_characterbuttons);
        dlg.show();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.paint_dialog_slide_up);
        dlg.getWindow().getDecorView().startAnimation(animation);

        dlg.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP);

        int[] buttonIds = {
                R.id.btn_draw_a, R.id.btn_draw_e, R.id.btn_draw_i, R.id.btn_draw_o, R.id.btn_draw_u,
                R.id.btn_draw_b, R.id.btn_draw_k, R.id.btn_draw_d, R.id.btn_draw_g, R.id.btn_draw_h,
                R.id.btn_draw_l, R.id.btn_draw_m, R.id.btn_draw_n, R.id.btn_draw_ng, R.id.btn_draw_p,
                R.id.btn_draw_r, R.id.btn_draw_s, R.id.btn_draw_t,  R.id.btn_draw_w, R.id.btn_draw_y,
                R.id.btn_draw_eo, R.id.btn_draw_iu, R.id.btn_draw_xx,  R.id.btn_draw_period, R.id.btn_draw_comma
        };

        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton button = dlg.findViewById(buttonIds[i]);
            final int finalI = i + 1; // Adjust index to match gif count
            button.setOnClickListener(v -> {
                ClickSoundEffect();
                currentGIFCount = finalI;
                setGIF();
                drawingView.clearDrawing();
                dlg.dismiss();
            });
        }

        ImageButton btnDrawCharExit = dlg.findViewById(R.id.btn_draw_char_exit);
        btnDrawCharExit.setOnClickListener(v -> dlg.dismiss());
    }

    private void openDrawingGuidePrompt() {
        Dialog dlg;
        dlg = new Dialog(NewUI_L5_Handwriting.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_newui_writing_guideprompt);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        LinearLayout Linearlayout_drawinguideprompt = dlg.findViewById(R.id.linearlayout_transcript_prompt);
        Drawable background = Linearlayout_drawinguideprompt.getBackground();

        if (background instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        } else if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        }

        TvDrawingGuideTittle = dlg.findViewById(R.id.tv_drawinguidetittle);
        TvDrawingGuide = dlg.findViewById(R.id.tv_trascript_prompt);

        setTitleAndGuide();

        ImageButton ImgbtnWritingPromptOk = dlg.findViewById(R.id.imgbtn_canvas_prompt_ok);
        ImgbtnWritingPromptOk.setOnClickListener(v -> {
            dlg.dismiss();
        });
    }

    @SuppressLint("SetTextI18n")
    private void setTitleAndGuide(){
        switch (currentGIFCount) {
            case 1:
                TvDrawingGuideTittle.setText("Draw A:");
                TvDrawingGuide.setText("Draw a smooth \"U\" shape-like line with both ends facing away each other. Then add two short lines in the middle crossing the first column of the shape.");
                break;
            case 2:
                TvDrawingGuideTittle.setText("Draw E:");
                TvDrawingGuide.setText("Draw two separate wavy lines. The top line should be a smooth, gentle wave while the bottom one had more pronounced peaks and troughs.");
                break;
            case 3:
                TvDrawingGuideTittle.setText("Draw I:");
                TvDrawingGuide.setText("Draw two separate wavy lines. The top line should be a smooth, gentle wave with a vertical line in the middle, while the bottom one had more pronounced peaks and troughs.");
                break;
            case 4:
                TvDrawingGuideTittle.setText("Draw O:");
                TvDrawingGuide.setText("Draw a smooth \"3\" shape-like line with the bottom one having a larger curve.");
                break;
            case 5:
                TvDrawingGuideTittle.setText("Draw U:");
                TvDrawingGuide.setText("Draw a smooth \"3\" shape-like line with the bottom one having a larger curve. Then draw a small vertical line next to the bigger curve following its curvature.");
                break;
            case 6:
                TvDrawingGuideTittle.setText("Draw BA:");
                TvDrawingGuide.setText("Draw a smooth inverted \"heart-like\" shape without sharp turns or corners.");
                break;
            case 7:
                TvDrawingGuideTittle.setText("Draw KA:");
                TvDrawingGuide.setText("Draw two separate wavy lines with top and bottom having the same structures. Draw a slightly slanted vertical line in the middle connecting the two wavy lines.");
                break;
            case 8:
                TvDrawingGuideTittle.setText("Draw DA:");
                TvDrawingGuide.setText("Draw two separate wavy lines. The top line should be a smooth, gentle wave. Then draw the second line starting from where the first line started going down then horizontal again following the structure of the first line.");
                break;
            case 9:
                TvDrawingGuideTittle.setText("Draw GA:");
                TvDrawingGuide.setText("Draw a smooth \"3\" shape-like line with the bottom one having a larger curve. Add a second line starting from the middle of the top curve going down resembling like a ponytail.");
                break;
            case 10:
                TvDrawingGuideTittle.setText("Draw HA:");
                TvDrawingGuide.setText("Draw a smooth wave line with a pronounced curves, and with both ends facing like its enclosing to itself.");
                break;
            case 11:
                TvDrawingGuideTittle.setText("Draw LA:");
                TvDrawingGuide.setText("Draw a smooth wave line horizontally. Then draw a second line vertically starting from the middle of the top line going down resembling the shape of number \"3\".");
                break;
            case 12:
                TvDrawingGuideTittle.setText("Draw MA:");
                TvDrawingGuide.setText("Draw a smooth \"U\" shape-like line with both ends facing away from each other. Then draw a second horizontal line connecting the two vertical lines in the middle.");
                break;
            case 13:
                TvDrawingGuideTittle.setText("Draw NA:");
                TvDrawingGuide.setText("Draw a smooth inverted \"U\" shape-like line. Then draw a second wavy line in the middle of the shape starting from the top; touching the line, going down.");
                break;
            case 14:
                TvDrawingGuideTittle.setText("Draw NGA:");
                TvDrawingGuide.setText("Draw a smooth vertical \"U\" shape-like line with both ends facing away from each other. The open side should face the left. Then draw a second line from the middle right side of the first line forming a pronounced wave shaped tail.");
                break;
            case 15:
                TvDrawingGuideTittle.setText("Draw PA:");
                TvDrawingGuide.setText("Draw a smooth \"U\" shape-like line with both ends facing away from each other. Then add one short line in the middle crossing the second column of the shape.");
                break;
            case 16:
                TvDrawingGuideTittle.setText("Draw RA:");
                TvDrawingGuide.setText("Draw two separate wavy lines. The top line should be a smooth, gentle wave. Then draw a second line starting from where the first line started going down then horizontal again following the structure of the first line. After that, draw a short vertical strike in the middle of the second line.");
                break;
            case 17:
                TvDrawingGuideTittle.setText("Draw SA:");
                TvDrawingGuide.setText("Draw a smooth \"V\" shape-like line with a smooth bottom corner. Continue the line then draw a number \"3\" shape-like line beside the V line.");
                break;
            case 18:
                TvDrawingGuideTittle.setText("Draw TA:");
                TvDrawingGuide.setText("Draw a horizontal wavy line with a smooth, gentle wave. Then draw a second small line with a \"C\" shape-like line below the first curve of the wavy line.");
                break;
            case 19:
                TvDrawingGuideTittle.setText("Draw WA:");
                TvDrawingGuide.setText("Draw a smooth \"U\" shape-like line with both ends facing the left side.");
                break;
            case 20:
                TvDrawingGuideTittle.setText("Draw YA:");
                TvDrawingGuide.setText("Draw a smooth \"U\" shape-like line with both ends facing away each other.");
                break;
            case 21:
                TvDrawingGuideTittle.setText("Draw E/O SOUND:");
                TvDrawingGuide.setText("Draw a small hollow circle above or below a character.");
                break;
            case 22:
                TvDrawingGuideTittle.setText("Draw I/U SOUND:");
                TvDrawingGuide.setText("Draw a small filled circle above or below a character.");
                break;
            case 23:
                TvDrawingGuideTittle.setText("Draw X-MARK:");
                TvDrawingGuide.setText("Draw an x mark below a character.");
                break;
            case 24:
                TvDrawingGuideTittle.setText("Draw PERIOD:");
                TvDrawingGuide.setText("Draw double slashes after a character or word.");
                break;
            case 25:
                TvDrawingGuideTittle.setText("Draw COMMA:");
                TvDrawingGuide.setText("Draw a single slash after a character or word.");
                break;
            default:
                break;
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

    // Call the RegButtonClickSound method from Z_SoundManager
    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    // Call Erase Sound
    void EraseSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.PaintEraseSound(this);
        }
    }

    @Override
    public void onBackPressed() {
        Toasty.info(NewUI_L5_Handwriting.this, "Use the dedicated back button.", Toasty.LENGTH_SHORT).show();
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