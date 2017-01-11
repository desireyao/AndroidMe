package com.yaoh.view.RunningSpeedProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yaoh.view.R;

/**
 * Package com.yaoh.view.RunningSpeedProgress.
 * Created by yaoh on 2017/01/11.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:跑步的每KM的配速图
 */
public class RunningSpeedProgress extends View {
    private int mProgressColor;           // 进度条的颜色
    private int mProgressBackgroundColor; // 背景的颜色

    private Paint mPaint;

    public RunningSpeedProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RunningSpeedProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RunningSpeedProgress,defStyleAttr,0);
        mProgressColor = a.getColor(R.styleable.RunningSpeedProgress_ProgressColor, Color.parseColor("#FF8247"));
        mProgressBackgroundColor = a.getColor(R.styleable.RunningSpeedProgress_ProgressBackgroundColor,Color.parseColor("#FF4040"));
        a.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mProgressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mProgressPaint.setColor(mProgressBackgroundColor);
//        RectF rect = new RectF(100,10,310,110);
//        canvas.drawRect(rect,mProgressPaint);
//        mPaint.setColor(mProgressColor);
//        RectF rectProgress = new RectF(10,10,510,110);
//        canvas.drawRoundRect(rectProgress,50,50,mPaint);
//        canvas.drawBitmap(createRectBimap(),100,10,mPaint);
        canvas.drawBitmap(createRoundRectBimap(),10,10,mPaint);
        canvas.saveLayer(10, 10, 510, 110, null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(createRoundRectBimap(),10,10,mPaint);
        canvas.drawBitmap(createRectBimap(),100,10,mPaint);
        mPaint.setXfermode(null);
    }

    private Bitmap createRectBimap() {
        Bitmap bm = Bitmap.createBitmap(200, 100, Bitmap.Config.ARGB_8888);
        Canvas canvcs = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        canvcs.drawRect(new RectF(0, 0, 200, 100), paint);
        return bm;
    }

    private Bitmap createRoundRectBimap() {
        Bitmap bm = Bitmap.createBitmap(500, 100, Bitmap.Config.ARGB_8888);
        Canvas canvcs = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvcs.drawRoundRect(new RectF(0, 0, 500, 100),50,50,paint);
        return bm;
    }
}
