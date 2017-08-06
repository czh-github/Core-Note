package com.laochen.source.view.flow;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Date:2017/7/5 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class SelfView extends View {
    private static final String TAG = "SelfView";
    private int mWidth = 100;
    private int mHeight = 100;
    public SelfView(Context context) {
        super(context);
    }

    public SelfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            Log.e(TAG, "widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST");
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            Log.e(TAG, "widthSpecMode == MeasureSpec.AT_MOST");
            setMeasuredDimension(mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            Log.e(TAG, "heightSpecMode == MeasureSpec.AT_MOST");
            setMeasuredDimension(widthSpecSize, mHeight);
        } else {
            Log.e(TAG, "heightSpecMode != MeasureSpec.AT_MOST && heightSpecMode != MeasureSpec.AT_MOST");
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
