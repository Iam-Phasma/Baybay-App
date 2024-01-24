package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class NewUI_L2_Introduction extends AppCompatActivity {
    ImageButton ImgbtnPlayPause;
    ProgressBar progressBar;
    NestedScrollView NestedSvIntroduction;
    ImageButton BtnExitIntroduction;
    private int IntroSlideTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_lessons_introduction);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        //int singleColor = Color.parseColor("#FCF4E7");
        Theme_Color.init(this);
        setBackgroundColor();






        Intent intent = getIntent();
        int getIntroSlideTo = intent.getIntExtra("introduction", 1);
        IntroSlideTo = getIntroSlideTo;
        slideToTextview();

        BtnExitIntroduction = findViewById(R.id.btn_charsound_exit3);
        BtnExitIntroduction.setOnClickListener(view -> {
            finish();
        });

        TextView tvBriefIntroduction = findViewById(R.id.tv_brief_introduction);
        tvBriefIntroduction.setText(R.string.baybayin_introduction);

        TextView tvBriefIntroduction2 = findViewById(R.id.tv_brief_introduction2);
        tvBriefIntroduction2.setText(R.string.baybayin_introduction2);

        TextView tvIntroductionV17 = findViewById(R.id.tv_introduction_v17);
        tvIntroductionV17.setText(R.string.baybayin_version17);

        TextView tvIntroductionV17plus = findViewById(R.id.tv_introduction_v17plus);
        tvIntroductionV17plus.setText(R.string.baybayin_version17plus);

        TextView tvIntroductionV18 = findViewById(R.id.tv_introduction_v18);
        tvIntroductionV18.setText(R.string.baybayin_version18);

        TextView tvIntroductionV20plus = findViewById(R.id.tv_introduction_v20plus);
        tvIntroductionV20plus.setText(R.string.baybayin_version20plus);



        VideoView videoViewAbakada = findViewById(R.id.videoView_abakada2);
        if (videoViewAbakada != null) {
            int videoResource = getResources().getIdentifier("abakada_video_compresed", "raw", getPackageName());
            if (videoResource != 0) {
                videoViewAbakada.setVideoPath("android.resource://" + getPackageName() + "/" + videoResource);

                SeekBar SeekbarVideo = findViewById(R.id.seekBar_video2);

                // Start the video playback
                videoViewAbakada.start();

                // Update SeekBar progress using a Handler
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int currentPosition = videoViewAbakada.getCurrentPosition();
                        int totalDuration = videoViewAbakada.getDuration();

                        // Update SeekBar progress based on video position
                        SeekbarVideo.setMax(totalDuration);
                        SeekbarVideo.setProgress(currentPosition);

                        // Schedule the handler to run again after a short delay
                        handler.postDelayed(this, 1000); // Update every 1 second
                    }
                }, 0);

                // Set an OnCompletionListener to restart the video when it reaches the end
                videoViewAbakada.setOnCompletionListener(mp -> {
                    // Restart the video
                    videoViewAbakada.start();
                });

                // Add an OnSeekBarChangeListener to the SeekBar
                SeekbarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            // Calculate the timestamp to jump to based on SeekBar progress
                            int newPosition = (int) (((float) progress / seekBar.getMax()) * videoViewAbakada.getDuration());

                            // Jump to the specified timestamp in the video
                            videoViewAbakada.seekTo(newPosition);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Not needed for this implementation
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Not needed for this implementation
                    }
                });

                ImgbtnPlayPause =findViewById(R.id.imgbtn_playpause2);
                ImgbtnPlayPause.setOnClickListener(v -> {

                    if (videoViewAbakada.isPlaying()) {
                        // Pause the video and change the button icon to play
                        videoViewAbakada.pause();
                        ImgbtnPlayPause.setImageResource(R.drawable.video_pause_icon); // Change to your play icon resource
                    } else {
                        // Resume the video and change the button icon to pause
                        videoViewAbakada.start();
                        ImgbtnPlayPause.setImageResource(R.drawable.video_play_icon); // Change to your pause icon resource
                    }
                });
            }
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

    private void slideToTextview(){
        NestedScrollView nsvMain = findViewById(R.id.nestedsv_introduction);
        final TextView tvImportance = findViewById(R.id.tv_importance);
        final TextView tvB20Plus = findViewById(R.id.tv_b20plus);
        final TextView tvB18 = findViewById(R.id.tv_b18);
        final TextView tvB17 = findViewById(R.id.tv_brief_B17);

        ViewTreeObserver vto = nsvMain.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                nsvMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (IntroSlideTo == 2){
                    nsvMain.smoothScrollTo(0, (int) tvImportance.getY(), 1500);
                } else if (IntroSlideTo == 3) {
                    nsvMain.smoothScrollTo(0, (int) tvB20Plus.getY(), 2000);
                } else if (IntroSlideTo == 4) {
                    nsvMain.smoothScrollTo(5, (int) tvB18.getY(), 2500);
                }else if (IntroSlideTo == 5) {
                    nsvMain.smoothScrollTo(5, (int) tvB17.getY(), 2800);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        ImgbtnPlayPause.setImageResource(R.drawable.video_pause_icon);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BtnExitIntroduction.performClick();
    }
}