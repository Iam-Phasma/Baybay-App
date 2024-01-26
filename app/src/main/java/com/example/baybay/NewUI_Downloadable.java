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
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class NewUI_Downloadable extends AppCompatActivity {

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

    private ImageButton ImgbtnDownloadableExit;
    private ImageButton R1, R2, R3;
    private ImageView SR1, SR2, SR3;
    private ImageButton A1, A2, A3, A4, A5;
    private ImageButton SA1, SA2, SA3, SA4, SA5;
    private String downloadPicker = "";
    private String snapshotcount = "SR1";
    private TextView DLLinkQuestion;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_downloadable);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();





        ImgbtnDownloadableExit = findViewById(R.id.imgbtn_downloadable_exit);
        ImgbtnDownloadableExit.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgbtnDownloadableExit);
            finish();
        });

        R1 = findViewById(R.id.imgbtn_dl_r1);
        R1.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_introducingbaybayin_characters_for_children.pdf";
            ProceedPDFDownload();
        });

        R2 = findViewById(R.id.imgbtn_dl_r2);
        R2.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_understanding_baybayin_and_its_rules.pdf";
            ProceedPDFDownload();
        });

        R3 = findViewById(R.id.imgbtn_dl_r3);
        R3.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_the_baybayin_script_chart.pdf";
            ProceedPDFDownload();
        });

        A1 = findViewById(R.id.imgbtn_a1_dlbutton);
        A1.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_1_practice_tracing.pdf";
            ProceedPDFDownload();
        });

        A2 = findViewById(R.id.imgbtn_a2_dlbutton);
        A2.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_2_matching_test.pdf";
            ProceedPDFDownload();
        });

        A3 = findViewById(R.id.imgbtn_a3_dlbutton);
        A3.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_3_draw_characters.pdf";
            ProceedPDFDownload();
        });

        A4 = findViewById(R.id.imgbtn_a4_dlbutton);
        A4.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_4_crossword_puzzle.pdf";
            ProceedPDFDownload();
        });

        A5 = findViewById(R.id.imgbtn_a5_dlbutton);
        A5.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            downloadPicker = "pdf_5_paragraph_translation.pdf";
            ProceedPDFDownload();
        });

        //Snapshots
        SR1 = findViewById(R.id.imgview_fb1_board);
        SR1.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SR1";
            snapshot();
        });

        SR2 = findViewById(R.id.imgview_dl_r2_board);
        SR2.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SR2";
            snapshot();
        });

        SR3 = findViewById(R.id.imgview_dl_r3_board);
        SR3.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SR3";
            snapshot();
        });


        SA1 = findViewById(R.id.imgbtn_dl_a1_board);
        SA1.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SA1";
            snapshot();
        });

        SA2 = findViewById(R.id.imgbtn_dl_a2_board);
        SA2.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SA2";
            snapshot();
        });

        SA3 = findViewById(R.id.imgbtn_dl_a3_board);
        SA3.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SA3";
            snapshot();
        });

        SA4 = findViewById(R.id.imgbtn_dl_a4_board);
        SA4.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SA4";
            snapshot();
        });

        SA5 = findViewById(R.id.imgbtn_dl_a5_board);
        SA5.setOnClickListener(v -> {
            ClickSoundEffect();
            DisableNav();
            snapshotcount = "SA5";
            snapshot();
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


    //DOWNLOAD PDF CONFIRMATION
    private void ProceedPDFDownload(){
        Dialog dlg = new Dialog(NewUI_Downloadable.this, R.style.PopupDialog);
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
        DLLinkQuestion.setText("Do you want to save locally the selected file?");

        ImageButton BtnYesExit = dlg.findViewById(R.id.imgbtn_yes_exit);
        BtnYesExit.setOnClickListener(v -> {
            dlg.dismiss();
            ClickSoundEffect();
            savePdfToDownloadFolder();
        });

        ImageButton BtnNoExit = dlg.findViewById(R.id.imgbtn_no_exit);
        BtnNoExit.setOnClickListener(v -> {
            ClickSoundEffect();
            downloadPicker = "";
            dlg.dismiss();
//            Toasty.info(Library.this, "Task Dismissed.", Toasty.LENGTH_SHORT).show();
        });

        EnableNav();
    }

    //DOWNLOAD PDF
    private void savePdfToDownloadFolder() {
        try {
            if (downloadPicker.isEmpty()) {
                // Check if the downloadPicker is empty or not set
                Toasty.info(this, "No PDF selected to download", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the InputStream of the PDF file from the assets folder
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(downloadPicker);

            // Get the path to the Downloads folder
            File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            // Generate a unique file name with a timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = downloadPicker.replace(".pdf", "_" + timestamp + ".pdf");

            // Create a new file in the Downloads folder
            File outputFile = new File(downloadFolder, fileName);

            // Write the PDF content to the output file
            OutputStream outputStream = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                outputStream = Files.newOutputStream(outputFile.toPath());
            }
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                assert outputStream != null;
                outputStream.write(buffer, 0, length);
            }
            assert outputStream != null;
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Toasty.success(this, "FILE SAVED SUCCESSFULLY to Downloads folder: " + fileName, Toast.LENGTH_SHORT).show();
            downloadPicker = "";
        } catch (IOException e) {
            e.printStackTrace();
            Toasty.error(this, "FAILED to save PDF file", Toast.LENGTH_SHORT).show();
            downloadPicker = "";
        }
    }

    private void snapshot(){
        Dialog dlg = new Dialog(NewUI_Downloadable.this, R.style.PopupDialog);
        dlg.setContentView(R.layout.activity_snapshot);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        ImageView Imgview_SnapshotHolder = dlg.findViewById(R.id.imgview_snapshotholder);

        switch(snapshotcount) {
            case "SR1":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot1_reading1);
                break;
            case "SR2":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot1_reading2);
                break;
            case "SR3":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot1_reading3);
                break;

            case "SA1":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity1);
                break;
            case "SA2":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity3);
                break;
            case "SA3":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity2);
                break;
            case "SA4":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity4);
                break;
            case "SA5":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity5);
                break;
            default:
        }

        //Dismiss dialog when view is clicked
        ConstraintLayout ConstraintLayoutSnapshot = dlg.findViewById(R.id.constraintlayout_snapshot);
        ConstraintLayoutSnapshot.setOnClickListener(v -> {
            dlg.dismiss(); // Close the dialog
        });

        //Dismiss dialog when clicked outside the layout
        dialogWindowView.setOnClickListener(v -> {
            dlg.dismiss(); // Close the dialog
        });

        EnableNav();
    }

    private void DisableNav(){
        R1.setEnabled(false);
        R2.setEnabled(false);
        R3.setEnabled(false);
        A1.setEnabled(false);
        A2.setEnabled(false);
        A3.setEnabled(false);
        A4.setEnabled(false);
        A5.setEnabled(false);

        SR1.setEnabled(false);
        SR2.setEnabled(false);
        SR3.setEnabled(false);
        SA1.setEnabled(false);
        SA2.setEnabled(false);
        SA3.setEnabled(false);
        SA4.setEnabled(false);
        SA5.setEnabled(false);
    }

    private void EnableNav(){
        R1.setEnabled(true);
        R2.setEnabled(true);
        R3.setEnabled(true);
        A1.setEnabled(true);
        A2.setEnabled(true);
        A3.setEnabled(true);
        A4.setEnabled(true);
        A5.setEnabled(true);

        SR1.setEnabled(true);
        SR2.setEnabled(true);
        SR3.setEnabled(true);
        SA1.setEnabled(true);
        SA2.setEnabled(true);
        SA3.setEnabled(true);
        SA4.setEnabled(true);
        SA5.setEnabled(true);
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
}