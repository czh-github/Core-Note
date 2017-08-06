package com.laochen.source.java.collection.queue;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ListView;

/**
 * Date:2017/7/10 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class MyListView extends ListView {
    private static final String TAG = "MyListView";

    VelocityTracker velocityTracker = VelocityTracker.obtain();

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        aboutVelocityTracker(ev);
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        velocityTracker.recycle();
    }

    private void aboutVelocityTracker(MotionEvent event) {
        velocityTracker.addMovement(event);
        // 计算速度：指定1000得到的速度单位是像素/1000ms
        velocityTracker.computeCurrentVelocity(1000);
        // 获取X和Y方向的速度，可以是负数
        float vx = velocityTracker.getXVelocity();
        float vy = velocityTracker.getYVelocity();
//        Log.e(TAG, "VelocityX:" + vx + "px/s");
        Log.e(TAG, "VelocityY:" + vy + "px/s");
    }
}
