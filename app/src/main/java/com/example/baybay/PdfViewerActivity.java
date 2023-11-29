package com.example.baybay;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PdfViewerActivity extends AppCompatActivity {

    ImageButton Imgbtn_pdfview_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_pdf_viewer);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Get the root view of the layout
        View rootView = getWindow().getDecorView().getRootView();

        // Set the background color using a hexadecimal color value
        rootView.setBackgroundColor(Color.parseColor("#fffbff"));



        PDFView pdfView = findViewById(R.id.pdfView);

        Intent intent = getIntent();
        int bookNumber = intent.getIntExtra("bookNumber", 1);
        String pdfFileName = "";

        if(bookNumber == 1){
            pdfFileName = "pdf_library_mga_alamat.pdf";
        } else if (bookNumber == 2) {
            pdfFileName = "pdf_library_likhang_makabayan.pdf";
        } else if (bookNumber == 3) {
            pdfFileName = "pdf_library_maikling_kwento.pdf";
        } else if (bookNumber == 4) {
            pdfFileName = "pdf_library_mga_tula.pdf";
        }

        pdfView.fromAsset(pdfFileName)
                .enableSwipe(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();


        Imgbtn_pdfview_exit = findViewById(R.id.imgbtn_pdfview_exit);
        Imgbtn_pdfview_exit.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Imgbtn_pdfview_exit.performClick();
    }
}