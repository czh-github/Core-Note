package com.laochen.jni.view.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Date:2017/6/30 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class BaseView extends View {
    private static final String TAG = "BaseView";
    private GestureDetector mGestureDetector;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGestureDetector();
    }

    /**
     * GestureDetector：手势检测，用于辅助检测单击，双击，滑动，长按等。
     * 如果监听滑动相关的，建议在onTouchEvent()中实现；如果要监听滑动和双击，建议使用GestureDetector。
     */
    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            // 手指轻触屏幕瞬间，由一个ACTION_DOWN触发。
            // 返回true表示事件被消费；否则不消费。
            @Override
            public boolean onDown(MotionEvent e) {
                Log.e(TAG, "onDown");
                return true;
            }

            // 手指轻触屏幕，没有移动也没有松开。
            // 强调没有移动也没有松开，让用户知道其操作被识别了，例如可以高亮显示某个元素。
            @Override
            public void onShowPress(MotionEvent e) {
                Log.e(TAG, "onShowPress");
            }

            // 手指轻触屏幕后松开（没有move），由一个ACTION_UP触发。单击行为。
            // 返回true表示事件被消费；否则不消费。
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e(TAG, "onSingleTapUp");
                return false;
            }

            // 手指按下屏幕并滑动，由一个ACTION_DOWN和多个ACTION_MOVE触发。滑动行为。
            // 返回true表示事件被消费；否则不消费。
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e(TAG, "onScroll");
                return false;
            }

            // 手指长按
            @Override
            public void onLongPress(MotionEvent e) {
                Log.e(TAG, "onLongPress");
            }

            // 按下屏幕快速滑动后松开，由由一个ACTION_DOWN，多个ACTION_MOVE和一个ACTION_UP触发。快速滑动行为。
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e(TAG, "onFling");
                return false;
            }
        });
        // 设置为true只能接收长按事件
        // 设置为false能接收长按事件和拖动事件
        mGestureDetector.setIsLongpressEnabled(false);
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            // 严格的单击，跟onSingleTapUp的区别是，如果发生了onSingleTapConfirmed，后面不可能再紧跟一个单击。
            // 即该事件不可能是双击中的一次单击。
            // 跟onDoubleTap互斥。
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.e(TAG, "onSingleTapConfirmed");
                return false;
            }

            // 双击
            // 跟onSingleTapConfirmed互斥
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.e(TAG, "onDoubleTap");
                return false;
            }

            // 双击期间发生的事件，包括ACTION_DOWN,ACTION_MOVE和ACTION_UP
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.e(TAG, "onDoubleTapEvent");
                return false;
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 接管View的onTouchEvent
//        return mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }
}
