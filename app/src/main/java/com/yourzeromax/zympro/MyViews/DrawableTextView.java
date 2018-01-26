package com.yourzeromax.zympro.MyViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.yourzeromax.zympro.R;


/**
 * Created by yourzeromax on 2017/11/8.
 *   
 */

public class DrawableTextView extends AppCompatTextView {
    public static final int LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4;
    int mHeight, mWidth;
    Drawable mDrawable;
    int mLocation;

    public DrawableTextView(Context context) {
        super(context);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        mHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawable_height, 0);
        mWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawable_width, 0);
        mDrawable = typedArray.getDrawable(R.styleable.DrawableTextView_drawableSrc);
        mLocation = typedArray.getInt(R.styleable.DrawableTextView_drawableLocation, LEFT);
        typedArray.recycle();
        drawDrawable();

    }

    private void drawDrawable() {
        if (mDrawable != null) {
            Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
            Drawable drawable;
            if (mWidth != 0 && mHeight != 0) {

                drawable = new BitmapDrawable(getResources(), getBitmap(bitmap,
                        mWidth, mHeight));
            } else {
                drawable = new BitmapDrawable(getResources(),
                        Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
                                bitmap.getHeight(), true));
            }

            switch (mLocation) {
                case LEFT:
                    this.setCompoundDrawablesWithIntrinsicBounds(drawable, null,
                            null, null);
                    break;
                case TOP:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, drawable,
                            null, null);
                    break;
                case RIGHT:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            drawable, null);
                    break;
                case BOTTOM:
                    this.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                            drawable);
                    break;
            }
        }
    }

    //缩放图片
    public Bitmap getBitmap(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = (float) newWidth / width;
        float scaleHeight = (float) newHeight / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

}
