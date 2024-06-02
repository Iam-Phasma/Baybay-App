package com.example.baybay;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

import es.dmoral.toasty.Toasty;

public class NewUI_Transliterate extends AppCompatActivity {
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
    ImageButton Imgbtn_translit_exit, Imgbtn_translit_share, Imgbtn_translit_delete, Imgbtn_translit_copy, Imgbtn_translit_info, Imgbtn_translit_send;
    TextView  Tv_trascript_prompt;
    String Converted, toConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_transliterate);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();



        // TYPING
        EditText EditText_tagalog = findViewById(R.id.editText_tagalog);
        // RESULT
        TextView TextView_convertedtext = findViewById(R.id.textView_convertedtext);

        TextView TvSelection = findViewById(R.id.tvSelection);
        String[] selections = {"B20+ ◢", "B17 ◢", "B17+ ◢", "B18  ◢"};
        AtomicInteger num = new AtomicInteger(0);
        TvSelection.setText(selections[num.get()]);

        TvSelection.setOnClickListener(v -> {
            TvSelection.setEnabled(false);

            num.set((num.get() + 1) % selections.length);
            TvSelection.setText(selections[num.get()]);

            if (!EditText_tagalog.getText().toString().equals("")) {
                Imgbtn_translit_send.performClick();
            }

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                TvSelection.setEnabled(true);
            }, 200);
        });


        Imgbtn_translit_send = findViewById(R.id.imgbtn_translit_send);
        Imgbtn_translit_send.setOnClickListener(v -> {
            toConvert = EditText_tagalog.getText().toString().trim();
            if (!toConvert.isEmpty()) {
                ClickSoundEffect();

                String mappedText = Z_BaybayinConverter.mapToBaybayinB20Plus(toConvert);
                Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/OpenBaybayin.ttf");
                TextView_convertedtext.setTextSize(30);
                if (num.get() == 0) {
                    mappedText = Z_BaybayinConverter.mapToBaybayinB20Plus(toConvert);
                    customFont = Typeface.createFromAsset(getAssets(), "fonts/OpenBaybayin.ttf");
                    TextView_convertedtext.setTextSize(32);
                } else if (num.get() == 1) {
                    mappedText = Z_BaybayinConverter.mapToBaybayinB17(toConvert);
                    customFont = Typeface.createFromAsset(getAssets(), "fonts/baybay.ttf");
                } else if (num.get() == 2) {
                    mappedText = Z_BaybayinConverter.mapToBaybayinB17Plus(toConvert);
                    customFont = Typeface.createFromAsset(getAssets(), "fonts/baybay.ttf");
                } else if (num.get() == 3) {
                    mappedText = Z_BaybayinConverter.mapToBaybayinB18(toConvert);
                    customFont = Typeface.createFromAsset(getAssets(), "fonts/baybay.ttf");
                }

                try {
                    TextView_convertedtext.setTypeface(customFont);
                    TextView_convertedtext.setText(mappedText);

                    EditText_tagalog.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                //if EditText is empty but the TextView is not
                TextView_convertedtext.setText("");
                TextView_convertedtext.setTextSize(18);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.blinker);
                TextView_convertedtext.setTypeface(typeface);

                cancelToast();
                globalToast = Toasty.warning(NewUI_Transliterate.this, "Field's empty! Type something.", Toast.LENGTH_SHORT);
                globalToast.show();

                EditText_tagalog.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(EditText_tagalog, InputMethodManager.SHOW_IMPLICIT);

                scrollOnTop();
            }
        });

        Imgbtn_translit_share = findViewById(R.id.imgbtn_translit_share);
        Imgbtn_translit_share.setOnClickListener(v -> {
            try {
                if (TextView_convertedtext.length() != 0) {
                    ClickSoundEffect();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, TextView_convertedtext.getText().toString());
                    shareIntent.setType("text/plain");
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                } else {
                    cancelToast();
                    globalToast = Toasty.warning(NewUI_Transliterate.this, "Nothing to share yet.", Toast.LENGTH_SHORT);
                    globalToast.show();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while copying text: " + e.getMessage());
                e.printStackTrace();
            }
        });

        Imgbtn_translit_copy = findViewById(R.id.imgbtn_translit_copy);
        Imgbtn_translit_copy.setOnClickListener(v -> {
            try {
                if (TextView_convertedtext.length() != 0){
                    ClickSoundEffect();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Baybayin", TextView_convertedtext.getText().toString());
                    clipboard.setPrimaryClip(clip);

                    cancelToast();
                    globalToast = Toasty.success(NewUI_Transliterate.this, "Successfully copied into clipboard.", Toast.LENGTH_SHORT);
                    globalToast.show();
                }else{
                    cancelToast();
                    globalToast = Toasty.warning(NewUI_Transliterate.this, "Nothing to copy yet.", Toast.LENGTH_SHORT);
                    globalToast.show();
                }
            }catch (Exception e){

            }
        });

        Imgbtn_translit_delete = findViewById(R.id.imgbtn_translit_delete);
        Imgbtn_translit_delete.setOnClickListener(v -> {
            if ((TextView_convertedtext.length() != 0) || (EditText_tagalog.length() != 0)){
                ClickSoundEffect();
                TextView_convertedtext.setText("");
                TextView_convertedtext.setTextSize(18);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.blinker);
                TextView_convertedtext.setTypeface(typeface);
                EditText_tagalog.setText("");
                Converted = "";

                scrollOnTop();
            }else{
                cancelToast();
                globalToast = Toasty.warning(NewUI_Transliterate.this, "Field's already empty! Type something.", Toast.LENGTH_SHORT);
                globalToast.show();

                EditText_tagalog.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(EditText_tagalog, InputMethodManager.SHOW_IMPLICIT);

                scrollOnTop();
            }
        });

        Imgbtn_translit_info = findViewById(R.id.imgbtn_translit_info);
        Imgbtn_translit_info.setOnClickListener(v -> {
            ClickSoundEffect();
            Imgbtn_translit_info.setEnabled(false);
            transliterateLimitation();
        });

        Imgbtn_translit_exit = findViewById(R.id.imgbtn_translit_exit);
        Imgbtn_translit_exit.setOnClickListener(v -> {
            ClickSoundEffect();
            cancelToast();
            finish();
        });
    }

    public void scrollOnTop(){
        NestedScrollView Sv_transliterate = findViewById(R.id.sv_transliterate);
        ObjectAnimator animScrollToTop = ObjectAnimator.ofInt(Sv_transliterate, "scrollY", 0);
        animScrollToTop.setDuration(200);
        animScrollToTop.start();
    }



    private void transliterateLimitation(){
        Dialog dlg;
        dlg = new Dialog(NewUI_Transliterate.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_newui_trascript_prompt);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        ConstraintLayout Constlayout_transcript_prompt = dlg.findViewById(R.id.constlayout_transcript_prompt);
        Drawable background = Constlayout_transcript_prompt.getBackground();

        if (background instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        } else if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        }

        Tv_trascript_prompt = dlg.findViewById(R.id.tv_trascript_prompt);
        Tv_trascript_prompt.setText(R.string.transcript_limitation);

        dlg.setOnKeyListener((dialogInterface, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                return true;
            }
            return false;
        });
        dlg.setCanceledOnTouchOutside(false);

        ImageButton Imgbtn_transcript_prompt_ok = dlg.findViewById(R.id.imgbtn_transcript_prompt_ok);
        Imgbtn_transcript_prompt_ok.setOnClickListener(v -> {
            Imgbtn_translit_info.setEnabled(true);
            dlg.dismiss();
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

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
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