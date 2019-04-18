package com.example.haolin.advertisinglist.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import static com.example.haolin.advertisinglist.constant.Constants.CONSTANTS_NUMBER_ZERO;

/**
 * @author haoli
 */
public class AdImageView extends AppCompatImageView {
    private int mDy;
    private int mMinDy;
    private Bitmap mBitmap;
    private RectF mBitmapRectF;

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        mMinDy = h;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        mBitmap = drawableToBitmap(drawable);
        mBitmapRectF = new RectF(CONSTANTS_NUMBER_ZERO, CONSTANTS_NUMBER_ZERO, w, mBitmap.getHeight() * w / mBitmap.getWidth());
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(CONSTANTS_NUMBER_ZERO, CONSTANTS_NUMBER_ZERO, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }
        mDy = dy - mMinDy;
        if (mDy <= CONSTANTS_NUMBER_ZERO) {
            mDy = CONSTANTS_NUMBER_ZERO;
        }
        if (mDy > mBitmapRectF.height() - mMinDy) {
            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        canvas.save();
        canvas.translate(CONSTANTS_NUMBER_ZERO, -mDy);
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }
}