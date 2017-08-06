package com.laochen.source.view.dispatcher;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Date:2017/6/30 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class EventViewGroup extends RelativeLayout {
    private static final String TAG = "EventViewGroup";

    public EventViewGroup(Context context) {
        super(context);
    }

    public EventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent#ACTION_DOWN");
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent#ACTION_MOVE");
                return false;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent#ACTION_UP");
               return false;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ViewGroup#onTouchEvent#ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ViewGroup#onTouchEvent#ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ViewGroup#onTouchEvent#ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
