package com.example.baybay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.mrudultora.colorpicker.ColorPickerDialog;
import com.mrudultora.colorpicker.listeners.OnSelectColorListener;
import com.mrudultora.colorpicker.util.ColorItemShape;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

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
    ImageButton ColorPicker, BgColorPicker, Canvas_exit, Imgbtn_SwitchFontStyle, Save, CanvasDelete, CanvasSend, Canvas_info;
    private ColorPickerDialog colorPickerDialog;
    SeekBar FontSize;
    EditText Edittext_content;
    ConstraintLayout Conslayout_canvas;
    RelativeLayout RelativeLContent;
    TextView FontNameDisplay;

    private ImageView ImgviewCards;

    @SuppressLint("SetTextI18n")
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

        setBackgroundColor();



        Edittext_content = findViewById(R.id.edittext_content);
        CanvasSend = findViewById(R.id.imgbtn_canvas_send);

        ColorPicker = findViewById(R.id.btn_color_picker);
        ColorPicker.setOnClickListener(v -> {
            TextColorPicker();
            ClickSoundEffect();
        });

        GradientDrawable editTextDrawable = new GradientDrawable();
        editTextDrawable.setColor(Color.parseColor("#8093F1"));
        Edittext_content.setBackground(editTextDrawable);
        editTextDrawable.setCornerRadius(40);

        BgColorPicker = findViewById(R.id.btn_bgcolor_picker);
        BgColorPicker.setOnClickListener(v -> {
            BackgroundColorPicker();
            ClickSoundEffect();
        });

        TextView TvTextAlignment = findViewById(R.id.tv_text_alignment);
        TvTextAlignment.setOnClickListener(v -> {
            ClickSoundEffect();
            TvTextAlignment.setEnabled(false);

            String currentText = TvTextAlignment.getText().toString();
            switch (currentText) {
                case "Center":
                    Edittext_content.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    TvTextAlignment.setText("Right");
                    break;
                case "Right":
                    Edittext_content.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    TvTextAlignment.setText("Left");
                    break;
                default:
                    Edittext_content.setGravity(Gravity.CENTER);
                    TvTextAlignment.setText("Center");
                    break;
            }

            new Handler(Looper.getMainLooper()).postDelayed(() -> TvTextAlignment.setEnabled(true), 200);
        });

        FontNameDisplay = findViewById(R.id.tv_font_name);
        CanvasSend.setEnabled(false);

        String[] fonts = {"baybayin_bayani.ttf","baybayin_chochin.ttf", "baybayin_robotika.ttf", "baybayin_sarimanok.ttf", "baybayin_sawasdee.ttf", "baybayin_sejong.ttf", "baybayin_tinta.ttf", "baybayin_deko.ttf"};
        final int[] currentFontIndex = {0};

        final String[] originalText = {""};
        Imgbtn_SwitchFontStyle = findViewById(R.id.imgbtn_switch_font_style);
        Imgbtn_SwitchFontStyle.setOnClickListener(v -> {
            ClickSoundEffect();
            CanvasSend.setEnabled(true);
            Imgbtn_SwitchFontStyle.setEnabled(false);

            FontNameDisplay.setText(fonts[currentFontIndex[0]]);


            Imgbtn_SwitchFontStyle.animate().rotation(360).setDuration(600).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Imgbtn_SwitchFontStyle.setRotation(0);
                    Imgbtn_SwitchFontStyle.setEnabled(true);
                }
            }).start();

            // Change the font style without altering the original text
            if (!originalText[0].isEmpty()) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fonts[currentFontIndex[0]]);
                Edittext_content.setTypeface(typeface);
            }
            currentFontIndex[0] = (currentFontIndex[0] + 1) % fonts.length;
        });

        CanvasSend.setOnClickListener(v -> {
            ClickSoundEffect();
            Edittext_content.setAllCaps(true);
            if ((Edittext_content.length() != 0) || (!originalText[0].isEmpty())){
                if (originalText[0].isEmpty()) {
                    originalText[0] = Edittext_content.getText().toString();
                }

                // Update the font style
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fonts[currentFontIndex[0] == 0 ? fonts.length - 1 : currentFontIndex[0] - 1]);
                Edittext_content.setTypeface(typeface);

                String convertedText = Z_BaybayinCanvasConverter.mapToRobotika(originalText[0]);

                Edittext_content.setText(convertedText);

                Edittext_content.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                Edittext_content.setEnabled(false);
            } else {
                cancelToast();
                globalToast = Toasty.warning(NewUI_Canvas.this, "Field's empty! Type something.", Toast.LENGTH_SHORT);
                globalToast.show();

                Edittext_content.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(Edittext_content, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        Save = findViewById(R.id.imgbtn_save);
        Save.setOnClickListener(v -> {
            RelativeLContent = findViewById(R.id.relativeL_content);

            if (Edittext_content.length() != 0) {
                Edittext_content.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                ClickSoundEffect();
                saveImage();
            } else {
                cancelToast();
                globalToast = Toasty.warning(NewUI_Canvas.this, "Nothing to save yet.", Toast.LENGTH_SHORT);
                globalToast.show();

                Edittext_content.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(Edittext_content, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        FontSize = findViewById(R.id.seekbar_fontsize);
        FontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float fontSize = 14 + progress;
                Edittext_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Conslayout_canvas = findViewById(R.id.conslayout_canvas);
        Conslayout_canvas.setOnClickListener(v -> {
            Edittext_content.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        CanvasDelete = findViewById(R.id.imgbtn_canvas_delete);
        CanvasDelete.setOnClickListener(v -> {
            ClickSoundEffect();
            if ((Edittext_content.length() != 0) || (!originalText[0].isEmpty())){
                Edittext_content.setText("");
                originalText[0] = "";
                Edittext_content.setTextSize(18);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.noto_sans_bold);
                Edittext_content.setTypeface(typeface);

                Edittext_content.setEnabled(true);
            } else {
                cancelToast();
                globalToast = Toasty.warning(NewUI_Canvas.this, "Field's already empty! Type something.", Toast.LENGTH_SHORT);
                globalToast.show();

                Edittext_content.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(Edittext_content, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        Canvas_info = findViewById(R.id.imgbtn_canvas_info);
        Canvas_info.setOnClickListener(v -> {
            ClickSoundEffect();
            Canvas_info.setEnabled(false);
            CanvasInfoDialog();
        });

        Canvas_exit = findViewById(R.id.imgbtn_canvas_exit);
        Canvas_exit.setOnClickListener(v -> {
            ClickSoundEffect();
            cancelToast();
            finish();
        });

        Edittext_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (null != Edittext_content.getLayout() && Edittext_content.getLayout().getLineCount() > 25) {
                    Edittext_content.getText().delete(Edittext_content.getText().length() - 1, Edittext_content.getText().length());
                }
                for (UnderlineSpan span : editable.getSpans(0, editable.length(), UnderlineSpan.class)) {
                    editable.removeSpan(span);
                }
                // Request layout update
                Edittext_content.requestLayout();
            }
        });

    }


    private void TextColorPicker() {
        colorPickerDialog = new ColorPickerDialog(new ContextThemeWrapper(this, R.style.MoreActivityTheme));
        colorPickerDialog.setDialogTitle("Select a color");
        colorPickerDialog.setColors(new ArrayList<>(Arrays.asList(
                        "#FFC1C1", "#FFD1A1", "#FFFFB3", "#B3FFB3", "#B3D9FF",
                        "#EE7272", "#FCB274", "#F9DC5C", "#8AF0BF", "#8093F1",
                        "#B72727", "#B76527", "#B7B727", "#30B727", "#2738B7",
                        "#C2A3FF", "#E6B3FF", "#EBDEBE", "#FFFFFF", "#A6A6A6",
                        "#9C8FE5", "#B795E4", "#AA7A79", "#0A0A0A", "#D8B5BE",
                        "#5E27B7", "#B727AB", "#88542D", "#68F5EE", "#015332"
                        )))
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
                        ColorPicker = findViewById(R.id.btn_color_picker);
                        GradientDrawable drawable = (GradientDrawable) ColorPicker.getBackground();
                        drawable.setColor(color);

                        Edittext_content.setTextColor(color);
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

    private void BackgroundColorPicker() {
        colorPickerDialog = new ColorPickerDialog(new ContextThemeWrapper(this, R.style.MoreActivityTheme));
        colorPickerDialog.setDialogTitle("Select a color");
        colorPickerDialog.setColors(new ArrayList<>(Arrays.asList(
                        "#FFC1C1", "#FFD1A1", "#FFFFB3", "#B3FFB3", "#B3D9FF",
                        "#EE7272", "#FCB274", "#F9DC5C", "#8AF0BF", "#8093F1",
                        "#B72727", "#B76527", "#B7B727", "#30B727", "#2738B7",
                        "#C2A3FF", "#E6B3FF", "#EBDEBE", "#FFFFFF", "#A6A6A6",
                        "#9C8FE5", "#B795E4", "#AA7A79", "#0A0A0A", "#D8B5BE",
                        "#5E27B7", "#B727AB", "#88542D", "#68F5EE", "#015332"
                )))
                .setColumns(5)
                .setDefaultSelectedColor("#8093F1")
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

                        GradientDrawable drawable = (GradientDrawable) BgColorPicker.getBackground();
                        drawable.setColor(color);

                        GradientDrawable editTextDrawable = new GradientDrawable();
                        editTextDrawable.setColor(color);
                        Edittext_content.setBackground(editTextDrawable);

                        editTextDrawable.setCornerRadius(40);
                        Edittext_content.setBackground(editTextDrawable);
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

    public void saveImage(){
        RelativeLContent.setDrawingCacheEnabled(true);
        RelativeLContent.buildDrawingCache();
        RelativeLContent.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bitmap bitmap = RelativeLContent.getDrawingCache();
        startSave(bitmap);
    }

    private void startSave(Bitmap bitmap) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(root+"/Download");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        String fileName = "quote_" + currentDateAndTime + ".jpeg";
        File myfile = new File(file, fileName);

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(myfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            RelativeLContent.setDrawingCacheEnabled(false);
            cancelToast();
            globalToast = Toasty.success(NewUI_Canvas.this, "Saved on downloads folder", Toast.LENGTH_SHORT);
            globalToast.show();

        }catch(Exception e){
            cancelToast();
            globalToast = Toasty.error(NewUI_Canvas.this, "Error", Toast.LENGTH_SHORT);
            globalToast.show();
        }
    }

    private void CanvasInfoDialog(){
        Dialog dlg;
        dlg = new Dialog(NewUI_Canvas.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_newui_canvas_infoprompt);
        dlg.show();

        View dialogWindowView = Objects.requireNonNull(dlg.getWindow()).getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        ConstraintLayout Constlayout_canvas_prompt = dlg.findViewById(R.id.constlayout_canvas_prompt);
        Drawable background = Constlayout_canvas_prompt.getBackground();

        if (background instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        } else if (background instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(Color.parseColor(Theme_Color.getDefaultColor()));
        }

        ImageSlider ImgSliderCard = dlg.findViewById(R.id.slider_card);
        List<SlideModel> slideModelsCards = new ArrayList<>();
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_bayani, ScaleTypes.CENTER_INSIDE));
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_chochin, ScaleTypes.CENTER_INSIDE));
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_robotika, ScaleTypes.CENTER_INSIDE));
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_sarimanok, ScaleTypes.CENTER_INSIDE));
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_sawasdee, ScaleTypes.CENTER_INSIDE));
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_sejong, ScaleTypes.CENTER_INSIDE));
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_tinta, ScaleTypes.CENTER_INSIDE));
        slideModelsCards.add(new SlideModel(R.drawable.newui_canvas_info_deko, ScaleTypes.CENTER_INSIDE));
        ImgSliderCard.setImageList(slideModelsCards);
        ImgSliderCard.setSlideAnimation(AnimationTypes.DEPTH_SLIDE);
        ImgSliderCard.startSliding(5000);


        dlg.setOnKeyListener((dialogInterface, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP);
        dlg.setCanceledOnTouchOutside(false);

        ImageButton Imgbtn_canvas_prompt_ok = dlg.findViewById(R.id.imgbtn_canvas_prompt_ok);
        Imgbtn_canvas_prompt_ok.setOnClickListener(v -> {
            Canvas_info.setEnabled(true);
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

    @Override
    public void onBackPressed() {

    }

}