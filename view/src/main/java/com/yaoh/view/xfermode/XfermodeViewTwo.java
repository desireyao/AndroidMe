package com.yaoh.view.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.yaoh.view.R;

/**
 * Package com.yaoh.view.xfermode.
 * Created by yaoh on 2016/12/07.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class XfermodeViewTwo extends View {

    private static final int W = 160;
    private static final int H = 160;

    private Bitmap mSrcB;
    private Bitmap mDstB;

    private int mScreenWidth;   // 屏幕的宽
    private int mScreenHeight;  // 屏幕的高

    private int mPiercedLeft, mPiercedRight, mPiercedTop, mPiercedBottom;

    public XfermodeViewTwo(Context context) {
        super(context);
    }

    public XfermodeViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPiercedPosition(int piercedLeft, int piercedTop, int piercedRight, int piercedBottom) {
        mPiercedLeft = piercedLeft;
        mPiercedRight = piercedTop;
        mPiercedTop = piercedRight;
        mPiercedBottom = piercedBottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mScreenWidth == 0) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;
        }
        Log.e("TAG", "mScreenWidth: " + mScreenWidth + " mScreenHeight: " + mScreenHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mSrcB = makeSrc();
        mDstB = makeDst();

        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        canvas.saveLayer(0, 0, mScreenWidth, mScreenHeight, null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        canvas.drawBitmap(mDstB, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        paint.setAlpha(100);
        canvas.drawBitmap(mSrcB, 0, 0, paint);
        paint.setXfermode(null);

        canvas.saveLayer(0, 0, mScreenWidth, mScreenHeight, null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        paint.setAlpha(255);
        canvas.drawBitmap(getBitmap4Img(), mPiercedRight, mPiercedBottom, paint);
    }

    private Bitmap makeDst() {
        Bitmap bm = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        Canvas canvcs = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        canvcs.drawOval(new RectF(W, H, W * 2, H * 2), paint);
        return bm;
    }

    private Bitmap makeSrc() {
        Bitmap bm = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        Canvas canvcs = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvcs.drawRect(new RectF(0, 0, mScreenWidth, mScreenHeight), paint);
        return bm;
    }

    private Bitmap getBitmap4Img() {
        BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ga_studio);
        Bitmap bmp = bmpDraw.getBitmap();

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // 计算缩放比例
        float scaleWidth = 0.3f;
        float scaleHeight = 0.3f;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
