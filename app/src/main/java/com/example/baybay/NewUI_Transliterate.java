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
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageButton Imgbtn_translit_exit, Imgbtn_translit_share, Imgbtn_translit_delete, Imgbtn_translit_copy, Imgbtn_translit_info;
    TextView TextView_convertedtext, Tv_trascript_prompt;
    EditText EditText_tagalog;
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
        // Button
        ImageButton Imgbtn_translit_send = findViewById(R.id.imgbtn_translit_send);
        Imgbtn_translit_send.setOnClickListener(v -> {
            toConvert = EditText_tagalog.getText().toString().trim();
            if (!toConvert.isEmpty()){
                ClickSoundEffect();
                String mappedText = mapToBaybayin(toConvert);
                try {
                    Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/baybay.ttf");
                    TextView_convertedtext.setTypeface(customFont);
                    TextView_convertedtext.setText(mappedText);
                    TextView_convertedtext.setTextSize(30);
                } catch (Exception e) {

                }
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
                    globalToast = Toasty.warning(NewUI_Transliterate.this, "Nothing to share yet.", Toast.LENGTH_LONG);
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
                    globalToast = Toasty.success(NewUI_Transliterate.this, "Successfully copied into clipboard.", Toast.LENGTH_LONG);
                    globalToast.show();
                }else{
                    cancelToast();
                    globalToast = Toasty.warning(NewUI_Transliterate.this, "Nothing to copy yet.", Toast.LENGTH_LONG);
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
                globalToast = Toasty.warning(NewUI_Transliterate.this, "Field's already empty! Type something.", Toast.LENGTH_LONG);
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

    private String mapToBaybayin(String toConvert) {
        StringBuilder mappedText = new StringBuilder();

        toConvert = toConvert.replaceAll("(?i)NGA", "ᜅ");
        toConvert = toConvert.replaceAll("(?i)NGE", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGI", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGO", "ᜅᜓ");
        toConvert = toConvert.replaceAll("(?i)NGU", "ᜅᜓ");

        toConvert = toConvert.replaceAll("(?i)BA", "ᜊ");
        toConvert = toConvert.replaceAll("(?i)BE", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BI", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BO", "ᜊᜓ");
        toConvert = toConvert.replaceAll("(?i)BU", "ᜊᜓ");

        toConvert = toConvert.replaceAll("(?i)KA", "ᜃ");
        toConvert = toConvert.replaceAll("(?i)KE", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KI", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KO", "ᜃᜓ");
        toConvert = toConvert.replaceAll("(?i)KU", "ᜃᜓ");

        toConvert = toConvert.replaceAll("(?i)DA", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)DE", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DI", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DO", "ᜇᜓ");
        toConvert = toConvert.replaceAll("(?i)DU", "ᜇᜓ");

        toConvert = toConvert.replaceAll("(?i)GA", "ᜄ");
        toConvert = toConvert.replaceAll("(?i)GE", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GI", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GO", "ᜄᜓ");
        toConvert = toConvert.replaceAll("(?i)GU", "ᜄᜓ");

        toConvert = toConvert.replaceAll("(?i)HA", "ᜑ");
        toConvert = toConvert.replaceAll("(?i)HE", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HI", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HO", "ᜑᜓ");
        toConvert = toConvert.replaceAll("(?i)HU", "ᜑᜓ");

        toConvert = toConvert.replaceAll("(?i)LA", "ᜎ");
        toConvert = toConvert.replaceAll("(?i)LE", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LI", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LO", "ᜎᜓ");
        toConvert = toConvert.replaceAll("(?i)LU", "ᜎᜓ");

        toConvert = toConvert.replaceAll("(?i)MA", "ᜋ");
        toConvert = toConvert.replaceAll("(?i)ME", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MI", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MO", "ᜋᜓ");
        toConvert = toConvert.replaceAll("(?i)MU", "ᜋᜓ");

        toConvert = toConvert.replaceAll("(?i)NA", "ᜈ");
        toConvert = toConvert.replaceAll("(?i)NE", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NI", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NO", "ᜈᜓ");
        toConvert = toConvert.replaceAll("(?i)NU", "ᜈᜓ");

        toConvert = toConvert.replaceAll("(?i)PA", "ᜉ");
        toConvert = toConvert.replaceAll("(?i)PE", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PI", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PO", "ᜉᜓ");
        toConvert = toConvert.replaceAll("(?i)PU", "ᜉᜓ");

        toConvert = toConvert.replaceAll("(?i)RA", "\u170D");
        toConvert = toConvert.replaceAll("(?i)RE", "\u170Dᜒ");
        toConvert = toConvert.replaceAll("(?i)RI", "\u170Dᜒ");
        toConvert = toConvert.replaceAll("(?i)RO", "\u170Dᜓ");
        toConvert = toConvert.replaceAll("(?i)RU", "\u170Dᜓ");

        toConvert = toConvert.replaceAll("(?i)SA", "ᜐ");
        toConvert = toConvert.replaceAll("(?i)SE", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SI", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SO", "ᜐᜓ");
        toConvert = toConvert.replaceAll("(?i)SU", "ᜐᜓ");

        toConvert = toConvert.replaceAll("(?i)TA", "ᜆ");
        toConvert = toConvert.replaceAll("(?i)TE", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TI", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TO", "ᜆᜓ");
        toConvert = toConvert.replaceAll("(?i)TU", "ᜆᜓ");

        toConvert = toConvert.replaceAll("(?i)WA", "ᜏ");
        toConvert = toConvert.replaceAll("(?i)WE", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WI", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WO", "ᜏᜓ");
        toConvert = toConvert.replaceAll("(?i)WU", "ᜏᜓ");

        toConvert = toConvert.replaceAll("(?i)YA", "ᜌ");
        toConvert = toConvert.replaceAll("(?i)YE", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YI", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YO", "ᜌᜓ");
        toConvert = toConvert.replaceAll("(?i)YU", "ᜌᜓ");

        toConvert = toConvert.replaceAll("(?i)NG", "ᜅ᜔");
        toConvert = toConvert.replaceAll("(?i)B", "ᜊ᜔");
        toConvert = toConvert.replaceAll("(?i)K", "ᜃ᜔");
        toConvert = toConvert.replaceAll("(?i)D", "ᜇ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)H", "ᜑ᜔");
        toConvert = toConvert.replaceAll("(?i)L", "ᜎ᜔");
        toConvert = toConvert.replaceAll("(?i)M", "ᜋ᜔");
        toConvert = toConvert.replaceAll("(?i)N", "ᜈ᜔");
        toConvert = toConvert.replaceAll("(?i)P", "ᜉ᜔");
        toConvert = toConvert.replaceAll("(?i)R", "\u170D ᜔");
        toConvert = toConvert.replaceAll("(?i)S", "ᜐ᜔");
        toConvert = toConvert.replaceAll("(?i)T", "ᜆ᜔");
        toConvert = toConvert.replaceAll("(?i)W", "ᜏ᜔");
        toConvert = toConvert.replaceAll("(?i)Y", "ᜌ᜔");

        for (int i = 0; i < toConvert.length(); i++) {
            char character = toConvert.toUpperCase().charAt(i);
            switch (character) {
                case 'A':
                    mappedText.append("ᜀ");
                    break;
                case 'E':
                case 'I':
                    mappedText.append("ᜁ");
                    break;
                case 'O':
                case 'U':
                    mappedText.append("ᜂ");
                    break;
                case '.':
                    mappedText.append("᜶");
                    break;
                case ',':
                    mappedText.append("᜵");
                    break;

                default:
                    mappedText.append(character);
                    break;
            }
        }
        return mappedText.toString().trim();
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