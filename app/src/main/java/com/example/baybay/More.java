package com.example.baybay;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class More extends AppCompatActivity {

    private Toast globalToast;
    Handler handler = new Handler();
    private String snapshotcount = "S1R1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_more);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Define the colors for the gradient
        int topColor = Color.parseColor("#12062a"); // #160630 with full opacity
        int bottomColor = Color.parseColor("#2E0C64"); // #24094E with full opacity

//        // Get the screen height
//        int screenHeight = getResources().getDisplayMetrics().heightPixels;
//
//        // Calculate the heights for the top and bottom colors (20% and 80% respectively)
//        int topHeight = (int) (screenHeight * 0.1); // 20% of screen height
//        int bottomHeight = screenHeight - topHeight;

        // Create the custom GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{topColor, bottomColor});

        // Set the gradient heights
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setGradientCenter(0, 0);
        gradientDrawable.setBounds(0, 0, getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

        // Set the custom GradientDrawable as the window background
        getWindow().setBackgroundDrawable(gradientDrawable);

        setContentView(R.layout.activity_more);


        //Setting GIFs
        //ImageView imgview_star = findViewById(R.id.imgview_moreStar);
        //Glide.with(this).load(R.drawable.more_stars).into(imgview_star);

        ImageButton Imgbtn_moreExit;
        Imgbtn_moreExit = findViewById(R.id.imgbtn_moreExit);
        Imgbtn_moreExit.setOnClickListener(v -> onBackPressed());

        //READING MATERIALS
        ImageButton ImgbtnDL_introducing = findViewById(R.id.imgbtnDL_introducing);
        ImgbtnDL_introducing.setOnClickListener(v -> {
            animateButton(ImgbtnDL_introducing);
            downloadPicker = "pdf_introducingbaybayin_characters_for_children.pdf";
            ProceedPDFDownload();
        });

        ImageButton ImgbtnDL_understanding = findViewById(R.id.imgbtnDL_understanding);
        ImgbtnDL_understanding.setOnClickListener(v -> {
            animateButton(ImgbtnDL_understanding);
            downloadPicker = "pdf_understanding_baybayin_and_its_rules.pdf";
            ProceedPDFDownload();
        });

        ImageButton ImgbtnDL_baybayinChart = findViewById(R.id.imgbtnDL_baybayinchart);
        ImgbtnDL_baybayinChart.setOnClickListener(v -> {
            animateButton(ImgbtnDL_baybayinChart);
            downloadPicker = "pdf_the_baybayin_script_chart.pdf";
            ProceedPDFDownload();
        });

        TextView Tv_libraryShortcut = findViewById(R.id.tv_libraryshortcut);
        Tv_libraryShortcut.setOnClickListener(v -> {
                Intent More = new Intent(getApplicationContext(), Library.class);
                startActivity(More);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //More.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
        });

        ImageView imgview9 = findViewById(R.id.imageView9);
        imgview9.setOnClickListener(v -> {
            snapshotcount = "S1R1";
            snapshot();
        });
        TextView Tv_S1_R1 = findViewById(R.id.tv_s1_r1);
        Tv_S1_R1.setOnClickListener(v -> {
            snapshotcount = "S1R1";
            snapshot();
        });

        ImageView imgview15 = findViewById(R.id.imageView15);
        imgview15.setOnClickListener(v -> {
            snapshotcount = "S1R2";
            snapshot();
        });
        TextView Tv_S1_R2 = findViewById(R.id.tv_s1_r2);
        Tv_S1_R2.setOnClickListener(v -> {
            snapshotcount = "S1R2";
            snapshot();
        });

        ImageView imgview16 = findViewById(R.id.imageView16);
        imgview16.setOnClickListener(v -> {
            snapshotcount = "S1R3";
            snapshot();
        });
        TextView Tv_S1_R3 = findViewById(R.id.tv_s1_r3);
        Tv_S1_R3.setOnClickListener(v -> {
            snapshotcount = "S1R3";
            snapshot();
        });

        //ACTIVITIES
        ImageButton Imgbtn_tracing = findViewById(R.id.imgbtnDL_tracing);
        Imgbtn_tracing.setOnClickListener(v -> {
            animateButton(Imgbtn_tracing);
            downloadPicker = "pdf_1_practice_tracing.pdf";
            ProceedPDFDownload();
        });

        ImageButton Imgbtn_matching = findViewById(R.id.imgbtnDL_matching);
        Imgbtn_matching.setOnClickListener(v -> {
            animateButton(Imgbtn_matching);
            downloadPicker = "pdf_2_matching_test.pdf";
            ProceedPDFDownload();
        });

        ImageButton Imgbtn_draw = findViewById(R.id.imgbtnDL_draw);
        Imgbtn_draw.setOnClickListener(v -> {
            animateButton(Imgbtn_draw);
            downloadPicker = "pdf_3_draw_characters.pdf";
            ProceedPDFDownload();
        });

        ImageButton Imgbtn_crossword = findViewById(R.id.imgbtnDL_crossword);
        Imgbtn_crossword.setOnClickListener(v -> {
            animateButton(Imgbtn_crossword);
            downloadPicker = "pdf_4_crossword_puzzle.pdf";
            ProceedPDFDownload();
        });

        ImageButton Imgbtn_paragraph = findViewById(R.id.imgbtnDL_paragraph);
        Imgbtn_paragraph.setOnClickListener(v -> {
            animateButton(Imgbtn_paragraph);
            downloadPicker = "pdf_5_paragraph_translation.pdf";
            ProceedPDFDownload();
        });

        ImageView imgview11 = findViewById(R.id.imageView11);
        imgview11.setOnClickListener(v -> {
            snapshotcount = "S2A1";
            snapshot();
        });
        TextView Tv_S2_A1 = findViewById(R.id.tv_s2_a1);
        Tv_S2_A1.setOnClickListener(v -> {
            snapshotcount = "S2A1";
            snapshot();
        });

        ImageView imgview22 = findViewById(R.id.imageView22);
        imgview22.setOnClickListener(v -> {
            snapshotcount = "S2A3";
            snapshot();
        });
        TextView Tv_S2_A2 = findViewById(R.id.tv_s2_a2);
        Tv_S2_A2.setOnClickListener(v -> {
            snapshotcount = "S2A3";
            snapshot();
        });

        ImageView imgview25 = findViewById(R.id.imageView25);
        imgview25.setOnClickListener(v -> {
            snapshotcount = "S2A2";
            snapshot();
        });
        TextView Tv_S2_A3 = findViewById(R.id.tv_s2_a3);
        Tv_S2_A3.setOnClickListener(v -> {
            snapshotcount = "S2A2";
            snapshot();
        });

        ImageView imgview26 = findViewById(R.id.imageView26);
        imgview26.setOnClickListener(v -> {
            snapshotcount = "S2A4";
            snapshot();
        });
        TextView Tv_S2_A4 = findViewById(R.id.tv_s2_a4);
        Tv_S2_A4.setOnClickListener(v -> {
            snapshotcount = "S2A4";
            snapshot();
        });

        ImageView imgview21 = findViewById(R.id.imageView21);
        imgview21.setOnClickListener(v -> {
            snapshotcount = "S2A5";
            snapshot();
        });
        TextView Tv_S2_A5 = findViewById(R.id.tv_s2_a5);
        Tv_S2_A5.setOnClickListener(v -> {
            snapshotcount = "S2A5";
            snapshot();
        });

//hello

        //EDUCATIONAL VIDEOS
        ImageView Imgview_UNTV = findViewById(R.id.imgview_untv);
        Imgview_UNTV.setOnClickListener(v -> {
            animateButton(Imgview_UNTV);
            ClickSoundEffect();
            handler.postDelayed(() -> {
                link = "https://www.youtube.com/watch?v=dkh1oKaAqnU&feature=youtu.be";
                ProceedToLink();
            }, 500);
        });

        ImageView Imgview_Plvton = findViewById(R.id.imgview_plvton);
        Imgview_Plvton.setOnClickListener(v -> {
            animateButton(Imgview_Plvton);
            ClickSoundEffect();
            handler.postDelayed(() -> {
                link = "https://www.youtube.com/watch?v=7GjeNO-KB8s";
                ProceedToLink();
            }, 500);
        });

        ImageView Imgview_GMA = findViewById(R.id.imgview_gma);
        Imgview_GMA.setOnClickListener(v -> {
            animateButton(Imgview_GMA);
            ClickSoundEffect();
            handler.postDelayed(() -> {
                link = "https://www.youtube.com/watch?v=N3LOfY1KAno&feature=youtu.be";
                ProceedToLink();
            }, 500);
        });

        ImageView Imgview_ABSCBN = findViewById(R.id.imgview_abscbn);
        Imgview_ABSCBN.setOnClickListener(v -> {
            animateButton(Imgview_ABSCBN);
            ClickSoundEffect();
            handler.postDelayed(() -> {
                link = "https://www.youtube.com/watch?v=Ke-7SpakbUw";
                ProceedToLink();
            }, 500);
        });



        //SOUVENIRS
        ImageView ImgviewEmmanuelBook = findViewById(R.id.imgview_emanuelbook);
        ImgviewEmmanuelBook.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgviewEmmanuelBook);
            handler.postDelayed(() -> {
                link = "https://bit.ly/3DBTSqX";
                ProceedToLink();
            }, 500);
        });

        ImageView ImgviewShirt = findViewById(R.id.imgview_shirt);
        ImgviewShirt.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgviewShirt);
            handler.postDelayed(() -> {
                link = "https://rb.gy/2gtsp";
                ProceedToLink();
            }, 500);
        });

        ImageView ImgviewBags = findViewById(R.id.imgview_bags);
        ImgviewBags.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgviewBags);
            handler.postDelayed(() -> {
                link = "https://rb.gy/j2ji9";
                ProceedToLink();
            }, 500);
        });

        ImageView ImgviewPants = findViewById(R.id.imgview_pants);
        ImgviewPants.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgviewPants);
            handler.postDelayed(() -> {
                link = "https://rb.gy/esfdk";
                ProceedToLink();
            }, 500);
        });

        ImageView ImgviewStickers = findViewById(R.id.imgview_stickers);
        ImgviewStickers.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgviewStickers);
            handler.postDelayed(() -> {
                link = "https://tinyurl.com/y6c3tr2e";
                ProceedToLink();
            }, 500);
        });

        ImageView ImgviewNotebook = findViewById(R.id.imgview_notebook);
        ImgviewNotebook.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgviewNotebook);
            handler.postDelayed(() -> {
                link = "https://bit.ly/47ebK8T";
                ProceedToLink();
            }, 500);
        });

        ImageView ImgviewShop = findViewById(R.id.imgview_shop);
        ImgviewShop.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(ImgviewShop);
            handler.postDelayed(() -> {
                link = "https://bit.ly/43RHw8G";
                ProceedToLink();
            }, 500);
        });

        //SOCIAL
        ImageView Imgview_Facebook = findViewById(R.id.imgview_facebook);
        Imgview_Facebook.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(Imgview_Facebook);
            handler.postDelayed(() -> {
                link = "https://www.facebook.com/groups/Baybayin.PhilippineNationalWritingSystem/";
                ProceedToLink();
            }, 500);
        });

        ImageView Imgview_Reddit = findViewById(R.id.imgview_reddit);
        Imgview_Reddit.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(Imgview_Reddit);
            handler.postDelayed(() -> {
                link = "https://www.reddit.com/r/baybayin_script/new/";
                ProceedToLink();
            }, 500);
        });

        ImageView Imgview_Workshop = findViewById(R.id.imgview_workshop);
        Imgview_Workshop.setOnClickListener(v -> {
            ClickSoundEffect();
            animateButton(Imgview_Workshop);
            handler.postDelayed(() -> {
                link = "https://binged.it/47dey63";
                ProceedToLink();
            }, 500);
        });

        //SCROLL TO TOP
        NestedScrollView more_nestedScrollview = findViewById(R.id.more_scrollview);
        ImageButton Imgbtn_ScrollToTop = findViewById(R.id.imgbtn_scrollToTop);
        Imgbtn_ScrollToTop.setOnClickListener(v -> {
            // Calculate the duration for the smooth scroll (in milliseconds)
            int duration = 1000; // 2 seconds

            // Calculate the current scroll position
            int currentScrollY = more_nestedScrollview.getScrollY();

            // Start the smooth scroll animation
            smoothScrollToTop(more_nestedScrollview, currentScrollY, duration);
        });

        Imgbtn_ScrollToTop.setOnLongClickListener(view -> {

            new Handler().postDelayed(() -> {
                int currentTrophies = Z_TrophyManager.getTrophies();
                int newTrophies = currentTrophies + 40;
                Z_TrophyManager.setTrophies(newTrophies);
                Toasty.warning(More.this, "Debug trophies has been added", Toast.LENGTH_SHORT).show();
            }, 5000);

            return true;
        });

    }



    //PROCEED TO LINK CONFIRMATION
    String link = "";
    private void ProceedToLink(){
        Dialog dlg = new Dialog(More.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_more_link_address);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyBounceAnimation(dialogWindowView);

        ImageButton BtnYesExit = dlg.findViewById(R.id.imgbtn_yes_exit);
        BtnYesExit.setOnClickListener(v -> {
            dlg.dismiss();
            ClickSoundEffect(); // Play click sound effect
            gotoLink(link);
            link = "";
        });

        ImageButton BtnNoExit = dlg.findViewById(R.id.imgbtn_no_exit);
        BtnNoExit.setOnClickListener(v -> {
            ClickSoundEffect(); // Play click sound effect
            dlg.dismiss();
            link = "";
            cancelToast();
            globalToast = Toasty.info(More.this, "Task Dismissed.", Toasty.LENGTH_SHORT);
            globalToast.show();
        });
    }

    //DIRECT TO LINKS (SHOPS AND VIDEOS)
    void gotoLink(String l){
        try{
            Uri uri = Uri.parse(l);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }catch (Exception e){
            cancelToast();
            globalToast = Toast.makeText(this, "Site not available at the moment", Toast.LENGTH_SHORT);
            globalToast.show();
        }
    }

    //DOWNLOAD PDF CONFIRMATION
    private void ProceedPDFDownload(){
        Dialog dlg = new Dialog(More.this, R.style.PopupDialog);
        dlg.setCanceledOnTouchOutside(false);  // Disable dialog dismiss when touch outside
        dlg.setContentView(R.layout.activity_more_pdf_download_confirmation);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyBounceAnimation(dialogWindowView);

        ImageButton BtnYesExit = dlg.findViewById(R.id.imgbtn_yes_exit);
        BtnYesExit.setOnClickListener(v -> {
            dlg.dismiss();
            ClickSoundEffect(); // Play click sound effect
            savePdfToDownloadFolder();
        });

        ImageButton BtnNoExit = dlg.findViewById(R.id.imgbtn_no_exit);
        BtnNoExit.setOnClickListener(v -> {
            ClickSoundEffect(); // Play click sound effect
            downloadPicker = "";
            dlg.dismiss();
            cancelToast();
            globalToast = Toasty.info(More.this, "Task Dismissed.", Toasty.LENGTH_SHORT);
            globalToast.show();
        });
    }

    //DOWNLOAD PDF
    String downloadPicker = "";
    private void savePdfToDownloadFolder() {
        try {
            if (downloadPicker.isEmpty()) {
                // Check if the downloadPicker is empty or not set
                cancelToast();
                globalToast = Toasty.info(this, "No PDF selected to download", Toast.LENGTH_SHORT);
                globalToast.show();
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

            cancelToast();
            globalToast = Toasty.success(this, "FILE SAVED SUCCESSFULLY to Downloads folder: " + fileName, Toast.LENGTH_SHORT);
            globalToast.show();
            downloadPicker = "";
        } catch (IOException e) {
            e.printStackTrace();
            cancelToast();
            globalToast = Toasty.error(this, "FAILED to save PDF file", Toast.LENGTH_SHORT);
            globalToast.show();
            downloadPicker = "";
        }
    }

    private void snapshot(){
        Dialog dlg = new Dialog(More.this, R.style.PopupDialog);
        dlg.setContentView(R.layout.activity_snapshot);
        dlg.show();

        View dialogWindowView = dlg.getWindow().getDecorView();
        Z_Dialogs_Animation.applyZoomInAnimationMore(dialogWindowView);

        ImageView Imgview_SnapshotHolder = dlg.findViewById(R.id.imgview_snapshotholder);

        switch(snapshotcount) {
            case "S1R1":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot1_reading1);
                break;
            case "S1R2":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot1_reading2);
                break;
            case "S1R3":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot1_reading3);
                break;

            case "S2A1":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity1);
                break;
            case "S2A2":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity2);
                break;
            case "S2A3":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity3);
                break;
            case "S2A4":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity4);
                break;
            case "S2A5":
                Imgview_SnapshotHolder.setImageResource(R.drawable.snapshot2_activity5);
                break;
            default:
                cancelToast();
                globalToast = Toasty.info(this, "No Snapshot Available", Toast.LENGTH_SHORT);
                globalToast.show();
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
    }

    //MediaPlayer songMore;
    @Override
    protected void onResume() {
        super.onResume();
        BackgroundSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Z_SoundManager.StopMoreMusic();
    }

    // Call the RegButtonClickSound method from Z_SoundManager
    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    void BackgroundSound() {
        Z_SoundManager.PlayMoreMusic(getApplicationContext());
    }

    void StopBackgroundSound(){
        Z_SoundManager.StopMoreMusic();
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

    // Method to perform smooth scrolling to the top
    private void smoothScrollToTop(NestedScrollView scrollView, int startY, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(startY, 0);
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            scrollView.scrollTo(0, animatedValue);
        });
        animator.start();
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
    public void onBackPressed() {
        super.onBackPressed();
        StopBackgroundSound();
        cancelToast();
        Intent More = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(More);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //More.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //More.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}