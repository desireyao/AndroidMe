package com.yaoh.view.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.yaoh.view.R;

/**
 * Package com.beacool.morethan.ui.widgets.
 * Created by yaoh on 2016/12/08.
 * Company Beacool IT Ltd.
 * <p/>
 * Description: 遥控拍照的遮罩页
 */
public class MaskPierceView extends View {
    private static final String TAG = "MaskPierceView";

    private Bitmap mSrcRect;
    private Bitmap mDstCircle;

    private int mScreenWidth;   // 屏幕的宽
    private int mScreenHeight;  // 屏幕的高

    private int mPiercedX, mPiercedY;
    private int mPiercedRadius;

    public MaskPierceView(Context context) {
        this(context, null);
    }

    public MaskPierceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);

        if (mScreenWidth == 0) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            mScreenWidth = dm.widthPixels;
            mScreenHeight = dm.heightPixels;
        }
    }

    /**
     * @param mPiercedX      镂空的圆心坐标
     * @param mPiercedY      镂空的圆心坐标
     * @param mPiercedRadius 镂空的圆半径
     */
    public void setPiercePosition(int mPiercedX, int mPiercedY, int mPiercedRadius) {
        this.mPiercedX = mPiercedX;
        this.mPiercedY = mPiercedY;
        this.mPiercedRadius = mPiercedRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mSrcRect = makeSrcRect();
        mDstCircle = makeDstCircle();

        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        canvas.saveLayer(0, 0, mScreenWidth, mScreenHeight, null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        canvas.drawBitmap(mDstCircle, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        paint.setAlpha(160);
        canvas.drawBitmap(mSrcRect, 0, 0, paint);
        paint.setXfermode(null);

        canvas.saveLayer(0, 0, mScreenWidth, mScreenHeight, null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        paint.setAlpha(255);

        canvas.drawBitmap(createPromptBitmap(), mPiercedX + mPiercedRadius, mPiercedY + mPiercedRadius, paint);
    }

    /**
     * 创建镂空层圆形形状
     * @return
     */
    private Bitmap makeDstCircle() {
        Bitmap bm = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        Canvas canvcs = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        canvcs.drawCircle(mPiercedX, mPiercedY, mPiercedRadius, paint);
        return bm;
    }

    /**
     * 创建遮罩层形状
     *
     * @return
     */
    private Bitmap makeSrcRect() {
        Bitmap bm = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        Canvas canvcs = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvcs.drawRect(new RectF(0, 0, mScreenWidth, mScreenHeight), paint);
        return bm;
    }

    /**
     * 创建提示提示信息文字图片的bitmap
     *
     * @return
     */
    private Bitmap createPromptBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.prompt);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        DisplayMetrics dm = getResources().getDisplayMetrics();  //获取屏幕密度
        float realScale = 1.0f / dm.density;  //恢复到实际像素的缩放
        float settingScale = realScale;

        if (realScale * width > mScreenWidth / 2) {
            settingScale = 0.8f * mScreenWidth / width;
        } else if (realScale * height > mScreenHeight) {
            settingScale = 0.8f * mScreenHeight / height;
        }

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(settingScale, settingScale);

//      得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newBitmap;
    }
}

