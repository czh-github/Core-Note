package com.laochen.jni.view.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import com.laochen.jni.R;

public class BaseViewActivity extends AppCompatActivity {
    private static final String TAG = "BaseViewActivity";
    private View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        view1 = findViewById(R.id.view1);

        aboutTouchSlop();
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.scrollTo(700, 700);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * View的位置参数。
     * left:左上角横坐标
     * top:左上角纵坐标
     * right:右上角横坐标
     * bottom:右上角纵坐标
     * 这些坐标单位都是像素，都是相对于View的父容器来说的。
     * 注意在onResume()里面获取不到这些坐标信息，因为onResume()不是最佳的activity对用户可见的指示，
     * 需要在onWindowFocusChanged()里面获取。
     */
    private void viewLocation() {
        Log.e(TAG, "left:" + view1.getLeft());
        Log.e(TAG, "top:" + view1.getTop());
        Log.e(TAG, "right:" + view1.getRight());
        Log.e(TAG, "bottom:" + view1.getBottom());
        // x,y表示View左上角的坐标，相对于父容器
        Log.e(TAG, "x:" + view1.getX());
        Log.e(TAG, "y:" + view1.getY());
        // translateX,translateY是View左上角相对于父容器的偏移量
        // View平移过程中，top和left不会变，变化的是x,y,translateX,translateY，它们之间的关系是
        // x = left + translateX
        // y = top + translateY
        Log.e(TAG, "translateX:" + view1.getTranslationX());
        Log.e(TAG, "translateY:" + view1.getTranslationY());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            viewLocation();
        }
    }

    private void aboutMotionEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        // 手指接触屏幕的点相对于当前View左上角的横纵坐标
        float x = event.getX();
        float y = event.getY();
        // 手指接触屏幕的点相对于手机屏幕左上角的横纵坐标
        float rawX = event.getRawX();
        float rawY = event.getRawY();
    }

    private void aboutTouchSlop() {
        // TouchSlop是系统所能识别出的被认为是滑动的最小距离，其值和设备有关
        // 通过以下方法得到，单位是像素
        // 我们可以利用这个常量做一些过滤，如果当两次的滑动距离小于这个值，可以认为不是滑动，从而提升用户体验
        int touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        Log.e(TAG, "TouchSlop:" + touchSlop + "px");
    }

    /**
     * VelocityTracker：速度追踪，追踪手指在滑动过程中的速度，包括X和Y方向。
     */
    private void aboutVelocityTracker(MotionEvent event) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        // 计算速度：指定1000得到的速度单位是像素/1000ms
        velocityTracker.computeCurrentVelocity(1000);
        // 获取X和Y方向的速度，可以是负数
        float vx = velocityTracker.getXVelocity();
        float vy = velocityTracker.getYVelocity();
        Log.e(TAG, "VelocityX:" + vx + "px/s");
        Log.e(TAG, "VelocityY:" + vy + "px/s");
        velocityTracker.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      //  aboutVelocityTracker(event);
        return super.onTouchEvent(event);
    }
}
