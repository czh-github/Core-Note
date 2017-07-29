package com.laochen.jni.view.scroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * Date:2017/6/30 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class ScrollView extends View {
    private float mLastX;
    private float mLastY;
    private Scroller mScroller;


    public ScrollView(Context context) {
        this(context, null);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    // 实现View的三种方式：
    // 1.scrollTo/scrollBy：适合对View的内容滑动；
    // 2.动画：适合没有交互的View
    // 3.改变LayoutParams：适合有交互的View

    // 动画方式实现全屏跟手滑动
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getRawX();
//        float y = event.getRawY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                setTranslationX(getTranslationX() + (x - mLastX));
//                setTranslationY(getTranslationY() + (y - mLastY));
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        mLastX = x;
//        mLastY = y;
//        return true;
//    }

    // Scroller实现平缓滑动
    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // startScroll方法只是把参数传递给Scroller
        mScroller.startScroll(scrollX, 0, delta, 0, 1000);
        // invalidate导致View重绘，View的draw方法会去调用computeScroll方法，
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
