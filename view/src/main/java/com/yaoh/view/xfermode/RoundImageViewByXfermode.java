package com.yaoh.view.xfermode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.yaoh.view.R;

import java.lang.ref.WeakReference;

/**
 * Package com.yaoh.view.xfermode.
 * Created by yaoh on 2016/11/02.
 * <p/>
 * Description:
 */
public class RoundImageViewByXfermode extends ImageView {

    private Paint mPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;

    private WeakReference<Bitmap> mWeakBitmap;

    /**
     * 图片的类型，圆形 or 圆角
     */
    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;

    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;

    /**
     * 圆角的大小
     */
    private int mBorderRadius;


    public RoundImageViewByXfermode(Context context) {
        this(context, null);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public RoundImageViewByXfermode(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBorderRadius = 30;
        type = TYPE_CIRCLE;// 默认为Circle
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        draw(canvas);

        //绘制矩形
//        mPaint.setStrokeWidth(5);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.GREEN);
//        canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()),mPaint);


    }


    public void draw(Canvas canvas){
        //在缓存中取出bitmap
        Bitmap bitmap = Bitmap.createBitmap(getWidth() * 2, getHeight() * 2, Bitmap.Config.ARGB_8888);

            //拿到Drawable
            Drawable drawable = getDrawable();
            Log.e("TAG","drawable: " + drawable);

            if (drawable != null) {
                //创建画布
                Canvas drawCanvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, getWidth(), getHeight());
                drawable.draw(drawCanvas);

                mMaskBitmap = getBitmap();
//                // Draw Bitmap.
                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);
//
//                //绘制形状
//                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
//                mPaint.setXfermode(null);
//
                //将准备好的bitmap绘制出来
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
    }

    /**
     * 绘制形状
     *
     * @return
     */
    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
//      canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
        return bitmap;
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }

}
