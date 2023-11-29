package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import es.dmoral.toasty.Toasty;

public class LineGraph extends AppCompatActivity {
    private Toast globalToast;
    ImageButton imgbtnGrapghExit;
    ImageView ImgviewGraph_pb;

    public static com.github.mikephil.charting.charts.LineChart lineChart;
    public List<String> xValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_line_chart);

        //Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        getWindow().setBackgroundDrawableResource(R.drawable.bg_graph2);


        //View rootView = getWindow().getDecorView().getRootView();


        //rootView.setBackgroundColor(Color.parseColor("#0F182B"));

        // Stop BG Music
        //Z_SoundManager.StopLessonsBgMusic();

        imgbtnGrapghExit = findViewById(R.id.imgbtn_grapgh_exit);
        imgbtnGrapghExit.setOnClickListener(view -> {
            finish();
        });

        ImgviewGraph_pb = findViewById(R.id.imgview_graph_pb);
        ImgviewGraph_pb.setOnClickListener(view -> {
            cancelToast();
            globalToast= Toasty.info(LineGraph.this, "Calculated by summing up a gameplay scores and dividing by the number of gameplay.", Toasty.LENGTH_LONG);
            globalToast.show();
        });



        lineChart = findViewById(R.id.chartview);
        Description description = new Description();
        description.setText("");
        description.setTextSize(10);
        description.setPosition(700f, 25f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        lineChart.setPinchZoom(false);
        lineChart.setScaleYEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraTopOffset(30f);
        lineChart.setExtraBottomOffset(10f);
        lineChart.setVisibleXRangeMaximum(6);

        // Enable zooming and scrolling
        lineChart.setScaleXEnabled(true);
        lineChart.zoom(4f, 1f, 0, 0);



        xValues = Arrays.asList();
        List<Entry> entries1 = createEntries1(this);
        List<Entry> entries2 = createEntries2(this);
        List<Entry> entries3 = createEntries3(this);

        // Set up X-axis values
        xValues = new ArrayList<>();
        for (int i = 1; i <= entries1.size() + entries2.size() + entries3.size(); i++) {
            xValues.add(String.valueOf(i));
        }


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisLineWidth(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE); // __
        //xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(10);
        //xAxis.setAxisMinimum(6);
        //xAxis.setAxisMaximum(6);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(21f);
        yAxis.setAxisLineColor(Color.GRAY);
        yAxis.setLabelCount(10);
        yAxis.enableGridDashedLine(20f, 10f, 0f);
        yAxis.setTextColor(Color.WHITE); // |
        yAxis.setXOffset(15f); // Set space between Y-axis labels and the axis line
        yAxis.setDrawGridLines(false);
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value > 20) {
                    return "";
                } else {
                    return String.valueOf((int) value);
                }
            }
        });




        LineDataSet dataSet1 = new LineDataSet(entries1, "QUIZ");
        dataSet1.setLineWidth(10);
        dataSet1.setCircleRadius(8);
        dataSet1.setCircleHoleRadius(3);
        dataSet1.setValueTextColor(Color.WHITE);
        dataSet1.setValueTextSize(10);
        dataSet1.setCircleColor(Color.parseColor("#FFA316"));
        dataSet1.setColor(Color.parseColor("#FFA316"));
        dataSet1.setMode(LineDataSet.Mode.LINEAR);
        dataSet1.enableDashedLine(10,10,0);

        LineDataSet dataSet2 = new LineDataSet(entries2, "MATCH");
        dataSet2.setLineWidth(10);
        dataSet2.setCircleRadius(8);
        dataSet2.setCircleHoleRadius(3);
        dataSet2.setValueTextColor(Color.WHITE);
        dataSet2.setValueTextSize(10);
        dataSet2.setCircleColor(Color.parseColor("#7B9AFA"));
        dataSet2.setColor(Color.parseColor("#7B9AFA"));
        dataSet2.setMode(LineDataSet.Mode.LINEAR);
        dataSet2.enableDashedLine(10,10,0);

        LineDataSet dataSet3 = new LineDataSet(entries3, "SPELL");
        dataSet3.setLineWidth(10);
        dataSet3.setCircleRadius(8);
        dataSet3.setCircleHoleRadius(3);
        dataSet3.setValueTextColor(Color.WHITE);
        dataSet3.setValueTextSize(10);
        dataSet3.setCircleColor(Color.parseColor("#0DE4FB"));
        dataSet3.setColor(Color.parseColor("#0DE4FB"));
        dataSet3.setMode(LineDataSet.Mode.LINEAR);
        dataSet3.enableDashedLine(10,10,0);

        LineData lineData = new LineData(dataSet1, dataSet2, dataSet3);
        lineChart.setData(lineData);
        lineChart.invalidate();

        averageGraph(this);

        final boolean[] isZoomedOut = {false};
        Button GraphChartZoom = findViewById(R.id.graph_chart_zoom);
        GraphChartZoom.setOnClickListener(view -> {
            cancelToast();
            ClickSoundEffect();
            if (isZoomedOut[0]) {
                lineChart.zoom(4f, 1f, 0, 0);
                isZoomedOut[0] = false;
                globalToast = Toasty.info(LineGraph.this,"Zoomed In", Toast.LENGTH_SHORT);
            } else {
                lineChart.fitScreen();
                isZoomedOut[0] = true;
                globalToast= Toasty.info(LineGraph.this, "Zoomed Out", Toasty.LENGTH_SHORT);
            }
            globalToast.show();
        });

        final AtomicInteger[] clickCount = {new AtomicInteger()};
        Button GraphChartView = findViewById(R.id.graph_chart_view);
        GraphChartView.setOnClickListener(view -> {
            cancelToast();
            ClickSoundEffect();
            clickCount[0].getAndIncrement();

            LineData newLineData = new LineData();

            if (clickCount[0].get() == 1) {
                newLineData.addDataSet(dataSet1);
                globalToast= Toasty.info(LineGraph.this, "Quiz", Toasty.LENGTH_SHORT);
            } else if (clickCount[0].get() == 2) {
                newLineData.addDataSet(dataSet3);
                globalToast= Toasty.info(LineGraph.this, "Spell", Toasty.LENGTH_SHORT);
            } else if (clickCount[0].get() == 3) {
                newLineData.addDataSet(dataSet2);
                globalToast= Toasty.info(LineGraph.this, "Match", Toasty.LENGTH_SHORT);
            } else if (clickCount[0].get() == 4) {
                newLineData.addDataSet(dataSet1);
                newLineData.addDataSet(dataSet2);
                newLineData.addDataSet(dataSet3);
                globalToast= Toasty.info(LineGraph.this, "All", Toasty.LENGTH_SHORT);

                clickCount[0].set(0);
            }
            globalToast.show();

            lineChart.clear();

            // Set the new data and refresh the chart
            lineChart.setData(newLineData);
            lineChart.invalidate();
        });
    }


    public static List<Entry> createEntries1(Context context) {
        List<Entry> entries1 = new ArrayList<>();
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(context);
        List<Integer> quizScoreList = scoreManager.getQuizScoreList();

        if (quizScoreList != null) {
            int xValue = 0;
            for (Integer score : quizScoreList) {
                entries1.add(new Entry(xValue, score));
                xValue++;
            }
        }
        return entries1;
    }


    public static List<Entry> createEntries2(Context context) {
        List<Entry> entries2 = new ArrayList<>();
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(context);
        List<Integer> matchScoreList = scoreManager.getMatchScoreList();

        if (matchScoreList != null) {
            int xValue = 0;
            for (Integer score : matchScoreList) {
                entries2.add(new Entry(xValue, score));
                xValue++;
            }
        }

        return entries2;
    }


    public static List<Entry> createEntries3(Context context) {
        List<Entry> entries3 = new ArrayList<>();
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(context);
        List<Integer> spellScoreList = scoreManager.getSpellScoreList();

        if (spellScoreList != null) {
            int xValue = 0;
            for (Integer score : spellScoreList) {
                entries3.add(new Entry(xValue, score));
                xValue++;
            }
        }
        return entries3;
    }


    ProgressBar pbGraphQuiz;
    ProgressBar pbGraphSpell;
    ProgressBar pbGraphMatch;
    TextView tvGraphQuiz;
    TextView tvGraphSpell;
    TextView tvGraphMatch;
    TextView tvGraphQuizArrayCount;
    TextView tvGraphSpellArrayCount;
    TextView tvGraphMatchArrayCount;
    public void averageGraph(Context context){
        Z_ScoreManager scoreManager = Z_ScoreManager.getInstance(context);

        List<Integer> quizScoreList = scoreManager.getQuizScoreList();
        if (quizScoreList.size() != 0){
            int maxQuizScore = 20 * quizScoreList.size();

            int totalScoreQuiz = 0;
            for (int score : quizScoreList) {
                totalScoreQuiz += score;
            }

            double averageScoreQuiz = (double) totalScoreQuiz / maxQuizScore * 100;

            pbGraphQuiz = findViewById(R.id.progressBar_graph_quiz);
            pbGraphQuiz.setMax(100);
            pbGraphQuiz.setProgress((int) averageScoreQuiz);

            tvGraphQuiz = findViewById(R.id.tv_graph_quiz);
            tvGraphQuiz.setText(String.format("%.0f%%", averageScoreQuiz));
        }else{
            tvGraphQuiz = findViewById(R.id.tv_graph_quiz);
            tvGraphQuiz.setText("0%");
        }


        List<Integer> spellScoreList = scoreManager.getSpellScoreList();
        if (spellScoreList.size() != 0){
            int maxSpellScore = 20 * spellScoreList.size();

            int totalScoreSpell = 0;
            for (int score : spellScoreList) {
                totalScoreSpell += score;
            }

            double averageScoreSpell = (double) totalScoreSpell / maxSpellScore * 100;

            pbGraphSpell = findViewById(R.id.progressBar_graph_spell);
            pbGraphSpell.setMax(100);
            pbGraphSpell.setProgress((int) averageScoreSpell);

            tvGraphSpell = findViewById(R.id.tv_graph_spell);
            tvGraphSpell.setText(String.format("%.0f%%", averageScoreSpell));
        }else{
            tvGraphSpell = findViewById(R.id.tv_graph_spell);
            tvGraphSpell.setText("0%");
        }

        List<Integer> matchScoreList = scoreManager.getMatchScoreList();
        if (matchScoreList.size() != 0){
            int maxMatchScore = 20 * matchScoreList.size();

            int totalScoreMatch = 0;
            for (int score : matchScoreList) {
                totalScoreMatch += score;
            }

            double averageScoreMatch = (double) totalScoreMatch / maxMatchScore * 100;

            pbGraphMatch = findViewById(R.id.progressBar_graph_match);
            pbGraphMatch.setMax(100);
            pbGraphMatch.setProgress((int) averageScoreMatch);

            tvGraphMatch = findViewById(R.id.tv_graph_match);
            tvGraphMatch.setText(String.format("%.0f%%", averageScoreMatch));
        }else{
            tvGraphMatch = findViewById(R.id.tv_graph_match);
            tvGraphMatch.setText("0%");
        }

        tvGraphQuizArrayCount = findViewById(R.id.tv_graph_quizCount);
        tvGraphSpellArrayCount = findViewById(R.id.tv_graph_spellCount);
        tvGraphMatchArrayCount = findViewById(R.id.tv_graph_matchCount);

        tvGraphQuizArrayCount.setText("Quiz : " + String.valueOf(quizScoreList.size()));
        tvGraphSpellArrayCount.setText("Spell : " + String.valueOf(spellScoreList.size()));
        tvGraphMatchArrayCount.setText("Match : " + String.valueOf(matchScoreList.size()));
    }

    private void cancelToast() {
//        if (globalToast != null && globalToast.getView() != null && globalToast.getView().isShown()) {
//            globalToast.cancel();
//        }

        if (globalToast != null) {
            globalToast.cancel();
        }

    }

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Z_SoundManager.setActivityRulesPaused(true);
        cancelToast();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Z_SoundManager.setActivityRulesResumed(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelToast();
        imgbtnGrapghExit.performClick();
    }
}