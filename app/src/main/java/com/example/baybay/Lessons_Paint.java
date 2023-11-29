package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import es.dmoral.toasty.Toasty;

public class Lessons_Paint extends AppCompatActivity {

    private Z_DrawingView drawingView;
    private ImageButton btnClear;
    public int gifcount = 1;
    boolean isAutoErase = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_paint);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Get the root view of the layout
        View rootView = getWindow().getDecorView().getRootView();

        // Set the background color to white
        rootView.setBackgroundColor(getResources().getColor(android.R.color.white));

        Toasty.info(Lessons_Paint.this, "Follow the strokes above. Use the empty space to draw.", Toasty.LENGTH_LONG).show();

        //showPaintToast();

        drawingView = findViewById(R.id.drawingView);
        TextView TvGifCount = findViewById(R.id.tv_gifcount);
        TvGifCount.setText(gifcount + " / 20");

        btnClear = findViewById(R.id.btn_draw_clear);
        btnClear.setOnClickListener(v -> {
            animateButton(btnClear);
            EraseSoundEffect();
            drawingView.clearDrawing();
        });

        //Long Press
        btnClear.setOnLongClickListener(v -> {
            eraseButtonRotate(btnClear);
            if (isAutoErase) {
                Toasty.info(Lessons_Paint.this, "Auto erase OFF", Toast.LENGTH_SHORT).show();
                //btnClear.animate().rotation(1);
                btnClear.setImageResource(R.drawable.draw_erasebutton);
            } else {
                Toasty.info(Lessons_Paint.this, "Auto erase ON", Toast.LENGTH_SHORT).show();
                //btnClear.animate().rotation(4);
                btnClear.setImageResource(R.drawable.draw_erasebutton_underline);
            }
            isAutoErase = !isAutoErase; // Toggle the value of isAutoErase
            return true;
        });

        TextView BtnDrawInfo = findViewById(R.id.tv_draw_info);
        BtnDrawInfo.setOnClickListener(v -> {
            showPaintToast();
            animateButton(BtnDrawInfo);
        });

        ImageButton BtnDrawExit = findViewById(R.id.btn_draw_exit);
        BtnDrawExit.setOnClickListener(v -> {
            // Dismiss the Paint Toast if showing
            if (currentPaintToast != null) {
                currentPaintToast.cancel();
            }
            //BACKGROUND MUSIC
            Z_SoundManager.setActivityLessonsPaused(false);

            Intent Draw = new Intent(getApplicationContext(), Lessons.class);
            startActivity(Draw);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            //Draw.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            finish();
        });

        //GIF CONTROLLER
        setGIF();

        ImageButton BtnLeft = findViewById(R.id.btn_draw_left);
        BtnLeft.setOnClickListener(v -> {
            animateButton(BtnLeft);
            autoErase();
            if(gifcount != 1){
                gifcount--;
            } else {
                gifcount = 20;
            }
            TvGifCount.setText(gifcount + " / 20");
            setGIF();
        });

        ImageButton BtnRight = findViewById(R.id.btn_draw_right);
        BtnRight.setOnClickListener(v -> {
            animateButton(BtnRight);
            autoErase();
            if(gifcount != 20){
                gifcount++;
            }else {
                gifcount = 1;
            }
            TvGifCount.setText(gifcount + " / 20");
            setGIF();
        });

        ImageButton BtnCharacters = findViewById(R.id.btn_draw_characters);
        BtnCharacters.setOnClickListener(v -> {
            animateButton(BtnCharacters);
            ClickSoundEffect();
            CharactersButtons();
        });
    }

    public void showPaintToast(){
        //Show Paint Toast
        paintToast();
    }

    //AUTO ERASE
    public void autoErase(){
        if (isAutoErase){
            drawingView.clearDrawing();
        }
    }


    private void eraseButtonRotate(View view) {
        view.animate()
                .scaleX(1.2f) // Example scale factor
                .scaleY(1.2f) // Example scale factor
                .setDuration(300) // Example duration in milliseconds
                .withEndAction(() -> view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(300)
                        .start())
                .start();
    }


    //SET GIF ILLUSTRATION
    @SuppressLint("SetTextI18n")
    public void setGIF(){
        ImageView ImgviewDrawIllustration = findViewById(R.id.imgview_draw_illustrattion);
        TextView TvGifLatin = findViewById(R.id.tv_gif_latin);

        TextView TvGifCount = findViewById(R.id.tv_gifcount);
        TvGifCount.setText(gifcount + " / 20");
        switch(gifcount) {
            case 1:
                Glide.with(this).load(R.drawable.draw_a).into(ImgviewDrawIllustration);
                TvGifLatin.setText("A");
                break;
            case 2:
                Glide.with(this).load(R.drawable.draw_b).into(ImgviewDrawIllustration);
                TvGifLatin.setText("BA");
                break;
            case 3:
                Glide.with(this).load(R.drawable.draw_k).into(ImgviewDrawIllustration);
                TvGifLatin.setText("KA");
                break;
            case 4:
                Glide.with(this).load(R.drawable.draw_d).into(ImgviewDrawIllustration);
                TvGifLatin.setText("DA");
                break;
            case 5:
                Glide.with(this).load(R.drawable.draw_e).into(ImgviewDrawIllustration);
                TvGifLatin.setText("E");
                break;
            case 6:
                Glide.with(this).load(R.drawable.draw_g).into(ImgviewDrawIllustration);
                TvGifLatin.setText("GA");
                break;
            case 7:
                Glide.with(this).load(R.drawable.draw_h).into(ImgviewDrawIllustration);
                TvGifLatin.setText("HA");
                break;
            case 8:
                Glide.with(this).load(R.drawable.draw_i).into(ImgviewDrawIllustration);
                TvGifLatin.setText("I");
                break;
            case 9:
                Glide.with(this).load(R.drawable.draw_l).into(ImgviewDrawIllustration);
                TvGifLatin.setText("LA");
                break;
            case 10:
                Glide.with(this).load(R.drawable.draw_m).into(ImgviewDrawIllustration);
                TvGifLatin.setText("MA");
                break;
            case 11:
                Glide.with(this).load(R.drawable.draw_n).into(ImgviewDrawIllustration);
                TvGifLatin.setText("NA");
                break;
            case 12:
                Glide.with(this).load(R.drawable.draw_ng).into(ImgviewDrawIllustration);
                TvGifLatin.setText("NGA");
                break;
            case 13:
                Glide.with(this).load(R.drawable.draw_o).into(ImgviewDrawIllustration);
                TvGifLatin.setText("O");
                break;
            case 14:
                Glide.with(this).load(R.drawable.draw_p).into(ImgviewDrawIllustration);
                TvGifLatin.setText("PA");
                break;
            case 15:
                Glide.with(this).load(R.drawable.draw_r).into(ImgviewDrawIllustration);
                TvGifLatin.setText("RA");
                break;
            case 16:
                Glide.with(this).load(R.drawable.draw_s).into(ImgviewDrawIllustration);
                TvGifLatin.setText("SA");
                break;
            case 17:
                Glide.with(this).load(R.drawable.draw_t).into(ImgviewDrawIllustration);
                TvGifLatin.setText("TA");
                break;
            case 18:
                Glide.with(this).load(R.drawable.draw_u).into(ImgviewDrawIllustration);
                TvGifLatin.setText("U");
                break;
            case 19:
                Glide.with(this).load(R.drawable.draw_w).into(ImgviewDrawIllustration);
                TvGifLatin.setText("WA");
                break;
            case 20:
                Glide.with(this).load(R.drawable.draw_y).into(ImgviewDrawIllustration);
                TvGifLatin.setText("YA");
                break;
            default:
                // code block
        }
    }


    public void CharactersButtons(){
        Dialog dlg = new Dialog(Lessons_Paint.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(R.layout.activity_paint_characterbuttons);
        dlg.show();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.paint_dialog_slide_up);
        dlg.getWindow().getDecorView().startAnimation(animation);

        dlg.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP);

        int[] buttonIds = {
                R.id.btn_draw_a, R.id.btn_draw_b, R.id.btn_draw_k, R.id.btn_draw_d,
                R.id.btn_draw_e, R.id.btn_draw_g, R.id.btn_draw_h, R.id.btn_draw_i,
                R.id.btn_draw_l, R.id.btn_draw_m, R.id.btn_draw_n, R.id.btn_draw_ng,
                R.id.btn_draw_o, R.id.btn_draw_p, R.id.btn_draw_r, R.id.btn_draw_s,
                R.id.btn_draw_t, R.id.btn_draw_u, R.id.btn_draw_w, R.id.btn_draw_y
        };

        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton button = dlg.findViewById(buttonIds[i]);
            final int finalI = i + 1; // Adjust index to match gifcount
            button.setOnClickListener(v -> {
                ClickSoundEffect();
                gifcount = finalI;
                setGIF();
                autoErase();
                dlg.dismiss();
            });
        }

        ImageButton btnDrawCharExit = dlg.findViewById(R.id.btn_draw_char_exit);
        btnDrawCharExit.setOnClickListener(v -> dlg.dismiss());
    }


    //Show Paint Toast. This is created so Toast immediately dismissed when activity is paused or destroyed
    private Toast currentPaintToast;
    private void paintToast() {
        if (currentPaintToast != null) {
            currentPaintToast.cancel(); // Cancel any existing Toast
        }
        currentPaintToast = Toasty.info(Lessons_Paint.this, "Long Press Erase Ink button to enable auto erase.", Toast.LENGTH_LONG);
        currentPaintToast.show();
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
    protected void onPause() {
        super.onPause();
        if (currentPaintToast != null) {
            currentPaintToast.cancel();
        }
        Z_SoundManager.setActivityPaintPaused(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Z_SoundManager.setActivityPaintResumed(this);
    }

    @Override
    public void onBackPressed() {
        Toasty.info(Lessons_Paint.this, "Use the dedicated back button.", Toasty.LENGTH_SHORT).show();
    }
}