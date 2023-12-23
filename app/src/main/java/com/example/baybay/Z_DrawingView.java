package com.example.baybay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Z_DrawingView extends View {

    public Z_DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // Initialize the soundManager
    }

    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;

    //private float prevX, prevY;
    private static final float TOUCH_TOLERANCE = 0.5f; // Adjust this value as needed
    Shader shader = new LinearGradient(0, 0, 100, 100, Color.RED, Color.WHITE, Shader.TileMode.MIRROR);

    private void init() {
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(50); // Increase value to make the lines thicker
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND); // Round brush
        //paint.setPathEffect(new DashPathEffect(new float[] {10, 20}, 0));
        //paint.setShadowLayer(5, 1, 1, Color.BLACK);
        paint.setAntiAlias(true);
        //paint.setShader(shader);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas(bitmap);
        }
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }


    private final ArrayList<Float> points = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Play drawing sfx
                Z_SoundManager.PlayDrawing(getContext());

                points.add(x);
                points.add(y);
                canvas.drawPoint(x, y, paint);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                float prevX = points.get(points.size() - 2);
                float prevY = points.get(points.size() - 1);

                float deltaX = Math.abs(x - prevX);
                float deltaY = Math.abs(y - prevY);
                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE) {
                    points.add(x);
                    points.add(y);
                    canvas.drawLine(prevX, prevY, x, y, paint);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //Stop drawing sfx
                Z_SoundManager.StopDrawing();
                break;
        }

        return true;
    }



    public void clearDrawing() {
//        if (canvas != null) {
//            canvas.drawColor(Color.WHITE);
//            invalidate();
//        }

        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            invalidate();
        }
    }
}

