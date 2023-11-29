package com.example.baybay;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.TypedValue;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class Library extends AppCompatActivity {

    Handler handler = new Handler();
    public int bookNumber = 1;
    TextView TvBookTitle;
    private NestedScrollView NestedSV_Libraray_Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_library);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        View rootView = getWindow().getDecorView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this); // Remove the listener to avoid multiple calls

                WindowInsets insets = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    insets = rootView.getRootWindowInsets();
                }

                if (insets != null) {
                    DisplayCutout cutout = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        cutout = insets.getDisplayCutout();
                    }

                    if (cutout != null) {
                        // The device has a cutout, so set the top margin to 310dp
                        int topMargin = (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 290, getResources().getDisplayMetrics()
                        );
                        // Set the top margin for your TextView (assuming you have a TextView with ID tv_booktitle)
                        TextView tvBookTitle = findViewById(R.id.tv_booktitle);
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tvBookTitle.getLayoutParams();
                        params.topMargin = topMargin;
                        tvBookTitle.setLayoutParams(params);
                    } else {
                        // The device does not have a cutout, so set the top margin to 290dp
                        int topMargin = (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 310, getResources().getDisplayMetrics()
                        );
                        // Set the top margin for your TextView (assuming you have a TextView with ID tv_booktitle)
                        TextView tvBookTitle = findViewById(R.id.tv_booktitle);
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tvBookTitle.getLayoutParams();
                        params.topMargin = topMargin;
                        tvBookTitle.setLayoutParams(params);
                    }
                } else {
                    Toasty.info(Library.this, "NO", Toasty.LENGTH_SHORT).show();
                }
            }
        });

        // Set the background to a drawable resource
        LibraryWallChanger();

        ImageButton ImgbtnExitLibrary = findViewById(R.id.imgbtn_library_exit);
        ImgbtnExitLibrary.setOnClickListener(v ->{
            ClickSoundEffect();
            onBackPressed();
        } );

        NestedSV_Libraray_Description = findViewById(R.id.nestedsv_library_description);

        ImageButton ImgBtnViewPdf = findViewById(R.id.imgbtn_library_viewpdf);
        ImgBtnViewPdf.setOnClickListener(v -> {
            Intent intent = new Intent(Library.this, PdfViewerActivity.class);
            intent.putExtra("bookNumber", bookNumber);
            startActivity(intent);
        });

        ImageButton ImgbtnPrevious = findViewById(R.id.imgbtn_library_previous);
        ImgbtnPrevious.setOnClickListener(v -> {
            NestedSV_Libraray_Description.smoothScrollTo(0, 0);
            FlipLeftSound();
            animateButton(ImgbtnPrevious);
            ImgbtnPrevious.setEnabled(false);
            if(bookNumber != 1){
                bookNumber--;
                LibraryWallChanger();
            }else{
                bookNumber = 4;
                LibraryWallChanger();
            }

            handler.postDelayed(() -> {
                ImgbtnPrevious.setEnabled(true);
            }, 500);
        });

        ImageButton ImgbtnNext = findViewById(R.id.imgbtn_library_next);
        ImgbtnNext.setOnClickListener(v -> {
            NestedSV_Libraray_Description.smoothScrollTo(0, 0);
            FlipRightSound();
            animateButton(ImgbtnNext);
            ImgbtnNext.setEnabled(false);
            if(bookNumber != 4){
                bookNumber++;
                LibraryWallChanger();
            }else{
                bookNumber = 1;
                LibraryWallChanger();
            }


            handler.postDelayed(() -> {
                ImgbtnNext.setEnabled(true);
            }, 500);
        });

        ImageButton ImgbtnSave = findViewById(R.id.imgbtn_library_save);
        ImgbtnSave.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgbtnSave);
            ImgbtnSave.setEnabled(false);
            handler.postDelayed(() -> {
                ImgbtnSave.setEnabled(true);
                if (bookNumber == 1){
                    downloadPicker = "pdf_library_mga_alamat.pdf";
                } else if (bookNumber == 2) {
                    downloadPicker = "pdf_library_likhang_makabayan.pdf";
                } else if (bookNumber == 3) {
                    downloadPicker = "pdf_library_maikling_kwento.pdf";
                } else if (bookNumber == 4) {
                    downloadPicker = "pdf_library_mga_tula.pdf";
                }
                ProceedPDFDownload();
            }, 500);
        });

    }

    public void LibraryWallChanger(){
        TvBookTitle = findViewById(R.id.tv_booktitle);
        TextView TvBoonSubTitle = findViewById(R.id.tv_booksubtitle);
        TextView TvBookDescription = findViewById(R.id.tv_book_description);
        if(bookNumber == 1){
            getWindow().setBackgroundDrawableResource(R.drawable.library_alamat_wall);
            TvBookTitle.setText("Mga Alamat");
            TvBoonSubTitle.setText("Legends and Myths");
            TvBookDescription.setText(R.string.alamat_description);
        } else if (bookNumber == 2) {
            getWindow().setBackgroundDrawableResource(R.drawable.library_likhang_makabayan_wall);
            TvBookTitle.setText("Likhang Makabayan");
            TvBoonSubTitle.setText("Nationalist Literatures");
            TvBookDescription.setText(R.string.likhang_makabayan_description);
        } else if (bookNumber == 3) {
            getWindow().setBackgroundDrawableResource(R.drawable.library_maikling_kwento_wall);
            TvBookTitle.setText("Maikling Kwento");
            TvBoonSubTitle.setText("Short Stories");
            TvBookDescription.setText(R.string.maikling_kwento_description);
        } else if (bookNumber == 4) {
            getWindow().setBackgroundDrawableResource(R.drawable.library_tula_wall);
            TvBookTitle.setText("Mga Tula");
            TvBoonSubTitle.setText("Poems");
            TvBookDescription.setText(R.string.tula_description);
        }
    }


    //DOWNLOAD PDF CONFIRMATION
    private void ProceedPDFDownload(){
        Dialog dlg = new Dialog(Library.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_more_pdf_download_confirmation);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyBounceAnimation(dialogWindowView);

        ImageButton BtnYesExit = dlg.findViewById(R.id.imgbtn_yes_exit);
        BtnYesExit.setOnClickListener(v -> {
            dlg.dismiss();
            //ClickSoundEffect();
            savePdfToDownloadFolder();
        });

        ImageButton BtnNoExit = dlg.findViewById(R.id.imgbtn_no_exit);
        BtnNoExit.setOnClickListener(v -> {
            //ClickSoundEffect();
            downloadPicker = "";
            dlg.dismiss();
            Toasty.info(Library.this, "Task Dismissed.", Toasty.LENGTH_SHORT).show();
        });
    }

    //DOWNLOAD PDF
    String downloadPicker = "";
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

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    void FlipLeftSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(Library.this, R.raw.page_flip_left);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
    }

    void FlipRightSound() {
        MediaPlayer mediaPlayer;
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            mediaPlayer = MediaPlayer.create(Library.this, R.raw.pag_flip_right);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Z_SoundManager.PlayMoreMusic(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Z_SoundManager.StopMoreMusic();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Z_SoundManager.StopMoreMusic();
        Intent More = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(More);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //More.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //More.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}